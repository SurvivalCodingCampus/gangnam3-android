# Food Recipe App (KodeCamp Task) — UI/UX 와이어프레임 & DB 설계 상세 명세서

**버전:** 2.0  
**작성일:** 2026-11-06  
**프로젝트:** KodeCamp Task Design (Food Recipe Mobile App)  
**플랫폼:** iOS / Android (모바일 우선, 375×812 기준)  
**디자인 콘셉트:** 모던 클래시(Modern Classy), 엄지 존(Thumb Zone) 최적화, 웜 톤(Warm-tone) 라이트 가이드라인

---

## 1. 디자인 시스템 가이드라인 (Design System)

### 1.1 컬러 팔레트 (Color Palette)

| 역할 | 헥스 코드 | 설명 / 사용처 |
| :--- | :--- | :--- |
| **Primary (브랜드)** | `#129575` / `#E23E3E` | 주요 버튼, 액션 하이라이트, 활성 탭 |
| **Secondary (보조)** | `#FFAD30` | 평점(Star), 뱃지, 북마크 아이콘, 경고 |
| **Neutral Dark** | `#121212` / `#292D32` | 메인 헤드라인 및 본문 텍스트 |
| **Neutral Medium** | `#A9A9A9` / `#D9D9D9` | 보조 텍스트, 비활성 아이콘, Border |
| **Neutral Light** | `#F1F1F1` / `#F8F8F8` | 검색바 배경, 카드 Surface, 약한 구분선 |
| **Background** | `#FFFFFF` | 앱 기본 배경색 |

### 1.2 타이포그래피 (Typography)

- **Title / Heading:** Poppins / Bold / 22px ~ 30px (메인 타이틀, 레시피 이름)
- **Subheading:** Poppins / SemiBold / 16px ~ 18px (섹션 제목, 카테고리)
- **Body:** Poppins / Regular / 14px (재료 설명, 본문)
- **Caption / Tag:** Poppins / Medium / 11px ~ 12px (조리 시간, 평점, 셰프 이름)

---

## 2. 데이터베이스 설계 (Database ERD & Architecture)

### 2.1 Entity Relationship Diagram (ERD 개념 구조)

```
[Users] 1 ───< [Recipes] 1 ───< [Recipe_Ingredients] >─── 1 [Ingredients]
  │               │ 1
  │               └───< [Recipe_Steps]
  │               │ 1
  │               └───< [Reviews]
  │
  └───< [Saved_Recipes] (Bookmarks)
  │
  └───< [User_Follows]
```

---

### 2.2 상세 테이블 명세 (Tables Schema)

#### 1) `Users` (사용자 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `user_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 사용자 고유 ID |
| `email` | VARCHAR(255) | UNIQUE, NOT NULL | 이메일 계정 |
| `password_hash` | VARCHAR(255) | NOT NULL | 암호화된 비밀번호 |
| `name` | VARCHAR(100) | NOT NULL | 사용자 이름 / 닉네임 |
| `profile_img` | VARCHAR(500) | NULL | 프로필 이미지 URL |
| `bio` | TEXT | NULL | 한 줄 소개 |
| `is_chef` | BOOLEAN | DEFAULT FALSE | 셰프 권한 여부 |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 가입 일시 |

#### 2) `Categories` (카테고리 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `category_id` | INT | PRIMARY KEY, AUTO_INCREMENT | 카테고리 ID |
| `name` | VARCHAR(50) | NOT NULL | 카테고리명 (Asian, Italian 등) |
| `icon_url` | VARCHAR(500) | NULL | 카테고리 아이콘 URL |

#### 3) `Recipes` (레시피 메인 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `recipe_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 레시피 고유 ID |
| `author_id` | BIGINT | FOREIGN KEY (`Users.user_id`) | 작성자(셰프) ID |
| `category_id` | INT | FOREIGN KEY (`Categories.category_id`) | 카테고리 ID |
| `title` | VARCHAR(200) | NOT NULL | 레시피 제목 |
| `description` | TEXT | NULL | 레시피 한줄 설명 |
| `thumbnail_url` | VARCHAR(500) | NOT NULL | 메인 대표 이미지 URL |
| `cooking_time` | INT | NOT NULL | 조리 시간 (분 단위) |
| `difficulty` | ENUM | ('Easy', 'Medium', 'Hard') | 난이도 |
| `calories` | INT | NULL | 칼로리 (kcal) |
| `serving_size` | INT | DEFAULT 1 | 기준 인원수 |
| `rating_avg` | DECIMAL(2,1) | DEFAULT 0.0 | 평균 평점 (1.0~5.0) |
| `review_count` | INT | DEFAULT 0 | 전체 리뷰 개수 |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 등록 일시 |

#### 4) `Ingredients` (재료 마스터 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `ingredient_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 재료 고유 ID |
| `name` | VARCHAR(100) | NOT NULL | 재료명 (예: 파스타 면, 계란) |
| `icon_url` | VARCHAR(500) | NULL | 재료 미니 아이콘 URL |

#### 5) `Recipe_Ingredients` (레시피별 재료 매핑 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `recipe_id` | BIGINT | FOREIGN KEY (`Recipes.recipe_id`) | 레시피 ID |
| `ingredient_id` | BIGINT | FOREIGN KEY (`Ingredients.ingredient_id`) | 재료 ID |
| `amount` | VARCHAR(50) | NOT NULL | 정량 표현 (예: 200g, 2개) |
| PRIMARY KEY | (`recipe_id`, `ingredient_id`) | | 복합 기본키 |

#### 6) `Recipe_Steps` (조리 순서 단계 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `step_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 단계 ID |
| `recipe_id` | BIGINT | FOREIGN KEY (`Recipes.recipe_id`) | 레시피 ID |
| `step_number` | INT | NOT NULL | 순서 (1, 2, 3...) |
| `instruction` | TEXT | NOT NULL | 조리 설명 문구 |
| `step_img_url` | VARCHAR(500) | NULL | 해당 단계 설명 이미지/동영상 URL |

#### 7) `Saved_Recipes` (북마크/저장 테이블)
| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| `user_id` | BIGINT | FOREIGN KEY (`Users.user_id`) | 사용자 ID |
| `recipe_id` | BIGINT | FOREIGN KEY (`Recipes.recipe_id`) | 레시피 ID |
| `saved_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 저장 일시 |
| PRIMARY KEY | (`user_id`, `recipe_id`) | | 복합 기본키 |

---

## 3. 화면별 상세 UI/UX 명세 (Screen Design)

### 3.1 스플래시 & 온보딩 화면 (Splash & Onboarding)

```
┌─────────────────────────────────────┐
│                                     │
│         [ 🍳 대형 히어로 포토 ]     │
│                                     │
│         Get Cooking                 │
│         Simple & Delicious Recipes  │
│                                     │
│  "10만 개 이상의 검증된 레시피로    │
│   오늘의 요리를 시작해보세요."      │
│                                     │
│  ┌───────────────────────────────┐  │
│  │     [ Start Cooking -> ]      │  │
│  └───────────────────────────────┘  │
│                                     │
└─────────────────────────────────────┘
```

- **목적:** 브랜드 시각 이미지 전달 및 메인 서비스 진입 유도
- **주요 UI 요소:**
  - 풀스크린 감성의 배경 레시피 히어로 이미지
  - 타이틀: "Get Cooking" (30px Bold)
  - 서브 타이틀: 캐치프레이즈 문구 (14px Regular)
  - **Start Cooking** CTA 버튼 (Primary Color `#129575` 적용, 클릭 시 홈 이동)

---

### 3.2 메인 홈 화면 (Home Screen)

```
┌─────────────────────────────────────┐
│  Hello, Alex 👋            [👤 Profile]│
│  What are you cooking today?        │
├─────────────────────────────────────┤
│  🔍 Search recipe...          [⚙️]  │
├─────────────────────────────────────┤
│  [All] [Asian] [Italian] [Mexican]  │
├─────────────────────────────────────┤
│  Featured Recipes                   │
│  ┌───────────────────────────────┐  │
│  │ [♥]  ⭐️ 4.8 (20 mins)         │  │
│  │                               │  │
│  │  Classic Spaghetti Carbonara  │  │
│  │  By Chef Mario                │  │
│  └───────────────────────────────┘  │
│                                     │
│  New Recipes                        │
│  ┌──────────┐ ┌──────────┐          │
│  │[Img]     │ │[Img]     │          │
│  │Steak     │ │Salad     │          │
│  └──────────┘ └──────────┘          │
├─────────────────────────────────────┤
│  [🏠 Home] [🔖 Saved] [🔔 Notification] [👤 Profile]│
└─────────────────────────────────────┘
```

- **목적:** 맞춤 레시피 탐색 및 빠른 검색 진입점 제공
- **주요 UI 요소:**
  - **상단 헤더:** 사용자 인사말 + 프로필 아바타 버튼 (프로필 화면 이동)
  - **검색 바:** 텍스트 입력 시 검색 결과 화면 전환 / 우측 ⚙️ 필터 모달 호출
  - **카테고리 칩 (Horizontal Scroll):** 선택 시 해당 카테고리 레시피 필터링
  - **Featured Recipes (가로 카드 뷰):** 대표 이미지, 북마크 아이콘(`Saved_Recipes` 연동), 평점 및 조리 시간 뱃지
  - **Bottom Navigation Bar:** 홈, 북마크, 알림, 프로필 아이콘 배치 (엄지 존 최적화)

---

### 3.3 레시피 상세 화면 (Recipe Detail Screen)

```
┌─────────────────────────────────────┐
│  [< Back]               [♥] [📤 Share]│
├─────────────────────────────────────┤
│                                     │
│         [ 🍝 음식 대형 포토 ]       │
│                                     │
├─────────────────────────────────────┤
│  Classic Spaghetti Carbonara        │
│  ⭐️ 4.8 (1.2k Reviews)               │
│                                     │
│  [👨‍🍳 Chef Mario]        [+ Follow] │
├─────────────────────────────────────┤
│  [ Ingredients (4) ]   [ Instructions ]
│                                     │
│  [v] Spaghettis Pasta       200g    │
│  [ ] Fresh Eggs             2 pcs   │
│  [ ] Parmesan Cheese        50g     │
│  [ ] Pancetta / Bacon       100g    │
│                                     │
│  ┌───────────────────────────────┐  │
│  │      [ Start Cooking ]        │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

- **목적:** 요리에 필요한 재료 준비 및 단계별 조리 지침 전달
- **주요 UI 요소:**
  - **히어로 포토:** 고화질 음식 사진
  - **상단 헤더 정보:** 요리 제목, 평균 평점 및 리뷰 수 (`rating_avg`, `review_count`)
  - **셰프 카드:** 작성자 이름 및 팔로우 토글 버튼 (`User_Follows` 연동)
  - **Segmented Control Tab:**
    - **Ingredients 탭:** 재료 체크리스트 (`Recipe_Ingredients` 및 `Ingredients` 조인)
    - **Instructions 탭:** 순서별 조리 설명 (`Recipe_Steps` 연동)
  - **하단 고정 CTA:** `[ Start Cooking ]` - 클릭 시 스텝별 전체 화면 조리 모드 진입

---

### 3.4 북마크 / 저장된 레시피 화면 (Saved / Bookmarked Recipes)

```
┌─────────────────────────────────────┐
│  Saved Recipes              [🗑️]    │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐  │
│  │ [Thumb]  Spaghetti Carbonara  │  │
│  │          ⭐️ 4.8 · 20 mins     │  │
│  │          By Chef Mario   [♥️] │  │
│  └───────────────────────────────┘  │
│                                     │
│  ┌───────────────────────────────┐  │
│  │ [Thumb]  Avocado Green Salad  │  │
│  │          ⭐️ 4.5 · 10 mins     │  │
│  │          By Sarah K.     [♥️] │  │
│  └───────────────────────────────┘  │
│                                     │
├─────────────────────────────────────┤
│  [🏠 Home] [🔖 Saved] [🔔 Notification] [👤 Profile]│
└─────────────────────────────────────┘
```

- **목적:** 사용자가 저장한 선호 레시피의 빠르게 재탐색 및 삭제 관리
- **주요 UI 요소:**
  - **리스트 아이템:** 썸네일 이미지(80x80px), 레시피 제목, 평점, 소요 시간, 셰프 정보
  - **북마크 토글 [♥️]:** 탭 시 해당 저장 레시피 토스트 안내 후 삭제 (`Saved_Recipes` 테이블 반영)

---

### 3.5 상세 검색 및 필터 모달 (Search Filter Sheet)

```
┌─────────────────────────────────────┐
│  Filter Search              [X Close]│
├─────────────────────────────────────┤
│  Time                               │
│  ( ) All   (•) < 15min  ( ) < 30min │
│                                     │
│  Rate                               │
│  [⭐️ 5] [⭐️ 4] [⭐️ 3] [⭐️ 2]        │
│                                     │
│  Category                           │
│  [Cereal] [Vegetable] [Dinner] [Local]│
│                                     │
│  ┌───────────────────────────────┐  │
│  │        [ Apply Filter ]       │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

- **목적:** 조리 시간, 평점, 카테고리 등 다중 조건에 따른 맞춤 레시피 추출
- **주요 UI 요소:**
  - **시간 조건 필터:** 라디오 버튼 선택 (`cooking_time`)
  - **평점 조건 필터:** 뱃지 선택 (`rating_avg`)
  - **카테고리 칩:** 다중 태그 선택 (`category_id`)
  - **Apply Filter 버튼:** 필터 조건 적용 후 메인 홈 검색 결과 업데이트

---

## 4. 데이터 플로우 & 사용자 흐름 (Data & User Flow)

```
[Onboarding Screen]
       │
       ▼ (Start Cooking)
[Home Screen] ───────────────────► [Filter Sheet Modal]
       │                                     │
       ├─────────────────┐                   ▼
       ▼                 ▼            [Filtered Results]
[Recipe Detail]   [Saved Recipes]
       │
       ▼ (Start Cooking Button)
[Step-by-Step Cooking Mode Screen]
```

1. **사용자 앱 접속:** Onboarding 화면을 거쳐 Home Screen 진입
2. **레시피 탐색 & 검색:** Home Screen에서 카테고리 칩 탭 또는 Search Bar / Filter Modal을 통해 원하는 `Recipes` 필터링
3. **상세 정보 확인:** 선택한 레시피의 Detail Screen으로 이동하여 `Ingredients` 및 `Recipe_Steps` 조회
4. **북마크:** 마음에 드는 레시피를 `Saved_Recipes`에 추가 후 Saved 화면에서 모아보기 관리
```

I have updated the document with both the comprehensive **Database Architecture (Schema/ERD)** and the detailed **UI Screen Specifications**. 

### Key Updates:
1. **DB Design (Data Architecture)**:
   - ERD mapping rules (`Users`, `Recipes`, `Categories`, `Ingredients`, `Recipe_Ingredients`, `Recipe_Steps`, `Saved_Recipes`).
   - Detailed schema tables with Data Types, Primary Keys, Foreign Keys, and Constraints.
2. **UI Screen Designs**:
   - Screen wireframes and UI descriptions (Splash/Onboarding, Main Home, Recipe Details, Saved Recipes, Search Filter Modal).
   - User Interaction & Data Flow mapping.