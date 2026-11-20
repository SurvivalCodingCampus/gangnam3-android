# sealed interface UI State와 `data object Loading`

```kotlin
sealed interface SavedRecipesState {
    data object Loading : SavedRecipesState

    data class Error(val error: RecipeError) : SavedRecipesState

    data class Success(
        val recipes: List<Recipe> = emptyList(),
        val unbookmarkedIds: Set<Long> = emptySet(),
    ) : SavedRecipesState
}
```

---

## 1. 왜 `Loading`을 `data object`(싱글톤)로 만들었나

> `Loading`은 **담을 데이터가 없음** → 인스턴스를 여러 개 만들 이유가 없음 → 하나만 두고 재사용.

### class로 만들면 생기는 문제

```kotlin
class Loading : SavedRecipesState   // 필드 없는 일반 class

val a = Loading()
val b = Loading()
a == b   // false ❌ 똑같은 "로딩 중"인데 다르다고 나옴
```

- 매번 `Loading()`으로 쓸데없이 객체를 새로 생성
- 내용이 같아도 `==`가 false → 비교·테스트에서 꼬임
- StateFlow는 값이 같으면 다시 내보내지 않는데, `==`가 false라 매번 다른 값으로 보고 내보냄 → 불필요한 리컴포지션
- `data class Loading()`은 불가능 → data class는 생성자 파라미터가 최소 1개 필요 (컴파일 에러)

### object로 만들면

```kotlin
data object Loading : SavedRecipesState

SavedRecipesState.Loading == SavedRecipesState.Loading   // true ✅ 항상 같은 객체
```

- 앱 전체에 객체가 딱 하나 → 메모리 낭비 없음
- 비교가 항상 정확함

### `object` vs `data object`

`data`를 붙이면 `toString()`이 보기 좋아짐.

```kotlin
object Loading      → println(Loading)  // "Loading@1a2b3c" (해시값)
data object Loading → println(Loading)  // "Loading"  ← 로그·디버깅할 때 편함
```

---

## 2. 활용 예시

**① 상태 대입: 괄호 없이 그대로 사용**

```kotlin
_state.value = SavedRecipesState.Loading
```

**② when 분기: `is` 없이 값으로 비교**

```kotlin
when (state) {
    SavedRecipesState.Loading -> CircularProgressIndicator()   // object는 하나뿐이라 is 필요 없음
    is SavedRecipesState.Error -> Text("에러")                  // class는 인스턴스가 여러 개라 is로 타입 체크
    is SavedRecipesState.Success -> RecipeList(state.recipes)
}
```

**③ 테스트: 그대로 비교**

```kotlin
assertEquals(SavedRecipesState.Loading, viewModel.state.value)
```

**④ 재시도할 때 재사용**

```kotlin
fun retry() {
    _state.value = SavedRecipesState.Loading   // 새로 만들 필요 없이 같은 객체 재사용
    loadSavedRecipes()
}
```

### 선택 기준

| 상태 | 담을 데이터 | 선택 |
|---|---|---|
| Loading | 없음 | `data object`: 하나만 있으면 충분 |
| Error | 어떤 에러인지 | `data class`: 매번 값이 다름 |
| Success | 레시피 목록 등 | `data class`: 매번 값이 다름 |

> 데이터가 없으면 `data object`, 데이터가 있으면 `data class`.

---

## 3. `isLoading: Boolean`을 쓰면 배타적 관계가 깨지나?

맞음. 다만 이유가 두 개 섞여 있어서 나눠서 봐야 함.

### ① 배타성이 보장되는 이유 → sealed interface로 나눴기 때문

```kotlin
// Boolean 방식: 필드끼리 따로 놀아서 모순 조합이 생김
data class State(
    val isLoading: Boolean,
    val error: RecipeError?,
    val recipes: List<Recipe>,
)

State(isLoading = true, error = SERVER_ERR, recipes = listOf(...))  // 로딩 중 + 에러 + 데이터?? 컴파일 통과 ❌
```

- 필드가 3개라 조합도 여러 개 나오는데, 그중 말이 되는 건 몇 개 안 됨
- 이상한 조합을 컴파일러가 막아주지 못함
- 그래서 UI에서 `if (isLoading) ... else if (error != null) ...` 같은 분기가 늘어남

```kotlin
// sealed 방식: 상태 자체가 하나의 타입
val state: SavedRecipesState = Loading   // Loading이면서 Error일 수 없음 ✅
```

- 상태가 한 번에 하나만 될 수 있음
- `when (state)`에서 케이스를 하나라도 빠뜨리면 컴파일 에러

### ② Loading을 object로 한 이유 → 데이터가 없어서

- 배타성과는 **별개**
- `class Loading`으로 만들어도 배타성은 그대로 유지됨
- `==` 문제랑 불필요한 객체 생성만 생김

---

## 정리

- **Boolean 대신 sealed** → 모순된 상태 조합을 막으려고 (배타성)
- **Loading을 class 대신 object로** → 담을 데이터가 없으니 하나만 있으면 돼서 (싱글톤)
