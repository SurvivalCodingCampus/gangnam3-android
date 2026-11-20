# viewModelScope.launch와 동시성 문제

## 결론

`viewModelScope.launch` 블록은 메인 스레드에서 실행돼. 그래서 **스레드 레벨의 데이터 레이스는 거의 걱정할 필요가 없어.**
하지만 **suspend 지점에서 코루틴끼리 순서가 섞이기 때문에 논리적인 경쟁 상태(race condition)는 여전히 생겨.**

---

## 1. 왜 메인 스레드에서 도는가

```kotlin
// androidx.lifecycle 내부 구현
val ViewModel.viewModelScope: CoroutineScope
    get() = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
```

- 디스패처가 `Dispatchers.Main.immediate`라서 `launch {}` 블록 안에서 직접 실행되는 코드는 메인 스레드에서 돌아.
- 메인 스레드는 하나뿐이라서, 블록 안에서 `_state`를 건드리는 코드끼리 **물리적으로 동시에** 실행되지는 않아.

---

## 2. 그래도 문제가 생기는 경우

### (a) suspend 지점에서 다른 코루틴이 끼어듦 → 업데이트 유실

코루틴은 `suspend` 지점에서 멈췄다가 나중에 다시 실행돼. 그 사이에 다른 코루틴이 먼저 실행될 수 있어.

```kotlin
// ❌ 문제 코드
fun toggleBookmark(id: Int) {
    viewModelScope.launch {
        val current = _state.value               // ① 상태 읽기
        val result = repository.toggle(id)       // ② suspend → 다른 launch가 끼어듦
        _state.value = current.copy(/* ... */)   // ③ 옛날 스냅샷으로 덮어씀
    }
}
```

버튼을 빠르게 두 번 누르면 이렇게 진행돼:

| 순서 | 코루틴 A | 코루틴 B |
|---|---|---|
| 1 | ① state = S0 읽음 | |
| 2 | ② suspend | |
| 3 | | ① state = S0 읽음 |
| 4 | | ② suspend |
| 5 | ③ S0 기준으로 S1 쓰기 | |
| 6 | | ③ S0 기준으로 S2 쓰기 → **A의 변경 유실** |

```kotlin
// ✅ 해결: suspend 이후 최신 상태 기준으로 갱신
fun toggleBookmark(id: Int) {
    viewModelScope.launch {
        val result = repository.toggle(id)
        _state.update { it.copy(/* ... */) }     // it = 그 시점의 최신 값
    }
}
```

### (b) 응답 순서 역전 → 오래된 결과가 화면에 남음

검색어 입력처럼 요청을 연달아 보낼 때 문제가 돼. 예를 들어 `"a"` 요청이 `"ab"` 요청보다 늦게 응답하면 화면에 `"a"`의 결과가 남아.

```kotlin
// ✅ 해결 1: 이전 Job 취소
private var searchJob: Job? = null

fun search(query: String) {
    searchJob?.cancel()
    searchJob = viewModelScope.launch {
        val result = repository.search(query)
        _state.update { it.copy(recipes = result) }
    }
}

// ✅ 해결 2: Flow + flatMapLatest / collectLatest
private val query = MutableStateFlow("")

init {
    viewModelScope.launch {
        query
            .debounce(300)
            .collectLatest { q ->
                val result = repository.search(q)
                _state.update { it.copy(recipes = result) }
            }
    }
}
```

### (c) 블록 안에서 디스패처를 바꿈

```kotlin
viewModelScope.launch {
    withContext(Dispatchers.IO) {
        _state.value = _state.value.copy(/* ... */)  // ⚠️ 여기는 IO 스레드
    }
}
```

- `MutableStateFlow` 자체는 thread-safe라서 크래시는 안 나.
- 다만 `value = value.copy()` 패턴은 **원자적이지 않아서** 업데이트가 유실될 수 있어.
- 일반 `var`나 `MutableList` 같은 걸 건드리면 **진짜 데이터 레이스**가 생겨.

```kotlin
// ✅ 해결: 무거운 작업만 IO에서 하고, 상태 갱신은 메인에서 update로
viewModelScope.launch {
    val data = withContext(Dispatchers.IO) { heavyWork() }
    _state.update { it.copy(data = data) }
}
```

### (d) Repository / DataSource 내부의 공유 상태

ViewModel이 메인에서 돈다고 해도 그 아래 레이어는 IO나 Default 디스패처에서 돌 수 있어.

```kotlin
// ❌ 여러 스레드에서 동시에 접근하면 데이터 레이스
class RecipeRepositoryImpl : RecipeRepository {
    private val cache = mutableMapOf<Int, Recipe>()
}

// ✅ Mutex로 보호
class RecipeRepositoryImpl : RecipeRepository {
    private val mutex = Mutex()
    private val cache = mutableMapOf<Int, Recipe>()

    suspend fun get(id: Int): Recipe? = mutex.withLock { cache[id] }
}
```

---

## 3. `_state.value = ...` vs `_state.update { }`

```kotlin
// 내부 구현 (CAS 루프)
public inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    while (true) {
        val prevValue = value
        val nextValue = function(prevValue)
        if (compareAndSet(prevValue, nextValue)) return
    }
}
```

| | `_state.value = _state.value.copy()` | `_state.update { it.copy() }` |
|---|---|---|
| 읽기와 쓰기 사이 원자성 | X | O (CAS) |
| 멀티스레드에서 안전한가 | X (업데이트 유실 가능) | O |
| 최신 값 기준으로 갱신하는가 | 읽은 시점 기준 | 쓰는 시점 기준 |

---

## 4. 정리

| 걱정할 것 | 필요 여부 | 대응 |
|---|---|---|
| 메인 스레드에서 `_state` 동시 쓰기 (데이터 레이스) | 거의 X | - |
| suspend 사이에 끼어들어 오래된 값으로 덮어쓰기 | **O** | `_state.update {}` |
| 요청 응답 순서 역전 | **O** | Job cancel, `collectLatest`, `flatMapLatest` |
| `withContext`로 스레드를 바꾼 뒤 상태 변경 | **O** | 결과만 반환받고 메인에서 갱신 |
| 하위 레이어의 공유 가변 상태 | **O** | `Mutex`, thread-safe 컬렉션 |

> **실무 습관:** `_state.value = _state.value.copy(...)` 대신 항상 `_state.update { it.copy(...) }`를 쓰는 것만 지켜도 대부분 막을 수 있어.
