# Result → UI State 변환, 에러 처리, 북마크 상태

현재 구조:

```
Repository                     ViewModel                      Screen
Result<T, NetworkError>  ──▶  SavedRecipesState        ──▶  when (state)
(데이터 결과)                  (UI 결과)                      Loading / Error / Success
                              unbookmarkedIds: Set<Long> ──▶  북마크 아이콘
```

---

## 1. 데이터 Result와 UI State는 다른 것

```kotlin
// 데이터 계층: 성공했는지, 실패했다면 왜인지
sealed interface Result<out D, out E> {
    data class Success<out D>(val value: D) : Result<D, Nothing>
    data class Error<out E>(val error: E) : Result<Nothing, E>
}

// UI 계층: 화면이 무엇을 그릴지
sealed interface SavedRecipesState {
    data object Loading : SavedRecipesState
    data class Error(val message: String) : SavedRecipesState
    data class Success(val recipes: List<Recipe>) : SavedRecipesState
}
```

ViewModel이 둘 사이를 변환한다.

```kotlin
_state.value = when (val result = recipeRepository.getRecipes()) {
    is Result.Success -> SavedRecipesState.Success(recipes = result.value)
    is Result.Error -> SavedRecipesState.Error(result.error.toMessage())
}
```

- NIA의 `Result.Loading -> TopicUiState.Loading`도 같은 변환이다.
- NIA는 Repository가 `Flow`를 반환하고 `.asResult()`가 `Loading`을 먼저 흘려보낸다. 그래서 `Result`에 `Loading`이 있다.
- 우리는 `suspend`로 한 번만 받는다. 그래서 `Result`에 `Loading`이 없다. 대신 호출 직전에 `_state.value = Loading`을 직접 넣는다.
- 화면용 결과는 `SavedRecipesState` 자체다. 그래서 공용 `UiResult<T>`를 따로 만들 필요가 없다.

### 같은 모양이라도 의미가 다르다

```kotlin
// ① 변환: 데이터 Result → UI State
is Result.Error -> SavedRecipesState.Error(...)

// ② 유지: UI State → 같은 UI State ("바꿀 게 없다")
is SavedRecipesState.Error -> state
```

---

## 2. `data object` vs `data class`: 이름만 쓸 수 있나

| 선언 | 값으로 쓰는 법 | `when` 분기 |
|---|---|---|
| `data object Error` | `SavedRecipesState.Error` | `SavedRecipesState.Error ->` |
| `data class Error(val message: String)` | `SavedRecipesState.Error("...")` | `is SavedRecipesState.Error ->` |

- `data object`는 인스턴스가 하나뿐이다. 그래서 이름만 써도 그 값이 된다.
- `data class`는 생성자를 호출해야 값이 생긴다. 이름만 쓰면 이런 에러가 난다.
  ```
  Classifier 'data class Error' does not have a companion object,
  so it cannot be used as an expression.
  ```
- 파라미터 없는 `data class Error : X`는 만들 수 없다. 파라미터가 없으면 `data object`로 쓴다.
  ```
  Data class must have at least one primary constructor parameter.
  ```

---

## 3. Repository: `Result` 반환 vs `throw`

**`Result`를 유지한다.**

| | `Result` 반환 | Repository에서 `throw` |
|---|---|---|
| 실패 가능성 | 타입에 드러남 | 타입만 봐서는 모름 |
| 처리 강제 | `when`에서 `Error`를 빼먹으면 컴파일 에러 | `try-catch`를 빼먹어도 컴파일됨 → 크래시 |
| 에러 종류 | `NetworkError` enum으로 정해짐 | `Exception`에서 직접 알아내야 함 |
| 코루틴 | 안전 | `catch (e: Exception)`이 코루틴 취소까지 삼키는 실수가 잦음 |

```kotlin
override suspend fun getRecipes(): Result<List<Recipe>, NetworkError> {
    try {
        val response = dataSource.getRecipes()

        when {
            response.statusCode.isServerError() -> return Result.Error(NetworkError.SERVER_ERR)
            response.statusCode.isClientError() -> return Result.Error(NetworkError.CLIENT_ERR)
        }

        val recipesDto = response.body ?: return Result.Error(NetworkError.NULL_DATA_ERR)

        return Result.Success(recipesDto.recipes.map { it.toModel() })
    } catch (_: SerializationException) {
        return Result.Error(NetworkError.PARSE_ERR)
    } catch (_: SocketException) {
        return Result.Error(NetworkError.NO_INTERNET_ERR)
    } catch (_: TimeoutCancellationException) {
        return Result.Error(NetworkError.TIMEOUT_ERR)
    } catch (_: Exception) {
        return Result.Error(NetworkError.UNKNOWN_ERR)
    }
}
```

`catch`는 구체적인 예외부터 쓴다. `Exception`이 앞에 있으면 아래 `catch`들은 실행되지 않는다.

---

## 4. 에러 문구는 presentation에 둔다

```kotlin
// presentation/NetworkErrorMessage.kt
fun NetworkError.toMessage(): String = when (this) {
    NetworkError.NO_INTERNET_ERR -> "인터넷 연결 확인"
    NetworkError.SERVER_ERR -> "서버 문제"
    ...
}
```

`NetworkError.kt`(core/network)에 넣지 않는 이유는 이렇다.

1. 문구는 디자인이나 기획이 바뀔 때 같이 바뀐다. 데이터 코드는 이런 변경에 영향받지 않게 둔다.
2. 나중에 `R.string.xxx`(UI 리소스)로 바꾸면 데이터 계층이 UI 리소스에 의존하게 된다.
3. 같은 에러라도 화면마다 다른 문구를 쓰고 싶을 수 있다.

`else` 없이 모든 경우를 적는다. 그러면 `NetworkError`에 값이 추가될 때 컴파일 에러가 나서 문구를 빼먹지 않는다.

---

## 5. `update { }` vs `.value =`

| 경우 | 예 | `update` |
|---|---|---|
| 이전 값을 읽어서 새 값을 만듦 | `ids + recipe.id` | 쓰면 좋음 |
| 이전 값과 상관없이 덮어씀 | `= Loading`, `= Success(...)` | 필요 없음 |

- `StateFlow.value = x`는 그 자체로 스레드 안전하다.
- `update`가 막는 건 읽기와 쓰기 사이에 다른 쓰기가 끼어드는 문제다.
  ```kotlin
  val ids = _ids.value        // A, B 둘 다 {1}을 읽음
  _ids.value = ids + 2        // A: {1, 2}
  _ids.value = ids + 3        // B: {1, 3} ← 2가 사라짐
  ```
- 클릭 이벤트와 `viewModelScope`는 메인 스레드에서 돈다. 그래서 지금 북마크는 `update` 없이도 안전하다. `update`는 나중을 대비한 습관이다.
- 응답 순서가 뒤바뀌는 문제(오래된 요청 결과가 나중에 도착)는 `update`로 막을 수 없다. 이전 `Job`을 취소해서 막는다.
  ```kotlin
  loadJob?.cancel()
  loadJob = viewModelScope.launch { ... }
  ```

자세한 내용은 [viewmodelscope-concurrency.md](viewmodelscope-concurrency.md)에 있다.

---

## 6. 가드 절, `!is`, `return@label`, 스마트 캐스트

```kotlin
_state.update { state ->
    if (state !is SavedRecipesState.Success) return@update state  // Success 아니면 그대로 반환

    state.copy(...)  // 여기부터 state는 Success로 스마트 캐스트됨
}
```

- `!is`는 `is`의 반대다. `!(x is T)`와 같다.
- `return@update`는 함수 전체가 아니라 `update { }` 람다만 끝내고 값을 반환한다.
- 먼저 걸러내고 빠져나가는 방식(early return)을 쓰면 핵심 로직의 들여쓰기가 줄어든다.

### `var by remember`는 스마트 캐스트가 안 된다

```kotlin
var state: SavedRecipesState by remember { ... }

if (state is SavedRecipesState.Success) {
    state.recipes  // ❌ 위임 프로퍼티라 값이 바뀔 수 있다고 보고 캐스트 안 해줌
}

val current = state  // val로 받으면 됨
if (current is SavedRecipesState.Success) {
    current.recipes  // ✅
}
```

---

## 7. 북마크는 화면 상태다

| | 화면 상태 (ViewModel) | 위젯 자체 상태 (`remember`) |
|---|---|---|
| 누가 알아야 하나 | ViewModel, Repository | 그 위젯만 |
| 데이터와 관련 있나 | 있음 | 없음 |
| 저장/전송 | 필요 | 불필요 |
| 예 | 목록, 검색어, 로딩, **북마크** | 눌림 효과, 드롭다운 열림, 스크롤 위치 |

북마크를 `remember`로 두면 안 되는 이유는 이렇다.

1. 나갈 때 저장해야 하는데, ViewModel이 그 값을 모른다.
2. `LazyColumn` 아이템 안의 `remember`는 스크롤해서 화면 밖으로 나가면 사라진다.
3. 화면을 회전하면 초기화된다.
4. "1번 레시피 북마크 해제"는 버튼 모양이 아니라 데이터에 대한 정보다.

### 별도 `StateFlow`로 분리

```kotlin
private val _state = MutableStateFlow<SavedRecipesState>(SavedRecipesState.Loading)
val state: StateFlow<SavedRecipesState> = _state.asStateFlow()

private val _unbookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
val unbookmarkedIds: StateFlow<Set<Long>> = _unbookmarkedIds.asStateFlow()

fun onBookmarkClick(recipe: Recipe) {
    _unbookmarkedIds.update { ids ->
        if (recipe.id in ids) ids - recipe.id else ids + recipe.id
    }
}
```

- `Success`인지 확인할 필요가 없어진다.
- 목록을 다시 불러와도 북마크 해제 목록이 초기화되지 않는다.
- 메모리에서 `Set`만 바꾸는 거라 `onBookmarkClick` 자체는 실패할 일이 없다.

### 나갈 때 반영 (예정)

```kotlin
// Root
LifecycleEventEffect(Lifecycle.Event.ON_STOP) { viewModel.saveBookmarks() }
```

- `onCleared()`는 쓰지 않는다. 그 시점에는 `viewModelScope`가 이미 취소돼 있어서 `suspend` 저장이 실행되지 않을 수 있다.
- `ON_STOP`은 화면 이동, 뒤로 가기, 홈 버튼 모두에서 불린다.

---

## 8. DTO 리스트는 빈 리스트를 기본값으로

```kotlin
@Serializable
data class RecipesResponseDto(
    val recipes: List<RecipeDto> = listOf(),
)
```

- Mapper와 Repository에서 `.orEmpty()`가 필요 없어진다.
- JSON에 키가 없으면 기본값 `listOf()`가 들어간다.
- JSON에 `"recipes": null`이 명시돼 오면 파싱 에러(`PARSE_ERR`)가 난다. 서버가 null을 보낼 수 있으면 `Json { coerceInputValues = true }`를 쓴다. 그러면 null이 기본값으로 바뀐다.

---

## 정리

- Repository는 `Result<T, NetworkError>`를 반환한다. 예외 대신 타입으로 실패를 알린다.
- ViewModel은 `Result`를 `SavedRecipesState`로 변환한다. 에러 문구는 `NetworkError.toMessage()`로 만든다.
- Screen은 `SavedRecipesState`만 보고 그린다. `Result`나 `NetworkError`는 모른다.
- 데이터가 없는 상태는 `data object`로, 데이터가 있는 상태는 `data class`로 만든다. `data class`는 `is`로 분기한다.
- `update { }`는 이전 값을 읽어서 쓸 때 쓴다. 단순히 덮어쓸 때는 `.value =`면 충분하다.
- 북마크처럼 저장해야 하는 상태는 화면 상태다. ViewModel에서 관리한다.
