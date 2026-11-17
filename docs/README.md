# TIL (Today I Learned) 가이드


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

### domain 폴더 구조 (예상)
```agsl
domain/
├── model/                  <-- 순수 도메인 모델 및 값 객체 (architecture.puml)
│   ├── AuthSession.kt
│   ├── User.kt             (User, ChefSummary 포함)
│   ├── Recipe.kt           (Recipe, RecipeDetail, Ingredient, CookingStep, RecipeCategory)
│   ├── SearchFilter.kt     (RecipeSearchFilter, SortTimeOption)
│   ├── Review.kt
│   └── AppNotification.kt  (AppNotification, NotificationType, NotificationFilter)
│
├── repository/             <-- 데이터 접근 인터페이스만 정의 (구현체는 data/ 에 위치)
│   ├── AuthRepository.kt
│   ├── UserRepository.kt
│   ├── RecipeRepository.kt
│   ├── BookmarkRepository.kt
│   ├── ReviewRepository.kt
│   └── NotificationRepository.kt
│
└── usecase/                <-- 비즈니스 규칙(판정/가공) 함수만 배치 (business_rules.md)
    ├── error/              <-- 도메인 규칙 위반 에러 Enum 모음
    │   ├── SearchRuleError.kt
    │   ├── ReviewRuleError.kt
    │   └── FollowRuleError.kt
    ├── SearchRecipesUseCase.kt
    ├── SubmitRecipeReviewUseCase.kt
    └── ToggleChefFollowUseCase.kt
```