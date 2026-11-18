
### 프로젝트 폴더 구조

```agsl
FoodRecipeApp/                     <-- 프로젝트 최상위 루트
├── .git/
├── gradle/
├── docs/                          <-- 설계 문서 및 다이어그램 전용 폴더
│   ├── architecture.puml
│   └── business_rules.md
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/recipeapp/
│           │   ├── core/          <-- Result, DataError 등 공통 타입
│           │   ├── domain/        <-- architecture.puml에 정의한 모델 & Repository 인터페이스
│           │   ├── data/          <-- Repository 구현체, DTO, Local/Remote 데이터소스
│           │   └── presentation/  <-- UI, ViewModel, UiState
│           └── res/               <-- 앱 실행에 필요한 이미지, strings.xml 등
├── build.gradle.kts
├── settings.gradle.kts
└── README.md                      <-- 루트 README에서 docs/ 파일들로 링크 연결
```

### core 폴더 구조 (예상)
```agsl
core/
├── RootError.kt            <-- 모든 에러의 최상위 마커 (business_rules.md 0-1)
├── Result.kt               <-- Result<D, E : RootError> (Success / Error)
└── DataError.kt            <-- DataError.Network, DataError.Local (Repository가 반환하는 데이터 실패)
```

### domain 폴더 구조 (예상)
```agsl
domain/
├── model/                  <-- 순수 도메인 모델 및 값 객체 (architecture.puml)
│   ├── User.kt             (User, UserSummary)
│   ├── SocialProvider.kt
│   ├── Recipe.kt           (Recipe, RecipeDetail, Ingredient, Procedure, RecipeCategory)
│   ├── SearchFilter.kt     (SearchFilter, SortOption)
│   ├── Review.kt           (Review, ReviewReaction)
│   ├── RecipeRating.kt
│   └── Notification.kt     (Notification, NotificationType)
│
├── repository/             <-- 데이터 접근 인터페이스만 정의 (구현체는 data/ 에 위치)
│   ├── AuthRepository.kt
│   ├── UserRepository.kt           (프로필 + 팔로우)
│   ├── RecipeRepository.kt         (검색, 상세, 셰프별 목록)
│   ├── RecentSearchRepository.kt   (최근 검색 id, 로컬)
│   ├── BookmarkRepository.kt
│   ├── ReviewRepository.kt
│   ├── RatingRepository.kt
│   └── NotificationRepository.kt
│
└── usecase/                <-- 비즈니스 규칙(판정/가공) 함수만 배치 (business_rules.md §3)
    ├── error/              <-- 규칙 위반 에러 Enum (RootError 구현)
    │   ├── SignUpError.kt          ← §3-1  INVALID_NAME, INVALID_EMAIL, WEAK_PASSWORD,
    │   │                                   PASSWORD_MISMATCH, TERMS_NOT_ACCEPTED, EMAIL_ALREADY_REGISTERED
    │   ├── SearchRecipesError.kt   ← §3-2  QUERY_TOO_LONG, EMPTY_CONDITION, INVALID_RATE
    │   └── RateRecipeError.kt      ← §3-3  NOT_LOGGED_IN, INVALID_SCORE, CANNOT_RATE_OWN_RECIPE
    ├── SignUpUseCase.kt            ← §3-1  AuthRepository
    ├── SearchRecipesUseCase.kt     ← §3-2  RecipeRepository, RecentSearchRepository
    └── RateRecipeUseCase.kt        ← §3-3  AuthRepository, RecipeRepository, RatingRepository
```

위 3개가 `business_rules.md` §3에서 **확정된 규칙**입니다. 에러 Enum 이름과 값은 문서와 동일하게 유지합니다.

단순 조회(레시피 상세, 리뷰 목록 등 §2)는 UseCase를 만들지 않고 ViewModel에서 Repository를 직접 호출합니다.

### 후보 규칙 (미확정, 아직 구현하지 않음)

`business_rules.md` §4-2의 "다음 단계 후보"입니다. 기획이 확정되기 전까지 파일을 만들지 않습니다.

```agsl
(후보) domain/usecase/
    ├── error/
    │   ├── ReviewRuleError.kt      후보: 리뷰 작성 — NOT_LOGGED_IN, BLANK_CONTENT, CONTENT_TOO_LONG
    │   ├── ReactToReviewError.kt   후보: 리뷰 👍/👎 — NOT_LOGGED_IN, CANNOT_REACT_OWN_REVIEW (본인 리뷰 반응 가능 여부 미확정)
    │   ├── FollowRuleError.kt      후보: 팔로우 — NOT_LOGGED_IN, CANNOT_FOLLOW_SELF
    │   └── BookmarkRuleError.kt    후보: 북마크 — NOT_LOGGED_IN, SAVE_LIMIT_EXCEEDED (최대 저장 개수 미정)
    ├── SubmitReviewUseCase.kt
    ├── ReactToReviewUseCase.kt     현재 반응과 누른 버튼으로 다음 상태 계산 → setReaction
    ├── FollowChefUseCase.kt        follow / unfollow 중 무엇을 부를지 결정
    └── SaveRecipeUseCase.kt
```
