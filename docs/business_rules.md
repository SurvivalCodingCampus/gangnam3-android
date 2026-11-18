# Food Recipe App — 핵심 비즈니스 규칙 (Spec)

> 기준 디자인: Figma *Food Recipe App ([Community](https://www.figma.com/design/gSeg6yi2QMN0GajM3diTb2/Food-Recipe-App--Community-?node-id=1-3&p=f&t=WGMtHBQPKKnawQeJ-0))* — Design 페이지  
> 모델/Repository 정의: `architecture.puml`  
> 이 문서는 **"무엇을 판정/가공하는가"** 만 다룬다. 비즈니스 규칙은 다이어그램에 그리지 않는다.  

---

## 0. 공통 규약

### 0-1. 결과 타입

```kotlin
sealed interface RootError

sealed interface Result<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}

sealed interface DataError : RootError {
    enum class Network : DataError { NO_INTERNET, TIMEOUT, UNAUTHORIZED, NOT_FOUND, CONFLICT, SERVER_ERROR, UNKNOWN }
    enum class Local : DataError { DISK_FULL, UNKNOWN }
}
```

| 구분 | 누가 만드나 | 예시 |
|---|---|---|
| **데이터 실패** (`DataError`) | Repository | 네트워크 끊김, 서버 5xx, 404, 409 |
| **규칙 실패** (`XxxError` enum) | 비즈니스 규칙 함수 | 비밀번호 불일치, 별점 범위 초과, 본인 레시피 평가 |

### 0-2. 공통 원칙

1. **빈 결과는 실패가 아니다.** 검색 결과 0건, 리뷰 0개는 `Success(emptyList())`. 빈 화면 문구는 UI가 정한다.
2. **값싼 검사 먼저.** 로컬에서 판정 가능한 규칙(입력값, 로그인 여부)을 네트워크 호출 전에 모두 끝낸다.
3. **Fail-fast.** 첫 번째로 위반한 규칙 하나를 반환한다. (필드별 동시 에러 표시가 필요해지면 `List<Error>`로 확장)
4. **데이터 실패는 그대로 통과**시키되, 규칙상 의미가 있는 코드(예: `CONFLICT`)만 규칙 에러로 **번역**한다.
5. 규칙 함수의 반환 타입은 `Result<성공데이터, RootError>`이고, 실제로 오는 에러는 `해당 규칙 Error enum | DataError.Network` 둘 중 하나다.
6. **보는 사람에 따라 달라지는 상태는 기본적으로 도메인 모델에 넣지 않는다.** 북마크 여부, 팔로우 여부는 `observeSavedRecipeIds()`, `observeFollowingIds()` Flow를 ViewModel에서 목록과 `combine`해 카드/버튼 UI 상태를 만든다. 한 화면에서 저장·팔로우를 바꾸면 Flow를 통해 다른 화면에도 자동으로 반영된다.
   - **명시적 예외: `Review.myReaction`.** 아래 두 조건을 모두 만족하는 상태만 모델에 담는다.
     1. **한 화면 안에서만 쓰인다.** 리뷰는 Reviews 화면에서만 보이므로 화면 간 동기화가 필요 없다 (북마크·팔로우는 홈, 검색, 상세, 프로필 등 여러 화면에 보여서 분리).
     2. **같은 모델의 집계값과 한 번에 바뀌어야 한다.** `setReaction` 응답(`Review`) 하나로 `likeCount`, `dislikeCount`, `myReaction`이 함께 갱신된다. 분리하면 카운트와 내 반응이 어긋나는 순간이 생긴다.
   - 상태 소유자는 `ReviewRepository` 하나다. 같은 리뷰에 대해 반응 Flow를 따로 두지 않는다.
   - 새로운 사용자별 상태를 모델에 넣으려면 위 두 조건을 근거로 이 예외 목록에 추가한다. 하나라도 못 채우면 Flow로 분리한다.

---

## 1. 화면 → 기능 → 처리 경로 한눈에 보기

| 화면 (Figma) | 기능 | 처리 경로 | 사용하는 부품 |
|---|---|---|---|
| Sign In | 이메일/소셜 로그인 | UI → Repo | `AuthRepository` |
| Sign up | **회원가입** | UI → **규칙 함수** → Repo | `AuthRepository` |
| Home | 카테고리 탭, 레시피 카드, New Recipes | UI → Repo (`searchRecipes("", SearchFilter(category=…, sort=NEWEST))` 직접 호출). 탭 목록은 UI 상수(`RecipeCategory` 중 홈에 보일 항목), "All" 탭은 `category = null` | `RecipeRepository`, `BookmarkRepository` |
| Search / Search Result / Filter | **레시피 검색** | UI → **규칙 함수** → Repo | `RecipeRepository`, `RecentSearchRepository` |
| Search (Recent Search) | 최근 검색 표시 | UI → Repo (저장된 id → `getRecipesByIds`) | `RecentSearchRepository`, `RecipeRepository` |
| Saved Recipe | 저장 목록 (Flow 관찰) | UI → Repo | `BookmarkRepository` |
| Recipe / Ingrident · Procedure | **레시피 상세 조회** | UI → Repo (직통) | `RecipeRepository.getRecipeDetail` |
| Recipe / More → Rate Recipe | **레시피 평점 남기기/수정** | UI → **규칙 함수** → Repo | `AuthRepository`, `RecipeRepository`, `RatingRepository` |
| Recipe / More → Share | 링크 복사 | UI only (`RecipeDetail.shareUrl` → 클립보드) | — |
| Recipe / More → Unsave | 저장 해제 | UI → Repo | `BookmarkRepository` |
| Reviews | **리뷰 목록 조회** | UI → Repo (직통) | `ReviewRepository` |
| Reviews | 리뷰 작성 | (후보) 규칙 함수 — 4-2절 참고 | `ReviewRepository` |
| Reviews | 리뷰 👍/👎 | (후보) 규칙 함수 — 4-2절 참고 | `ReviewRepository.setReaction` |
| Notifications (All/Read/Unread) | 알림 목록, 읽음 처리 | UI → Repo (탭 필터·날짜 그룹핑은 UI) | `NotificationRepository` |
| Profile | 프로필, 레시피 목록, 팔로우 | UI → Repo | `UserRepository`(팔로우 포함), `RecipeRepository` |

---

## 2. 단순 조회 — Repository 직통으로 충분한 기능

### 2-1. 레시피 상세 조회

| 항목 | 내용 |
|---|---|
| 목적 | 레시피 상세 화면(재료 / 조리 순서 탭)에 보여줄 레시피 한 건을 가져온다 |
| 필요한 부품 | `RecipeRepository.getRecipeDetail(recipeId)` |
| 입력값 | `recipeId: String` |
| 성공 | `RecipeDetail` — 카드 정보 `recipe`(셰프, 별점, 리뷰 수) + 상세 전용 `ingredients`, `procedures`(단계별 이미지 선택), `servings`, `savedCount`, `shareUrl` |
| 실패 | `DataError.Network` — 특히 `NOT_FOUND` (삭제된 레시피) |

**함수를 두지 않는 이유**: 판정·가공이 없다. `GetRecipeDetailUseCase`를 만들면 `repository.getRecipeDetail(id)`를 그대로 넘기는 위임 코드만 생긴다.
"1 serve", "10 Steps"(=`procedures.size`), Ingredient/Procedure 탭 전환은 **표시 로직**이므로 UI(ViewModel) 몫이다.

### 2-2. 리뷰 목록 조회

| 항목 | 내용 |
|---|---|
| 목적 | Reviews 화면에 레시피의 리뷰(댓글) 목록을 보여준다 |
| 필요한 부품 | `ReviewRepository.getReviews(recipeId)` |
| 입력값 | `recipeId: String` |
| 성공 | `List<Review>` (작성자 `UserSummary`, 내용, 좋아요/싫어요 수, 내 반응 `myReaction`, 작성 시각). **0건도 성공** |
| 실패 | `DataError.Network` |

**함수를 두지 않는 이유**: 서버가 준 순서 그대로 표시하면 된다. 헤더의 "200 Comments / 155 Saved"는 `Recipe.reviewCount` / `RecipeDetail.savedCount`를 표시만 한다.

> 같은 이유로 **저장 목록, 알림 목록, 프로필, 셰프의 레시피 목록, 팔로우 상태 조회**도 UI → Repository 직통이다.

---

## 3. 비즈니스 규칙 함수가 필요한 기능

### 3-1. 회원가입 `signUp`

**목적**: 가입 폼 입력을 검증하고, 통과하면 계정을 만든다.

**필요한 부품**

- `AuthRepository.signUp(name, email, password)`

**입력값**

| 파라미터 | 타입 | 출처 (Figma) |
|---|---|---|
| `name` | `String` | Name |
| `email` | `String` | Email |
| `password` | `String` | Password |
| `confirmPassword` | `String` | Confirm Password |
| `isTermsAccepted` | `Boolean` | "Accept terms & Condition" 체크박스 |

**비즈니스 규칙** (번호 순서 = 검사 순서)

| # | 규칙 | 위반 시 |
|---|---|---|
| R1 | `name.trim()` 길이가 **2~20자** | `INVALID_NAME` |
| R2 | `email.trim()`이 이메일 형식 (`local@domain.tld`) | `INVALID_EMAIL` |
| R3 | `password`는 **8자 이상, 영문과 숫자를 모두 포함** | `WEAK_PASSWORD` |
| R4 | `confirmPassword == password` | `PASSWORD_MISMATCH` |
| R5 | `isTermsAccepted == true` | `TERMS_NOT_ACCEPTED` |
| R6 | 정규화: 이름은 trim, 이메일은 trim + 소문자 변환 후 Repository에 전달 | — |
| R7 | Repository가 `CONFLICT`(409)를 반환하면 이미 가입된 이메일로 판정 | `EMAIL_ALREADY_REGISTERED` |

**결과물**

```kotlin
enum class SignUpError : RootError {
    INVALID_NAME,
    INVALID_EMAIL,
    WEAK_PASSWORD,
    PASSWORD_MISMATCH,
    TERMS_NOT_ACCEPTED,
    EMAIL_ALREADY_REGISTERED,
}

// 반환: Result<User, RootError>
//  - Success: 생성된 User
//  - Error  : SignUpError | DataError.Network (CONFLICT 외 나머지)
```

---

### 3-2. 레시피 검색 `searchRecipes`

**목적**: 검색 조건을 검증한 뒤, 검색어와 필터(Time / Rate / Category)로 레시피를 찾는다. 성공하면 결과를 "Recent Search"에 남긴다.

**필요한 부품**

- `RecipeRepository.searchRecipes(query, filter)` — 조건을 서버/Room 쿼리로 전달
- `RecentSearchRepository.saveRecentRecipeIds(recipeIds)` — 최근 검색 저장 (id만)

**입력값**

| 파라미터 | 타입 | 출처 (Figma) |
|---|---|---|
| `query` | `String` | Search recipe 입력창 ("Jollof rice") |
| `filter` | `SearchFilter` (기본값: `sort=ALL, rate=null, category=null`) | Filter Search 바텀시트 |

**비즈니스 규칙**

| # | 규칙 | 위반 시 / 결과 |
|---|---|---|
| R1 | 검색어는 앞뒤 공백 제거 후 사용 | — |
| R2 | 검색어 길이는 **최대 30자** | `QUERY_TOO_LONG` |
| R3 | 검색어가 비었고 필터도 기본값이면 검색하지 않는다 (UI는 Recent Search를 보여준다) | `EMPTY_CONDITION` |
| R4 | `filter.rate`는 `null` 또는 **1~5** | `INVALID_RATE` |
| R5 | *(검색 계약)* 매칭: `title` 또는 `chef.name`에 검색어 포함 (대소문자 무시). 검색어가 비었으면 전부 매칭 | — |
| R6 | *(검색 계약)* Category: `recipe.category == filter.category` (null이면 전체) | — |
| R7 | *(검색 계약)* Rate: **해당 점수대만** 포함 — `filter.rate <= recipe.rating < filter.rate + 1` (예: 4★ = 4.0~4.9). 5★는 만점이므로 `rating == 5.0`. 표시용 반올림 값이 아니라 원본 `rating`으로 판정한다 (4.96은 화면에 "5.0"으로 보여도 4★에 속함). null이면 전체 ※ 5장 Q1 확정 | — |
| R8 | *(검색 계약)* 정렬 — `ALL`: 원본 순서 / `NEWEST`: `createdAt` 내림차순 / `OLDEST`: 오름차순 / `POPULARITY`: `reviewCount` 내림차순, 같으면 `rating` 내림차순 | — |
| R9 | **결과 0건은 성공** (`Success(emptyList())`) | — |
| R10 | 결과가 1건 이상이면 **상위 10건의 id**를 최근 검색으로 저장. 레시피 객체를 통째로 저장하지 않으므로 별점·리뷰 수가 옛날 값으로 남지 않는다. 표시할 때 `getRecipesByIds`로 최신 데이터를 받고, 삭제된 레시피는 빠진다. 저장 실패(`DataError.Local`)는 검색 결과에 영향을 주지 않는다 (로그만) | — |

> **역할 분담**: R1~R4(검증)와 R9~R10은 규칙 함수가 실행한다. R5~R8은 *검색 계약*이다. 규칙이 무엇인지는 이 문서가 정하고, 실행은 `searchRecipes` 구현(서버 API 또는 Room 쿼리)이 맡는다. 전체 목록을 받아 앱 메모리에서 거르지 않는다. 구현이 계약을 지키는지는 Repository 구현체 테스트로 검증한다.

**결과물**

```kotlin
enum class SearchRecipesError : RootError {
    QUERY_TOO_LONG,
    EMPTY_CONDITION,
    INVALID_RATE,
}

// 반환: Result<List<Recipe>, RootError>
//  - Success: 필터·정렬된 레시피 목록 (0건 가능). "255 results"는 UI가 size로 표시
//  - Error  : SearchRecipesError | DataError.Network
```

---

### 3-3. 레시피 평점 남기기/수정 `rateRecipe`

**목적**: 상세 화면 More 메뉴 → "Rate recipe" 팝업에서 고른 별점을 제출한다. 이미 평가한 레시피라면 새 점수로 **수정**한다.

**필요한 부품**

- `AuthRepository.observeCurrentUser().first()` — 로그인 사용자 (현재 값 1회 사용)
- `RecipeRepository.getRecipeDetail(recipeId)` — 작성자(chef) 확인
- `RatingRepository.getMyRating(recipeId)` — 기존 평점 확인 (팝업 초기값 표시에도 사용)
- `RatingRepository.submitRating(recipeId, score)` — 제출 (생성/수정 겸용 upsert)

**입력값**

| 파라미터 | 타입 | 출처 (Figma) |
|---|---|---|
| `recipeId` | `String` | 현재 상세 화면 |
| `score` | `Int` | 별 1~5개 선택 (0개면 Send 버튼 비활성) |

**비즈니스 규칙**

| # | 규칙 | 위반 시 |
|---|---|---|
| R1 | 로그인한 사용자만 평가할 수 있다 | `NOT_LOGGED_IN` |
| R2 | `score`는 **1~5**. UI가 Send를 막더라도 규칙 함수에서 한 번 더 검사한다 | `INVALID_SCORE` |
| R3 | **자기 레시피는 평가할 수 없다** (`detail.recipe.chef.id == currentUser.id`) | `CANNOT_RATE_OWN_RECIPE` |
| R4 | **한 레시피에 1인 1개의 평점.** 기존 평점이 있으면 새 점수로 덮어쓴다(재평가 허용) ※ 5장 Q2 확정 | — |
| R5 | 기존 평점과 **같은 점수**면 서버에 보내지 않고 기존 `RecipeRating`을 그대로 성공으로 반환한다 | — |
| R6 | 평균 별점(`Recipe.rating`) 재계산은 **서버 책임**. 클라이언트는 제출 후 상세를 다시 조회한다 | — |

검사 순서: R1, R2(로컬) → 레시피 조회 → R3 → 내 평점 조회 → R5 → 제출(R4)

**결과물**

```kotlin
enum class RateRecipeError : RootError {
    NOT_LOGGED_IN,
    INVALID_SCORE,
    CANNOT_RATE_OWN_RECIPE,
}

// 반환: Result<RecipeRating, RootError>
//  - Success: 저장(생성 또는 수정)된 RecipeRating (recipeId, userId, score)
//  - Error  : RateRecipeError | DataError.Network (NOT_FOUND 포함)
```

---

## 4. 비판적 검토

### 4-1. 과잉 설계 걷어내기

| 처음 떠올린 설계 | 판단 | 최종 |
|---|---|---|
| `GetRecipeDetailUseCase`, `GetReviewsUseCase`, `GetCategoriesUseCase`, `GetSavedRecipesUseCase`, `GetNotificationsUseCase`, `GetProfileUseCase` | Repository 한 줄 위임뿐 | **제거** → UI → Repository 직통 |
| `ShareRecipeUseCase` + `RecipeLink` 모델 + `ShareRepository` | 링크는 서버가 주는 값, 복사는 플랫폼(클립보드) 기능 | **제거** → `Recipe.shareUrl` 필드 하나 |
| `Ingredient`(마스터) + `RecipeIngredient`(수량) 분리 | 재료만 따로 조회하는 화면이 없음 | **통합** → `Ingredient(name, imageUrl, amount: String)` |
| `amount`를 `(quantity: Double, unit: String)`으로 구조화 | 인분 조절 같은 수량 계산 기능이 디자인에 없음. "약간", "2개" 같은 표현도 표시만 하면 됨 | **문자열 하나** (`"500g"`, `"2 tbsp"`). 수량 계산 기능이 생기면 그때 구조화 |
| `CategoryRepository`(1개 메서드), `FollowRepository`(3개 메서드) 별도 정의 | 카테고리는 디자인에 고정된 값이라 서버에서 받아올 필요가 없음. 팔로우는 `User.followerCount`를 바꾸는 사용자 관계라 응집도가 같음 | **카테고리는 enum `RecipeCategory`로 전환**(조회 메서드 없음), 팔로우는 `UserRepository.follow/unfollow`로 **통합** |
| `RecipeCategory`에 `ALL` 포함 | "전체"는 카테고리가 아니라 필터 없음 상태. 넣으면 `Recipe.category = ALL` 같은 불가능한 상태가 생기고, `null`과 `ALL`이 둘 다 "전체"를 뜻하게 됨 | **제외** → `SearchFilter.category = null`이 전체. 서버가 모르는 값을 보내면 `UNKNOWN`으로 변환 |
| `Chef` 엔티티 별도 정의 | 카드의 셰프, 리뷰 작성자 모두 id·이름·사진만 씀 | **통합** → `UserSummary` 공용 |
| `SearchResult(recipes, totalCount)` | `totalCount == recipes.size` 중복 | **제거** → `List<Recipe>` |
| 알림 All/Read/Unread 필터, Today/Yesterday 그룹핑 함수 | 표시 방식이지 정책이 아님 | **UI(Presentation) 로직**으로 이동 |
| 홈 카테고리 탭 필터, New Recipes 정렬 함수 | `filter{}` / `sortedBy{}` 한 줄 | **UI 처리**. "7일 이내만 New" 같은 정책이 생기면 그때 함수로 분리 |
| (반대 방향) `Recipe` 하나에 상세 필드까지 전부 담기 | 목록·검색·저장 화면은 재료나 조리 단계를 쓰지 않는데 매번 함께 받게 됨 | **분리 도입** → 카드용 `Recipe` + 상세용 `RecipeDetail` |
| `toggleBookmark` / `toggleReaction` 같은 토글 메서드 | "현재 상태의 반대"를 정하는 판정이 Repository에 들어가고, 재시도하면 두 번 뒤집힘 | **사용 안 함** → `save/unsave`, `setReaction(목표 상태)` |

### 4-2. 비즈니스 규칙 누락 확인

| 기능 | 입력값 검증 | 최대 개수/길이 제한 | 권한 | 규칙 실패 Result | 빈 결과 처리 |
|---|---|---|---|---|---|
| 회원가입 | ✅ 이름·이메일·비밀번호·확인·약관 | ✅ 이름 2~20자, 비밀번호 8자+ | — | ✅ `SignUpError` 6종 (+409 번역) | — |
| 레시피 검색 | ✅ 검색어, rate 범위 | ✅ 검색어 30자, 최근 검색 10건 | — | ✅ `SearchRecipesError` 3종 | ✅ 0건 = 성공 |
| 평점 남기기/수정 | ✅ 1~5 | ✅ 1인 1개 (재평가 시 덮어쓰기, 같은 점수는 미전송) | ✅ 로그인, 본인 레시피 금지 | ✅ `RateRecipeError` 3종 | — |

모든 규칙 함수가 `DataError.Network` 외에 **규칙 실패 전용 enum**을 따로 가진다. 네트워크 실패와 규칙 실패가 섞이지 않아, UI는 "다시 시도" 안내와 "입력 수정" 안내를 구분해 보여줄 수 있다.

**검토 중 추가로 발견한 규칙 (다음 단계 후보)**

| 기능 | 규칙 | 에러 후보 |
|---|---|---|
| 리뷰 작성 (Reviews → Send) | 로그인 필요, 공백만 입력 금지, 최대 300자 | `NOT_LOGGED_IN`, `BLANK_CONTENT`, `CONTENT_TOO_LONG` |
| 팔로우 (Follow 버튼) | 자기 자신 팔로우 금지, 로그인 필요 | `CANNOT_FOLLOW_SELF`, `NOT_LOGGED_IN` |
| 레시피 저장 (북마크) | 로그인 필요, 최대 저장 개수(기획 미정) | `NOT_LOGGED_IN`, `SAVE_LIMIT_EXCEEDED` |
| 리뷰 👍/👎 `reactToReview(reviewId, tapped: LIKE \| DISLIKE)` | 로그인 필요, 본인 리뷰 반응 금지(기획 확인). **다음 상태 계산**: 현재 `myReaction`과 같은 버튼을 누르면 `NONE`(취소), 다른 버튼이면 그 반응으로 전환, `NONE`이면 누른 반응으로 설정. 계산된 목표 상태를 `setReaction`에 넘긴다 | `NOT_LOGGED_IN`, `CANNOT_REACT_OWN_REVIEW` |

### 4-3. 단일 책임 원칙(SRP) 점검 — Repository에 판정 코드가 섞이지 않았는가?

| 점검 항목 | 결과 |
|---|---|
| `RatingRepository.submitRating(score)`가 1~5 범위를 검사하는가? | ❌ 검사하지 않음 (규칙 함수 R2 책임). Repository는 받은 값을 저장만 한다 |
| `submitRating`이 "생성인지 수정인지", "같은 점수면 생략할지"를 판단하는가? | ❌ 서버 upsert로 저장만 한다. 같은 점수 생략(R5)은 규칙 함수가 정한다 |
| `ReviewRepository.setReaction`이 "다음 반응 상태"를 계산하는가? | ❌ 목표 상태(`LIKE`/`DISLIKE`/`NONE`)를 받아 그대로 저장한다. 취소·전환 계산은 규칙 함수 몫 |
| 보는 사람에 따라 달라지는 상태가 두 군데에 있는가? | ❌ 북마크 여부는 `observeSavedRecipeIds()`, 팔로우 여부는 `observeFollowingIds()` 하나만 출처로 둔다. `Recipe`/`User`에 `isBookmarked`/`isFollowing`을 넣지 않는다. 리뷰 반응은 0-2 원칙 6의 **명시적 예외**로 `Review.myReaction`에만 두고, 별도 Flow나 캐시를 만들지 않는다 |
| `AuthRepository.signUp`이 "이메일 중복"을 판정하는가? | ❌ `CONFLICT`라는 **데이터 사실**만 반환한다. "중복 이메일"이라는 의미는 규칙 함수가 붙인다 |
| `RecipeRepository.searchRecipes(query, filter)`가 입력을 검증하는가? | ❌ 검색어 길이, 조건 없음, rate 범위(R1~R4)는 규칙 함수가 검사한다. Repository는 검증된 조건을 서버/Room 쿼리로 **전달만** 한다 (R5~R8 계약 실행) |
| `RecentSearchRepository`가 10건 제한을 거는가? | ❌ 규칙 함수가 `take(10)` 해서 id를 넘긴다. Repository는 받은 id 목록을 그대로 저장한다 |
| `Recipe.rating` 평균을 클라이언트가 계산하는가? | ❌ 서버 집계 값을 그대로 쓴다 |
| Repository 반환 타입이 데이터 실패만 표현하는가? | ✅ 전부 `Result<T, DataError>`. 규칙 에러 enum은 Repository에 나오지 않는다 |

> **다음 단계**: 검색 결과가 수백 건("255 results")이므로 실제 구현에서는 `searchRecipes`에 페이지 파라미터(또는 Paging 3의 `Flow<PagingData<Recipe>>`)를 붙인다. 이때도 검증은 규칙 함수에 남는다.

### 4-4. 외부 반론 반영 내역

| 반론 | 판단 | 반영 |
|---|---|---|
| 목록/상세 모델 분리 | 수용 (이전 수정에서 반영) | `Recipe` + `RecipeDetail` |
| 최근 검색에 레시피 스냅샷 저장 → 옛날 데이터가 남음 | **수용** | id만 저장, `getRecipesByIds`로 복원 |
| 상태 변화 데이터를 Flow로 | **부분 수용** | 현재 사용자·팔로우 id를 Flow로 추가(북마크는 이전 수정에서 반영). **알림은 `Result` 유지**: 읽음 상태가 알림 화면 안에서만 바뀌고, 다른 화면에 미읽음 배지가 없다. 하단 탭에 배지가 생기면 Flow로 바꾼다 |
| `getRecipes()` 전수 조회 위험 | **수용** | `searchRecipes(query, filter)`로 교체, R5~R8은 검색 계약으로 전환 |
| Repository 파편화 | **부분 수용** | Category → enum `RecipeCategory`(Repository 불필요), Follow → `UserRepository`. **Rating은 분리 유지**: 별점과 리뷰는 디자인상 별도 흐름(팝업 vs 댓글 화면)이고, `rateRecipe` 규칙 함수가 리뷰 메서드까지 의존하게 되면 인터페이스 분리 원칙(ISP)에 어긋난다. 상세 화면 ViewModel은 Rating을 규칙 함수로만 쓰므로 직접 주입받는 Repository는 3개(Recipe, Bookmark, User)다 |
| `amountGram: Int` 경직성 | **수용** | `amount: String` |
| `getCurrentUser()` 동기 함수 | **수용** | `observeCurrentUser(): Flow<User?>`. 401로 세션이 만료되면 데이터 계층이 세션을 지우고 null을 방출 → 앱 전역에서 로그인 화면으로 이동 |
| `SearchFilter`에 query 없음 | **반론**: query는 시그니처로 해결 | `searchRecipes(query, filter)`. 검색어(입력창)와 필터(바텀시트)는 바뀌는 시점이 달라서 별도 값으로 둔다 |
| 👍/👎 메서드, OAuth 토큰, `Procedure.imageUrl` | 수용 (이전 수정에서 반영) | `setReaction`, `oauthToken`, `imageUrl` |
| `isBookmarked`/`isFollowing` 플래그 | **반론**: 모델이 아니라 Flow 결합으로 확정 (`Review.myReaction`만 조건부 예외) | 0-2 원칙 6 |
| `markAllAsRead()` | **반론**: 디자인에 "모두 읽음" 버튼이 없음 | 미반영 |
| `NotificationType` 확장성 | **부분 수용**: 화면에 없는 타입을 미리 만들지는 않음 | 서버가 새 타입을 추가해도 앱이 깨지지 않게 `UNKNOWN`만 추가 |

---

## 5. 기획 확인이 필요한 가정

| # | 질문 | 현재 가정 |
|---|---|---|
| Q1 | 필터의 Rate "4★"는 **4점 이상**인가, **4점대(4.0~4.9)**인가? | ✅ **확정: 4점대(4.0~4.9)** → 3-2 R7 |
| Q2 | 평점을 수정(재평가)할 수 있는가? | ✅ **확정: 가능** → 3-3 R4·R5, `submitRating` upsert |
| Q3 | 비밀번호 정책 (길이, 특수문자) | 8자 이상, 영문+숫자 |
| Q4 | "Recent Search"는 **최근 검색 결과**인가, **최근 본 레시피**인가? | 최근 검색 결과 상위 10건의 id를 로컬 저장, 표시할 때 최신 데이터로 복원 |
| Q5 | 리뷰의 👍/👎는 누를 수 있는가? | ✅ **확정: 가능** → `Review.myReaction`(0-2 원칙 6의 명시적 예외), `ReviewRepository.setReaction`, 4-2 반응 규칙. 본인 리뷰에 반응 가능한지는 추가 확인 필요 |
| Q6 | 프로필의 Videos / Tag 탭은 어떤 데이터인가? | Videos = `videoUrl != null`인 레시피 (UI 필터), Tag는 미정 |
| Q7 | Forgot Password 흐름이 있는가? | 범위 밖. 필요하면 `AuthRepository.requestPasswordReset(email)` 추가 |
| Q8 | 홈 하단 `+` 버튼(레시피 작성) 화면이 있는가? | 디자인에 없음 → 이번 설계에서 제외 |
| Q9 | 카테고리를 서버에서 관리하는가, 앱에 고정하는가? | ✅ **확정: 앱에 고정(enum `RecipeCategory`)**. 카테고리를 추가하려면 앱을 업데이트해야 하고, 업데이트 전 앱은 새 카테고리를 `UNKNOWN`으로 본다. 홈 탭과 필터 칩에 보일 목록, 화면 표시 이름(`LOCAL_DISH` → "Local Dish")은 UI가 정한다 |
