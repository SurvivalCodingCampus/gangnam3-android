# Food Recipe App — 프로젝트 기획안

- **작성일**: 2026-11-14
- **대상 프로젝트**: `/mnt/c/gangnam3-android` (`RecipeApp`, 패키지 `com.suwonsmartapp.recipeapp`, Kotlin + Jetpack Compose)
- **근거 소스**
  - `food_recipe_app_ui_ux_wireframe_specification.md` (v2.0, 2026-11-06) — **정본으로 채택**
  - Figma 링크 (`gSeg6yi2QMN0GajM3diTb2`, node-id=1-3) — **미추출**: 로그인/자바스크립트 렌더링 장벽. 명세를 1차 근거로 사용하며, 접근 가능해지면 §5 체크리스트로 대조 후 본 문서 갱신
- **문서 성격**: 명세 + 현재 구현 진단 + 로드맵 + 미결 결정 목록. 결정이 해소될 때마다 해당 표를 갱신한다.

---

## 0. 요약

> **현행 상태 갱신 (2026-11-20)**: 아래 §1–§5.7에는 이전 분석 시점 기록과 실제 현재 상태가 섞여 있다. 최신 확인에서 앱 소스 14개 파일에 병합 충돌 표식이 텍스트로 남아 있다(약 2,638줄 범위). 이는 Git의 미해결 인덱스 충돌이 아니라 이미 커밋된 파일 내용이므로, 파일별로 의도된 변경을 비교·통합해야 한다. 현재 브랜치 `student/01_박강원`, 작업 트리에 스테이징된 `FakeRecipeRepository.kt`, 수정된 `docs/2026-11-19.md`, 미추적 `.codex/`가 있다. `./gradlew assembleDebug`는 지정된 `JAVA_HOME=/mnt/c/Users/USER/.jdks/jbr-25.0.5.0.3` 경로가 유효하지 않아 실행 전에 중단됐다. 따라서 기존 로그의 빌드/테스트 성공은 현재 소스의 성공을 보증하지 않는다.

- **현재 개발 목표**: 기능 추가보다 우선 앱을 다시 빌드 가능한 상태로 복구하고, Room·화면·문자열·테마 변경을 일관되게 통합한 뒤 기준 테스트를 확보한다.
- **우선순위**: P0 복구(충돌 표식 조사/통합) → P1 도구체인과 빌드 재현 → P2 단위·계측 테스트 → P3 실제 기능 갭 및 UX → P4 제품 범위 결정/백엔드.
- 기존 로드맵은 과거 계획 스냅샷으로 유지한다. 아래 §3.1에 현시점 실행 순서를 추가하고, 복구 후 완료된 항목만 진행 로그에 기록한다.

- 명세는 **디자인 시스템(§1) + DB(§2, 테이블 7종 명세 완비) + 화면 5종 와이어프레임(§3.1–3.5) + 사용자 플로우(§4)** 를 정의한다. 플로우상 **스텝별 조리 모드 풀스크린**이 최종 단계로 언급되지만 §3에 독립 화면 명세는 없다.
- 저장소는 **수업 과제용 Android 앱**으로, 이미 명세 대비 상당 부분(테마/5개 화면/샘플 데이터/필터 순수함수+단위테스트)이 데모 구현되어 있다. 남은 격차는 "데모 → 제품/명세 정합" 성격이다: 이모지 썸네일 플레이스홀더, 로컬 `remember` 상태(회전/재진입 소실), 프로필/알림 플레이스홀더, 문자열 하드코딩, 기본 폰트(타이포 시스템), 조리 모드 no-op, 이미지 로더 부재.
- 진행 순서(합의): **① 기획안 문서화 → ② Figma 재확인 + 환경/빌드 기반 점검 → ③ P0 명세 정합화 착수**(폰트 → 조리 모드 → 이미지 로더 배선 → 상태 승격 → 문자열 리소스화 → 테스트/접근성 보강).
- 아래 §4의 결정 항목은 잠정 기본값으로 두고 진행하며, 되돌릴 수 있는 지점(인터페이스/추상화)을 유지한다.

---

## 1. 소스 분석

### 1.1 디자인 시스템 (명세 §1)
| 축 | 내용 |
| :--- | :--- |
| 컬러 | Primary `#129575`(버튼/액션/활성 탭) · `#E23E3E`(명세상 Primary 행에 병기, 구현은 보조 강조) · Secondary `#FFAD30`(별점/뱃지/북마크) · Neutral Dark `#121212`/`#292D32` · Medium `#A9A9A9`/`#D9D9D9` · Light `#F1F1F1`/`#F8F8F8` · Background `#FFFFFF` |
| 타이포 | Poppins — Bold 22–30(title) / SemiBold 16–18(subheading) / Regular 14(body) / Medium 11–12(caption). **현재 구현은 시스템 기본 폰트** |
| 기준/콘셉트 | 모바일 우선 **375×812**, Modern Classy, 엄지 존(Thumb Zone) 최적화, 웜톤 라이트 가이드라인. 다크는 명세에 정의 없음(구현은 근사치) |
| 구현 상태 | 라이트 팔레트 토큰화는 완료(`ui/theme/Color.kt`, `Theme.kt`). 다크 근사치·`dynamicColor` 옵션 존재(기본 off) |

### 1.2 화면 인벤토리와 현재 구현 상태
| 화면 (출처) | 목적 | 핵심 요소 | 현재 상태 |
| :--- | :--- | :--- | :--- |
| 스플래시/온보딩 (§3.1) | 브랜드 노출·진입 유도 | 히어로 이미지, "Get Cooking", Start Cooking CTA | 구현 (`OnboardingScreen`) |
| 홈 (§3.2) | 탐색·검색 진입점 | 인사+프로필, 검색바+필터 기어, 카테고리 칩, Featured 가로 카드, New 2열 그리드 | 구현 (`HomeScreen` + `AppRoot`). 엄지존 하단 탭은 AppRoot |
| 레시피 상세 (§3.3) | 재료 준비·단계 안내 | 히어로, 평점/리뷰수, 셰프+팔로우, 재료/단계 세그먼트 탭, 하단 Start Cooking | 부분 구현: 팔로우/재료 체크는 로컬 상태, **"Start Cooking"은 no-op 주석** |
| 저장 목록 (§3.4) | 저장 레시피 재탐색·해제 | 썸네일(80×80), 제목, 평점·시간, 셰프, 하트 토글, 전체 삭제 | 구현 (`SavedScreen`), 로컬 상태 |
| 필터 시트 (§3.5) | 시간/평점/카테고리 다중 조건 | 라디오(<15/<30), 평점 뱃지, 카테고리 멀티칩, Apply | 구현 (`SearchFilterSheet`) |
| 프로필/알림 (홈 탭) | — | — | **플레이스홀더** (`PlaceholderScreen`) |
| 스텝별 조리 모드 (§4 플로우) | 단계별 전체화면 조리 | — | **구현(2026-11-14, P0)**: `CookingModeScreen` — 진행 표시·단계 텍스트·이미지 슬롯·이전/다음/완료, 상태 없는 뷰어(호출자 AppRoot가 인덱스 소유) |
| 로그인/회원가입/셰프 권한 | — | — | 명세 화면 목록에 없음. `Users` 스키마에 `email`/`password_hash`/`is_chef` 존재 → 범위 결정 필요(§4 D3) |

### 1.3 데이터 모델 (명세 §2) 및 갭
- 테이블 7종 명세 완비: `Users`(셰프 플래그) · `Categories` · `Recipes` · `Ingredients` · `Recipe_Ingredients`(복합PK) · `Recipe_Steps` · `Saved_Recipes`(복합PK).
- **갭/불일치(기획 확인 필요)**
  1. ERD에만 있는 `Reviews`·`User_Follows` — 상세 명세 없음. `Recipes.rating_avg`/`review_count`는 **집계값(denormalized)**.
  2. 필드 정의만 있고 화면/필터 미노출: `difficulty`, `calories`, `serving_size`, `description`, `step_img_url` 등.
  3. §3.5 필터 예시 카테고리(`Cereal/Vegetable/Dinner/Local`) ↔ §3.2 칩(`All/Asian/Italian/Mexican`) 불일치 → 샘플/기획 확정 필요.
  4. `Recipe_Ingredients.amount`는 문자열(`200g`, `2 pcs`) — 정량 정규화 여부 미정.
  5. 좌표는 `id: String` 기반 샘플 데이터(`"r1"`) ↔ 스키마 `BIGINT AUTO_INCREMENT` — 데이터 레이어 도입 시 매핑 방식 결정.

### 1.4 데이터 플로우 (명세 §4)
온보딩 → 홈 ⇄ 필터 시트(결과 갱신) / 홈 → 상세 · 저장, 상세 → (Start Cooking) 조리 모드. 현재 구현은 홈 내 검색·필터 결과 리스트 방식(§3.5의 "메인 홈 검색 결과 업데이트"와 정합).

---

## 2. 구현 진단 (기획 시점 스냅샷 — P0 이후 해소 항목은 §3·진행 로그 참조)

- **스택/설정**: AGP 9.5.1, Kotlin 2.3.20(+compose 플러그인; 2026-11-15 정합 조정 — Coil 3.6.3의 stdlib 2.4.10 요구를 피해 3.4.0(2.3.10)으로, kapt는 AGP 9.5 built-in Kotlin 비호환·KSP 2.4.x 미출시로 KSP 2.3.14 채택), Compose BOM 2026.02.01, Material3, compileSdk 37 / minSdk 24 / targetSdk 37, edge-to-edge, `supportsRtl="true"`, `configuration-cache=true`. 테스트: JUnit 4 + compose ui-test 의존성 존재.
- **구현됨**: 브랜드 컬러/라이트(+다크 근사치) 테마, `MainActivity` + `AppRoot`(첫 실행 온보딩 게이트·탭·라우팅·필터 시트·Room 연결), 6개 화면, Room v2(빈 DB 시딩·북마크 영속화), 샘플 6레시피, 순수 함수 `Recipe.matches` + `formatRating`/`formatReviews`(로케일 무관), `RecipeFilterTest` 8건.
- **한계/격차(위 §1.2 외)**
  - 썸네일: (진단 시점) 이모지 플레이스홀더(`RecipeMedia`) — **P0.3(2026-11-14)에서 이미지 로더 배선 완료**.
  - 상태: (진단 시점) 북마크/팔로우/재료 체크가 컴포저블 로컬 상태 — 회전·재진입 시 소실(동료 주석에 "실제 앱은 상태 저장소/ViewModel로 승격" 명시). 북마크는 **P0.4(2026-11-14)에서 회전 생존(`rememberSaveable`)**; 팔로우/재료 체크 리프트·영구 저장은 P1 게이트(D5). 내비게이션 수동 `when`.
  - 문자열: (진단 시점) 하드코딩, `strings.xml`은 `app_name`만 — **P0.5에서 리소스화 완료**.
  - 타이포: (진단 시점) 기본값 위주 — **P0.1에서 Poppins(OFL) 적용**.
  - 테스트: 순수 로직 위주(8건). 화면/UI 테스트는 의존성만 있고 미작성으로 보임.
  - 접근성/상태: 이미지 로딩/실패 상태는 로더+정규화로 처리(P0.3); **터치 타깃 1차 점검(2026-11-14, P0.6-점검)** — 홈 프로필 아바타 40→48dp 히트영역(시각 원 40dp 유지·라벨 추가); 스톡 M3 컴포넌트는 기본 높이 정합. **대화형 요소 콘텐츠 설명 부여(2026-11-15)** — 홈 검색창 필터("필터 열기")·레시피 행("레시피 상세 열기: <제목>"); 프로필·하트 기존 라벨 확인. 잔여: 글리프 심볼(🔍/⚙️) 컨트롤 육안 확인, 대비·빈 상태 세부(Saved `Text("")` 정리 후보).
- **Git/WIP 주의**
  - 현재 `master`에 있던 **미커밋 변경 다수**(2026-11-15 `student/01_박강원` 생성으로 동반 이동): 신규(`AppRoot.kt`, `data/`, `screens/`, `test/data/`, `docs/2026-11-12.md`, 명세 md 2부), 수정(`MainActivity.kt`, `ui/theme/Color.kt`·`Theme.kt`, `gradlew`, `docs/README.md`, `app/src/ui/*`), 삭제 다수(경로 중복 `app/src/MainActivity.kt`, `UIExam.kt` 일부, `app/src/ui/theme/AppColors`·`AppTexStyles`, `app/src/presentation/main_screen.dart`, `docs/2026-11.12.md` 등 — **삭제 파일 내용 미확인·HEAD에서 복원 가능**).
   - 원격·docs 실측(2026-11-15): `origin`=https://github.com/kwpark0047/gangnam3-android.git, `upstream`=https://github.com/SurvivalCodingCampus/gangnam3-android.git(추적 ref 존재, 현재 `master`); 저장소 `docs/` 이미 존재(`2026-11-11.md`·`2026-11-12.md`·`README.md`=TIL 가이드) — 계획서 이관처 후보. 같은 날 원격 `recipe`(kwpark0047/recipe) 추가·push 및 계획서 copy(`docs/RecipeApp_Project_Plan.md`) 실행 — 정본 운영 표기(ⓐ/ⓑ/ⓒ)는 확인 대기.
  - 일부 파일 CRLF 정규화 경고(LF/CRLF 혼재).
  - README 규칙: 작업 브랜치 형식 `student/01_이름`, **PR base = 본인 브랜치, `master` 금지**, PR 제목 `[YYYY-MM-DD] 이름 - 과제 제출`, 본문에 구현 내용/캡처/AI 인사이트/회고. → 브랜치 분리·이름은 사용자 확인 후 진행이었으나 2026-11-15 브랜치 생성·원격 추가로 실행됨(이름 `student/01_박강원`, 번호 `01`은 관례 기반 — 상이 시 푸시 전 변경 가능).

---

## 3. 목표 아키텍처와 로드맵

### 3.1 현시점 실행 계획 (2026-11-20)

| 단계 | 목표 및 작업 | 완료 조건 / 증거 | 우선순위 |
| :--- | :--- | :--- | :--- |
| A. 통합 기준 확정 | 14개 파일의 각 충돌 블록을 부모 커밋 양쪽과 비교한다. 의존관계별로 모델/DAO/Room, 공통 UI/테마, 화면, 리소스를 순서대로 정리한다. `HEAD` 쪽 Room migration·Flow·시딩 변경 및 다른 쪽의 화면/모델 개선을 보존할 통합안을 만든다. | `app/src`에서 충돌 표식 검색 결과 0건, diff로 빠진 동작이 없는지 확인 | P0 |
| B. 도구체인 복구 | 설치된 JDK를 확인하고 Gradle이 사용하는 JDK 경로를 현재 환경에서만 유효하게 설정한다. 프로젝트 파일/사용자 전역 설정은 임의 변경하지 않는다. | `./gradlew --version`에서 JVM 확인, `:app:assembleDebug` 성공 | P0 |
| C. 기준 테스트 복구 | 기존 유닛 및 계측 테스트를 실행한다. 첫 실패는 실패 로그와 테스트/코드 소유 계층으로 분류해 수정한다. | `:app:testDebugUnitTest` 성공, 가능한 환경에서 `:app:connectedDebugAndroidTest` 성공 또는 기기 부재를 기록 | P0 |
| D. 앱 흐름 통합 확인 | 온보딩 → 홈 → 검색/필터 → 상세 → 조리 모드 → 저장 목록을 점검하고, 앱 재시작 후 북마크·DB 시딩 동작을 확인한다. | 화면 흐름/저장 데이터 결과 기록; 실제 이미지 URL 부재 등 제한 명시 | P1 |
| E. 아키텍처 수렴 | 현재 `AppRoot`가 데이터 접근/화면 상태를 직접 소유하는지 재확인한다. 중복된 초기 구현 경로(`app/src/presentation`, `app/src/data`와 실제 앱 패키지 `app/src/main/java/com/suwonsmartapp/recipeapp`)를 분류하고, 사용 여부 확인 전 삭제하지 않는다. RecipeRepository 인터페이스와 Room/샘플/Fake 구현의 계약을 정리한다. | 실제 앱 진입에서 사용하는 경로와 테스트 대상이 일치, 중복·미사용 경로 목록 및 삭제 근거 확보 | P1 |
| F. 제품 갭 해결 | 세부 상태의 회전/재진입 유지, 프로필·알림 범위, 이미지 URL과 오류/빈 상태, 접근성 및 화면 명세 불일치를 우선순위화한다. | §4 결정표 갱신 후 각각 작고 검증 가능한 변경으로 진행 | P2 |
| G. 제품 범위 결정 | Reviews/User_Follows, 로그인/셰프 권한, 서버/API 연동, 동기화·오프라인 정책을 확정한다. | 데이터/API 계약 및 명세 갱신; 그 뒤에만 서버 통합 착수 | P3 |

**실행 규칙**: 이 계획의 첫 변경은 충돌 표식이 있는 소스 통합이다. 한 파일의 양쪽을 통째로 선택하지 않고 관련 커밋 및 참조 사용처를 대조한다. 사용자가 이미 가진 변경(`FakeRecipeRepository.kt`, `docs/2026-11-19.md`, `.codex/`)은 보존한다. 빌드와 테스트는 현재 JDK 장애를 해소한 뒤 실행한다.

**원칙**: UI는 데이터 소스에 대해 무지(stateless + 콜백/상태호이스틀링 — 저장소 TIL 규칙과 일치). 썸네일/데이터 소스는 인터페이스 뒤로 교체 가능하게. 명세 필드와 모델 매핑을 문서로 유지.

### P0 — 명세 정합화 (데모 → 스펙 완성)
1. **타이포**: Poppins(OFL) 자산 등록 + 라이선스 동봉 — **완료(2026-11-14)**: `assets/fonts/Poppins-{Regular,Medium,SemiBold,Bold}.ttf` + `OFL.txt`, `poppinsTypography` 구현 + 로드 실패 폴백, 테마 연결(컴파일 검증 완료).
2. **조리 모드 화면**: 상세 CTA 연동, 스텝별 전체화면(진행 표시/인덱스, 단계 이미지 슬롯(플레이스홀더 허용), 완료 시 복귀). 명세에 독립 화면 없음 → 와이어프레임 수준 our own 결정, §4 D-기록. **구현 완료(2026-11-14)**, 컴파일 검증.
3. **이미지 로더 배선**: **완료(2026-11-14)** — `io.coil-kt.coil3:coil-compose` + `coil-network-okhttp`(좌표 `3.4.0`으로 정합 조정: 3.6.3의 stdlib 2.4.10 요구가 KSP 2.4.x 미출시·kapt 비호환 조합과 충돌 — 중앙 저장소 메타데이터/POM/jar로 좌표·패키지 `coil3.compose` 검증). 비-null `thumbnailUrl` → `coil3.compose.AsyncImage`(로딩/실패/성공은 로더 처리), 빈/공백 URL→null 정규화 후 기존 이모지/틴트 자리표시 유지. 샘플 데이터에 URL이 없어 시각적 변화 0 — 실제 URL 채움은 **D2 확정('서버 제공', 2026-11-15)**에 따른 데이터 확충 시점.
4. **상태 승격**: 북마크 회전 생존 — **완료(2026-11-14, rememberSaveable)** → **Room 영속화 완료(2026-11-15, §5.2)**; 팔로우/재료 체크 리프트 + 영구 저장 여부는 P1 게이트(D5)로 유지.
5. **문자열 리소스화**: 하드코딩 텍스트 → `strings.xml`(다국어 대비) — **완료(2026-11-14)**, 7개 화면 이관, 컴파일 검증.
6. **테스트/접근성 보강**: 기존 순수 로직 테스트 **8+1 통과(2026-11-14, failures=0)**. 화면 테스트 경로는 **androidTest/에뮬레이터·실기기**로 확정(로컬 검증 2026-11-15 — `emulator`·`system-images/android-34/google_apis/x86_64`·사용자 AVD `wemarket_test`·`/dev/kvm`·`platform-tools/adb` 존재, 다운로드 불필요; 디스크 여유 563M로 신규 설치 비권장). 기존 `androidTest/com/example/gangnam3` 3종은 자체 컴포저블 테스트(앱 클래스 무관, 실행 시 최종 판정). 실행: 에뮬레이터/기기 부팅 후 `./gradlew :app:connectedDebugAndroidTest`. 터치 타깃 1차 코드 점검 완료(홈 프로필 아바타 40→48dp 히트영역·라벨) + 대화형 요소 콘텐츠 설명 부여(검색 필터·레시피 행) — 잔여: 글리프 심볼 컨트롤·대비·빈 상태 세부 육안 점검(아래 P2).

### P1 — 데이터 레이어
- **P1-α(2026-11-14): 저장소 인터페이스 스켈레톤** — `RecipeRepository.recipes()` + 샘플 구현체(`SampleRecipeRepository`); AppRoot·Home·Saved 호출부 전환(컴파일·단위 테스트 검증). 검색/필터(`Recipe.matches`)·정렬 등 순수 로직과 데모 큐레이션(`sampleFeatured`/`sampleNew`)은 저장소 무관 계층으로 유지. 네트워크/로컬(Room 등) 구현체와 모델↔테이블 매핑 확정(§1.3)은 **D4 게이트 유지**.
- **P1-β(2026-11-15): Room 골격 구현** — `RecipeEntity`(recipes 테이블)·`RecipeDao`·`RecipeDatabase`(v1)·`RoomRecipeRepository`(org.json 직렬화, `Context` 필요) — **Room 2.8.5 + KSP 2.3.14 / Kotlin 2.3.20 / Coil 3.4.0** 정합 재컴파일·단위 테스트 성공. 기본값 교체(시딩/동기화 포함)는 당시 P1 게이트 유지.
- **P1-γ(2026-11-15): Room 기본값 전환** — `saved_recipes` 테이블(v2)·`MIGRATION_1_2`·빈 DB 트랜잭션 시딩·Flow 관찰(`observeRecipes`/`observeBookmarks`)·IO 북마크 토글/전체 해제. `AppRoot`가 Room을 기본 저장소로 사용하고 첫 방출 전 로딩 인디케이터로 빈 목록을 가린다. 샘플 저장소는 시딩 원본·프리뷰/테스트 전용. 컴파일·단위 테스트·androidTest 컴파일 통과, 기기 실행은 미검증(§5.2).
- 결정 게이트: ① `Reviews`/`User_Follows` 테이블 신설 vs 집계값/클라이언트 상태 유지 ② 로컬 영속(Room) 여부 — 오프라인 요구·백업 정책 기준 ③ 샘플 데이터 규모/출처.

### P2 — 제품 완성도
- 검색·정렬.
- 상태 전수(로딩/오류/빈/이미지 실패) 재점검 — 이미지 3상태는 P0.3 로더로 처리됨(샘플 URL 없어 발현은 데이터 확충 시).
- 접근성/대비/터치 타깃 48dp 수동 점검(체크리스트화 2026-11-15): 글리프 심볼 컨트롤(⭐️/📤/🗑️ 등) 육안 확인, 텍스트 대비, Saved 빈 상태 문구·여백 등.
- 다크 팔레트 정교화 — 현 구현은 근사치(명세에 다크 정의 부재, §5).
- RTL 검증 — 콘텐츠/방향 전환 확보 후(현재 검증 대상 콘텐츠 없음).

### P3(선택) — 백엔드 연동
저장소 인터페이스 뒤 구현체 교체. **스코프/일정/담당 미정의 — 결정 필요(D4)**. 스키마상 계정(이메일/비밀번호 해시) 존재하므로 인증 포함 여부 명시 결정.

---

## 4. 미결 결정 목록 (잠정 기본값 — 필요 시 사용자 확정)

| ID | 항목 | 선택지 | 잠정 기본값/현재 상태 | 영향 | 필요 시점 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| D1 | Figma 원본 대조 | 접근 가능/불가 | **불가(미추출)** — 명세 기준 진행; 접근 시 §5 체크리스트로 대조 후 갱신 | 시각 정합성 | 기회 될 때 |
| D2 | 이미지 소스·라이선스 | 실사진/스톡/직접 제작/앱·서버 제공 등 | **확정(2026-11-15): 서버 제공** — 로더 배선 완료(소스 무관, 2026-11-14); 실제 URL 채움은 데이터 확충 시(샘플 없어 현재 시각 변화 0). 라이선스 검증 의무는 유지 | 썸네일 렌더(실제 URL 채움) | 실제 데이터 확충 시 |
| D3 | 계정/인증/셰프 권한 | 포함/제외 | **제외 가정**(현행 데모 유지) — `is_chef` 쓰기 권한 필요해지면 재검토 | 범위(P3 인접) | 백엔드/권한 기능 전 |
| D4 | 데이터 소유 | 로컬 앱 완결/백엔드 연동 | **확정(2026-11-15): 로컬+Room** — `RecipeRepository` 인터페이스(2026-11-14, P1-α)로 교체 경로 확보 + **Room 2.8.5/KSP 2.3.14 골격 구현(2026-11-15, P1-β)**; 기본값 교체·빈 DB 시딩 완료(2026-11-15, P1-γ), 서버 동기화만 미결 | P1/P3 | P3 진입 시 |
| D5 | 리뷰/팔로우 저장 전략 | 테이블 신설+집계 정합/집계값 유지+팔로우 클라이언트 | **확정(2026-11-15): 현행 유지(집계값+로컬 상태)** — 명세 상세 화면/필드 부재·집계값 존재·리뷰 기능 요구 미확인 근거; 리뷰 기능/실데이터 신호 시 재검토 | P1 | P1 |
| D6 | 온보딩 카피 "10만 개 이상 검증된 레시피" | 유지/수정 | **유지**(데모) — 실제 데이터 규모와 정합성 점검은 데이터 확충 시 | 문구/신뢰 | 데이터 확충 시 |
| D7 | 난이도/칼로리/인분 등 노출 | 노출/미노출 | **미노출 유지**(현행) | 화면/필터 | P2 이전 |
| D8 | 브랜드 폰트 | Poppins(OFL) 적용/시스템 폴백 | **적용 완료(2026-11-14)** — assets/fonts + OFL 동봉, 로드 실패 폴백, 컴파일 검증 | 타이포 | — |
| D9 | 작업 위치/브랜치 | 기획안 워크스페이스 유지/저장소 `docs/` 이관, 브랜치 시점·이름 | **실행(2026-11-15)**: 브랜치 `student/01_박강원` 생성(master 미커밋 WIP 31건 동반 이동; 삭제 6건 동반 — 내용 미확인·HEAD에서 복원 가능)·계획서 copy→`docs/RecipeApp_Project_Plan.md`·원격 `recipe`(kwpark0047/recipe) 추가 후 push(사전 read-only `git ls-remote`로 main `04ae32a…` 확인) — ① 문서 정본 운영 표기(ⓐ/ⓑ/ⓒ)·브랜치 번호 `01`은 확인 대기(푸시 전 변경 가능) | 협업/PR/이력 | 지금 |

---

## 5. Figma 육안 검증 체크리스트 (접근 가능해질 경우)

- 화면별 프레임/레이아웃 그리드와 375pt 기준 대응(스페이싱 토큰).
- 타이포 크기·웨이트·행간 명세 대조(§1.2), Poppins 적용 전 대체 표시 여부.
- 컬러 토큰 대조(§1.1), 이미지 비율/코너 반경/로딩 표현.
- 칩/필터/세그먼트 탭/하트/프로필 아바타 스타일, 엄지존 하단 탭 배치.
- 명세 Δ: 와이어프레임에 없는 요소 또는 명세와 다른 인터랙션 발견 시 기록 → 본 문서 갱신.
- 다크 모드 정의 부재 확인(명세 기준) — 구현 근사치의 근거로만 사용.

---

## 5.1 UI/UX 재점검 및 구현 (2026-11-15)

- **근거 범위**: 초기에는 Figma 링크가 앱 셸/메타데이터만 반환해 `figma_thumb.webp`(800×780)만 확인했다. 이후 헤드리스 Chrome으로 Design 페이지에 접근해 상위 프레임 24개의 node ID를 확보하고, 100% 배율·선택 테두리 검출로 375×812 프레임을 추출했다(§5.5). 구체 배치는 명세 v2.0을 정본으로 유지하되, Figma 실측값과 충돌하는 항목은 §5.5에 기록했다.
- **홈**: New Recipes를 가중치 기반 2열로 복원하고 카드 상세 이동을 연결했다. 인사말/레시피 제목도 가변 폭으로 복원했다. 검색창 둥근 배경·48dp 필터 버튼, 검색 결과 없음 안내를 추가했다.
- **탐색**: 저장 목록에서도 상세 화면을 열 수 있다. 상세/조리 모드에서는 하단 탭을 숨기고 시스템 뒤로가기를 처리한다. 탭·검색어·상세 레시피·조리 단계는 `rememberSaveable`로 보존한다(필터·팔로우·재료 체크 영속화는 별도).
- **필터**: 실제 `ModalBottomSheet`로 전환했다. 칩/시간 옵션은 줄바꿈을 허용하며 라디오 의미·48dp 높이를 부여했다. Apply만 조건을 반영하고 Close/바깥 닫기는 임시 변경을 폐기한다. 평점 Any로 제한 해제가 가능하다.
- **이미지/북마크**: 사진 Crop·둥근 클립, 로딩 표시와 URL 없음/로딩/실패 시 이모지 폴백을 구현했다. 하트는 48dp `IconToggleButton`으로 선택 상태와 저장/해제 설명을 노출한다. 샘플 URL이 없어 서버 이미지의 실제 성공/실패 렌더는 아직 미검증이다.
- **검증**: 앱 Kotlin 컴파일·기존 단위 테스트 9건 통과. `RecipeUiTest` 5건(북마크 토글·저장 카드 이동·필터 적용·취소·검색 빈 상태) 추가 및 androidTest Kotlin 컴파일 성공. 연결된 기기가 없어 화면 테스트는 **실행하지 않았다**. `/mnt/c` 여유 약 571MB로 APK/에뮬레이터 확장 작업은 보류했다.
- **다음 단계**: Room 기본 저장소 연결·초기 시딩과 북마크 영속화는 §5.2에서 완료 → 서버 URL/API 계약 연결 → 기기에서 화면 테스트/스크린샷 대조. 다크 팔레트 대비·RTL 강제 방향·글자 확대·TalkBack 읽기 순서는 수동 점검 잔여이며 완료로 간주하지 않는다. 커밋/push는 이번 작업에서 실행하지 않았다.

## 5.2 Room 기본 저장소 연결 (2026-11-15)

- **스키마 v2**: `saved_recipes(recipeId TEXT PK, FK recipes.id ON DELETE CASCADE)` 추가, `RecipeDatabase` version 2. 기존 `recipeapp.db`(v1) 보존을 위해 `MIGRATION_1_2` 등록 — KSP 생성 코드의 테이블 SQL과 마이그레이션 SQL이 일치함을 확인.
- **시딩**: 앱 시작 시 `seedIfEmpty`를 트랜잭션으로 실행. 테이블이 비어 있을 때만 `sampleRecipes`를 삽입(`INSERT OR IGNORE`)하며 기존 데이터는 덮어쓰지 않는다.
- **읽기**: `RoomRecipeRepository`의 `observeRecipes()`/`observeBookmarks()` Flow를 `AppRoot`에서 `produceState`로 구독. 첫 방출 전에는 로딩 인디케이터를 표시해 빈 목록 깜빡임과 시딩 전 빈 화면을 방지한다. 홈 Featured는 데모 큐레이션 id 우선·없으면 평점 상위 2개, New는 `isNew` 플래그로 DB 목록에서 파생한다.
- **쓰기**: 북마크 토글/전체 해제를 `Dispatchers.IO`에서 DAO 트랜잭션으로 처리. 상세·홈·저장 화면이 같은 Flow를 공유하므로 어느 화면에서 눌러도 일관되게 반영된다.
- **역할 분리**: `SampleRecipeRepository`는 시딩 원본·프리뷰/테스트 용도로만 남고, 런타임 화면 데이터는 Room이 공급한다.
- **검증**: `:app:compileDebugKotlin`, `:app:testDebugUnitTest`(9건, failures=0), `:app:compileDebugAndroidTestKotlin` 통과. KSP 생성 DAO에서 `seedIfEmpty`/`toggleBookmark`가 트랜잭션으로 래핑됨을 확인. `RoomRecipeRepositoryTest` 3건(빈 DB 1회 시딩·도메인 매핑 왕복·인스턴스 재생성 후 북마크 유지)을 androidTest에 작성·컴파일했다. **기기/에뮬레이터 테스트는 미실행** — 연결 기기 없음, AVD 부팅 시도했으나 현재 계정이 KVM 그룹에 없어 하드웨어 가속 불가(`/dev/kvm` 권한 오류). 따라서 v1→v2 마이그레이션 실기동·DB 영속성·화면 테스트는 아직 검증되지 않았다.
- **잔여**: 기기 확보 시 `connectedDebugAndroidTest`와 v1→v2 마이그레이션/시딩 검증, 서버 이미지 URL/API 연결, 다크/RTL/글자 확대/TalkBack 수동 점검.

## 5.3 온보딩 진입 흐름 연결 (2026-11-15)

- **문제**: `OnboardingScreen`은 명세 §3.1대로 존재했지만 프리뷰에서만 호출되어 앱 실행 시 곧바로 홈이 표시됐다.
- **구현**: `AppRoot`가 `SharedPreferences`(`recipeapp_prefs`/`onboarding_done`)를 읽어 최초 실행에만 온보딩을 표시하고, Start Cooking에서 플래그를 저장한 뒤 홈으로 전환한다. 완료 상태는 `rememberSaveable`로도 유지한다.
- **화면 보완**: 명세의 인용 문구를 `onboarding_quote`로 추가하고, 히어로 영역 전체 폭·타이틀/서브타이틀/인용 중앙 정렬·CTA 전체 폭 52dp로 정리했다. 중복 import도 제거.
- **검증**: 앱 컴파일·단위 테스트 9건·androidTest 컴파일 통과. `RecipeUiTest`에 온보딩 CTA 콜백 테스트 1건을 추가했다(총 6건). 기기 미실행 사유는 §5.2와 동일하다.
- **잔여**: 온보딩 최초 1회 동작은 실제 기기에서 미검증. 설정 화면/재온보딩 경로는 범위 외다.

## 5.4 잔여 하드코딩 문자열 정리 (2026-11-15)

- 하단 탭 라벨을 `tab_home`/`tab_saved`/`tab_notification`/`tab_profile` 리소스로 이관.
- 홈 프로필 클릭 라벨을 `open_profile`, 레시피 행 클릭 라벨을 기존 `open_recipe` 포맷으로 통일(그리드 카드와 동일).
- `CookingModeScreen`의 중복 `stringResource` import 제거.
- 사용자 노출 문자열은 화면 코드에서 모두 `strings.xml` 경유로 정리됐다(디자인상 고정된 영문 카테고리·샘플 이름은 제외). 아이콘 이모지는 장식 처리 또는 라벨 병기.
- **검증**: 앱 컴파일·단위 테스트 9건·androidTest 컴파일 통과. 기기/에뮬레이터 미실행 사유는 §5.2와 동일(KVM 권한 없음).

## 5.5 Figma 정밀 프레임 대조 (2026-11-15)

- **수집 방법**: Design 페이지의 상위 프레임 23개에서 프레임명→node ID를 매핑하고, 헤드리스 Chrome(1600×1100)에서 각 프레임을 100%로 연 뒤 Figma 선택 테두리(파랑)를 검출해 375×812로 크롭했다. Home 포함 13개 화면을 캡처했으며, Figma 우측 하단 프로모션 배너의 파란색이 테두리 검출에 섞이는 문제는 y밴드 그룹화로 제거했다.
- **실측 대조표** (치수는 375×812 기준 1:1 픽셀):

| 화면 | Figma 실측 | 현재 구현/명세 | 판정 |
|---|---|---|---|
| Splash·Onboarding | 풀블리드 음식 사진, "Get Cooking" 약 46px, Start Cooking 243×54·radius 27 (y=674–727) | 명세 §3.1: 30sp 타이틀·14sp 서브타이틀·전폭 52dp CTA. 사진 URL이 없어 이모지 히어로로 대체 | 명세 우선. Figma의 풀블리드/대형 타이포는 이미지 자산 확보 후 재검토 |
| Home | 인사말 약 22sp, 검색창 40dp(흰 배경·#EEEEEE 테두리), 카드 150×176·간격 16·좌우 여백 30·카드 #ECECEC, Featured 제목 없음, New는 가로 카드 | 명세 §3.2: 인사말 **22sp로 보정**, 섹션 제목 **18sp로 보정**, 검색창 56dp·#F1F1F1, Featured 가로 카드+New 2열 그리드, 카드 #F8F8F8 | 명세 준수. 구조·카드색은 Figma와 명세가 충돌하며 명세를 정본으로 유지 |
| Saved | 중앙 정렬 제목, 풀폭 사진 카드 315×약 145, 제목 오버레이 | 명세 §3.4: 썸네일 80×80 리스트 행. 제목 20→**22sp**, 썸네일 72→**80dp** 보정 | 명세 준수. Figma 카드형은 명세와 다른 표현 |
| Recipe Detail | 히어로 약 150dp, 제목 약 18sp, 선택 탭이 teal 알약, 재료 카드형 | 명세 §3.3: Back/하트/Share, 히어로 190dp, 제목 24sp, Segmented 탭, 하단 Start Cooking. 헤더 순서를 하트→Share로 **보정** | 명세 준수. Figma의 탭/재료 표현과는 차이 |
| Procedure(조리) | 상세 화면의 Procedure 탭(사진+단계 카드) | 명세 §4의 독립 전체화면 스텝 뷰어(이전/다음/완료) | 정보 구조 차이 — 구현 결함 아님 |
| Filter | 흰 시트, 중앙 제목, 알약 칩, Filter 버튼 174×37 | 명세 §3.5: Time 라디오·Rate 배지·Category 다중 칩·Close·전폭 Apply. 시트 배경을 surface(#F8F8F8)→**white**로 보정 | 명세 준수. 칩/버튼 형태는 Figma와 다름 |

- **색상 토큰**: Primary `#129575`, Secondary `#FFAD30`, Red `#E23E3E`는 Figma·명세·코드가 일치. Figma의 카드 `#ECECEC`·검색 테두리 `#EEEEEE`는 명세의 `#F8F8F8`·`#F1F1F1`과 충돌하므로 명세 값을 유지했다.
- **미구현 Figma 화면**: Sign In, Sign up, Search, Search Result, Notifications(All/Read/Unread), Profile, 각종 popup. 앱의 Notification/Profile 탭은 아직 플레이스홀더이며, 이번 대조에서 화면을 새로 만들지 않았다(범위·검증 한계).
- **검증**: 위 보정 후 `:app:compileDebugKotlin`, `:app:testDebugUnitTest`(9건, failures=0), `:app:compileDebugAndroidTestKotlin` 통과. **기기 렌더링 대조는 미실행** — 연결 기기 없음·KVM 권한 없음. 폰트 크기는 글리프 높이에서 추정했고, 다크/RTL/글자 확대/TalkBack 수동 점검과 서버 이미지 렌더는 여전히 미검증이다.

## 5.6 다크/RTL/글자 확대/TalkBack 점검 (2026-11-15)

- **방법**: 기기·에뮬레이터가 없어(연결 기기 없음, KVM 권한 없음) **정적 감사 + Android Lint**로 수행했다. Lint(SARIF)는 Compose semantics를 검사하지 못하므로, 테마 역할·하드코딩 색·`left/right`·고정 높이·포커스/낭독 구조를 코드에서 직접 확인했다.
- **다크 모드**
  - 원인: `darkColorScheme`이 primary/background/surface 등 일부만 덮어써 `primaryContainer`·`secondaryContainer`·`surfaceContainer`가 M3 기본 **보라색**으로 남았다. 그 결과 선택된 세그먼트 탭·선택 칩·하단 탭 인디케이터/배경이 브랜드와 무관한 보라로 보였다. 라이트 스킴도 같은 이유로 선택 상태가 보라색이었다.
  - 조치: 라이트/다크 모두 `primaryContainer`(라이트 #D7F2E9 / 다크 #0B4F40), `secondaryContainer`(라이트 #FFE7BF / 다크 #5A3F00), `onSurfaceVariant`, `surfaceContainer`, `surfaceContainerHigh`, `outlineVariant`를 명시해 선택 상태·하단 바를 브랜드 색으로 고정했다.
  - 조리 모드의 이미지 슬롯·이전 버튼이 `RecipeSurfaceVariant`(#F1F1F1)를 직접 사용해 다크에서 밝은 블록 + 밝은 회색 글자(대비 ≈1.2:1)가 되는 문제 → `colorScheme.surfaceVariant`/`onSurfaceVariant`로 교체. 온보딩·상세 CTA·조리 다음 버튼의 `RecipePrimary`/`Color.White` 하드코딩 → `primary`/`onPrimary`.
  - 콜드 스타트: `values-night/colors.xml`의 `window_background`(#121212)를 테마 `android:windowBackground`에 연결해 다크에서 흰 화면이 번쩍이지 않게 했다.
  - **알려진 한계(미수정)**: 라이트 CTA의 흰 글자/`#129575`는 대비 **3.76:1**로 WCAG AA 일반 텍스트(4.5:1)에 미달한다. 브랜드 색이 명세 정본이라 임의 변경하지 않았다. 다크의 `#5BC7A7`+`#062019`는 약 8.9:1, 하트 `#E23E3E`/다크 배경은 약 4.45:1(비텍스트 3:1 충족)이다. 수치는 계산값이며 실측 도구로 측정한 값은 아니다.
- **RTL**
  - `android:supportsRtl="true"` 유지, 코드에 `left/right`·절대 offset 없음(전부 `start/end`, `TopEnd/BottomStart`, 레이아웃 방향 기반 Row/LazyColumn).
  - 문자열에 박혀 있던 방향 화살표(`← Back`, `← 닫기`)를 제거하고 `BackArrow()`가 `LocalLayoutDirection`에 따라 `←`/`→`를 그리도록 변경(장식 semantics 제외). 저장/공유/삭제 이모지도 문자열에서 분리해 RTL에서 글자와 섞이지 않게 했다.
  - **한계**: UI 문구가 영어·한국어 혼용이라 RTL 로케일에서 문장 단위 bidi는 기기에서 확인해야 한다. RTL 스크린샷은 미실행.
- **글자 확대**
  - 고정 높이 `44dp`(세그먼트 탭), `52dp`(온보딩/상세/조리 버튼)를 `heightIn(min=…)`로 바꿔 200%에서도 잘리지 않고 자라게 했다.
  - **상세 화면은 스크롤이 없어** 812dp 기준으로도 하단 CTA가 잘릴 수 있었고 본문이 화면 가장자리에 붙어 있었다 → 헤더 고정 + 본문 `verticalScroll` + 좌우 16dp 여백으로 재구성(히어로는 풀블리드 유지).
  - 조리 모드 중앙 영역과 온보딩을 `BoxWithConstraints + verticalScroll + heightIn(min=maxHeight)`로 바꿔, 내용이 짧으면 중앙 정렬·길면 스크롤되게 했다.
  - 제목이 길어질 때 우측 버튼이 밀리지 않도록 Saved/Filter 제목에 `weight(1f)`를, 상세 셰프명에 `weight(1f)`를 적용. 하단 탭 라벨은 1줄+Ellipsis.
  - 22–30sp 제목이 기본 `bodyLarge` lineHeight(24sp)를 상속해 2줄에서 겹칠 수 있던 문제 → 온보딩 38, 상세 32, 홈 28, 섹션/저장/필터 24–28, 조리 26sp로 lineHeight 명시.
  - 계측 테스트 `detailStaysUsableAtTwoHundredPercentFontScale`(LocalDensity fontScale=2) 추가 — **컴파일만 검증, 실행 미검증**.
- **TalkBack**
  - 장식 이모지(하단 탭·검색 🔍/⚙·평점 ⭐️·하트 글리프·공유 📤·삭제 🗑️·인사 👋·아바타 "A"·이미지 로딩 스피너)를 `clearAndSetSemantics`로 제거해 중복 낭독을 없앴다. 화살표는 `BackArrow`가 장식 처리.
  - 세그먼트 탭: `selectableGroup` + `Modifier.selectable(selected, role=Role.Tab)` → "선택됨" 상태 낭독. 재료 행: `Modifier.toggleable(role=Role.Checkbox)` + `Checkbox(onCheckedChange=null)`로 체크박스·이름·계량을 **한 포커스/한 토글**로 병합(최소 48dp).
  - 제목에 `heading()` semantics(온보딩·홈 인사말/섹션·저장·상세·필터), 조리 단계 텍스트에 `liveRegion=Polite`(다음/이전 시 새 단계 자동 낭독).
  - 계측 테스트 `detailIngredientRowTogglesAsOneTarget`, `detailTabsExposeSelectedState` 추가 — 컴파일만 검증.
- **Lint 정리**: 접근성/RTL 신규 경고는 없었다(수정 전후 모두). 함께 정리한 항목: `RedundantLabel`(activity label), `ModifierParameter`(RecipeMedia 파라미터 순서), `AutoboxingStateCreation`(`mutableIntStateOf`), `TypographyEllipsis`("Search recipe…"), `UseKtx`(SharedPreferences `edit {}`), 미사용 템플릿 색상 리소스. 남은 것은 의존성 업데이트 알림, 라이브러리 lint 자체 경고, 그리고 **기존 템플릿** `androidTest/com/example/gangnam3/ui/theme/Theme.kt`의 잘못된 색상 리터럴(우리 코드 아님, 미수정)이다.
- **검증**: `:app:compileDebugKotlin`, `:app:testDebugUnitTest`(9건, failures=0), `:app:compileDebugAndroidTestKotlin`(계측 12건: UI 9 + Room 3) 통과. **기기 실행은 전부 미검증** — 실제 다크 렌더링, RTL 미러링, 200% 글자, TalkBack 낭독은 기기/에뮬레이터 확보 후 확인 필요.

## 5.7 로컬 URL 프리뷰 렌더 (2026-11-15)

- **목적**: 기기·에뮬레이터 없이 개선된 화면을 눈으로 확인할 수 있게 한다.
- **에뮬레이터 시도(중단)**: API 34 x86_64 AVD(`recipe_preview`, 375×812@160dpi)를 `-accel off`(TCG)로 부팅. `surfaceflinger`·`zygote`는 뜨지만 `system_server`가 `DeadSystemException`으로 반복 사망(`permissioncontroller`·`systemui` FATAL)해 `sys.boot_completed`가 1이 되지 않았다. `kvm` 그룹 추가는 `sudo` 비밀번호가 필요해 불가(`/dev/kvm` root:kvm 660, 사용자 미포함).
- **대안**: Robolectric 4.18 **native graphics** + Roborazzi 1.79.0으로 실제 Compose 화면을 375×812(mdpi) PNG로 렌더. `app/src/test/.../PreviewScreenshotTest.kt` 12건.
  - 실제 앱과 같은 색을 얻기 위해 프리뷰도 `Surface(color = colorScheme.background)`로 감싼다. 없으면 배경이 투명해지고 색 미지정 텍스트가 검정으로 나온다(1차 렌더에서 확인 후 수정).
  - 글자 확대는 `LocalDensity`의 `fontScale`만 2로 제공해 시스템 설정과 같은 경로를 탄다.
  - 테스트 의존성: `robolectric`·`roborazzi`·`roborazzi-compose` + `testOptions.unitTests.isIncludeAndroidResources = true`(Poppins 에셋 로드), `roborazzi.test.record=true`.
- **출력/서빙**: `app/build/preview-site/*.png` — 라이트 6(온보딩/홈/저장/상세·재료/상세·조리단계/조리), 다크 4, 200% 2. `python3 -m http.server 8000`으로 정적 갤러리 서빙(`index.html`은 저장소 밖 `/tmp/opencode/site`).
- **결과**: 라이트/다크 모두 배경·카드·CTA·선택 탭이 브랜드 색으로 렌더되고 M3 보라 폴백은 보이지 않는다. 조리 모드는 세로 중앙 정렬이 유지되고, 200%에서 온보딩/상세 CTA가 잘리지 않는다(상세 탭 라벨은 2줄로 감싸짐).
- **한계**: **정적 이미지**다. 터치·스크롤·TalkBack 낭독·RTL 미러링·애니메이션은 검증하지 못했고, 서버 이미지 URL이 없어 실제 이미지 로딩도 미검증이다. 200%는 온보딩·상세 2장만 렌더했다.
- **재생성**: `./gradlew :app:testDebugUnitTest --tests "com.suwonsmartapp.recipeapp.PreviewScreenshotTest"`.

## 6. 진행 로그 (갱신 테이블)

| 일자 | 항목 | 결과/상태 |
| :--- | :--- | :--- |
| 2026-11-14 | 기획안 초안(본 문서) | 소스 2종 분석, 진단, 로드맵, 결정 목록 D1–D9, Figma 미추출 기록 |
| 2026-11-14 | P0.2 조리 모드 | `CookingModeScreen` 신규(상태 없음) + 상세 CTA 연동(AppRoot가 레시피/인덱스 소유) — 컴파일 성공 |
| 2026-11-14 | P0.5 문자열 리소스화 | `strings.xml` 키 일괄 추가(온보딩/홈/상세/저장/필터/조리/플레이스홀더), 7개 화면 하드코딩 정적 문자열 `stringResource` 이관 — 컴파일 성공(경고: 기존 Divider deprecation 1건 잔여) |
| 2026-11-14 | P0.6 정리 | `Divider`→`HorizontalDivider`(deprecation 제거); `:app:testDebugUnitTest` 실행 — 통과 확인 |
| 2026-11-14 | P0.1 타이포 | Poppins 4종+LICENSE(`assets/fonts`) + `poppinsTypography`·로드 실패 폴백·테마 연결 — 컴파일 검증 |
| 2026-11-14 | P0.3 이미지 로더 | 심/정규화(`thumbnailUrl`, 빈 URL→null) + **Coil 배선 완료**: `io.coil-kt.coil3:coil-compose`/`coil-network-okhttp:3.6.3`, `coil3.compose.AsyncImage` — 컴파일 검증; 샘플 URL 없어 시각 변화 0, 실제 채움은 D2 |
| 2026-11-14 | P0.4 북마크 회전 생존 | `remember` 로컬 리스트 → `rememberSaveable`(불변 List+함수형 갱신) — 컴파일 성공 |
| 2026-11-14 | 툴체인 정렬 | Kotlin 2.2.10→2.4.20 (Coil 3.6.3의 stdlib 2.4.10 요구 정렬; KGP·Kotlin Compose 플러그인 동반) — 재컴파일+단위 테스트 성공 |
| 2026-11-14 | P1-α 저장소 스켈레톤 | `RecipeRepository`+`SampleRecipeRepository` 신설, AppRoot·Home·Saved 호출부 전환 — 컴파일·단위 테스트 검증; 검색/필터 순수함수·데모 큐레이션 유지; 네트워크/로컬 구현체는 D4 게이트 |
| 2026-11-15 | P1-β Room 골격 | `RecipeEntity`(recipes)·`RecipeDao`·`RecipeDatabase`(v1)·`RoomRecipeRepository`(org.json 직렬화, Context 필요) — Room 2.8.5 + KSP 2.3.14 / Kotlin 2.3.20 / Coil 3.4.0 정합; 재컴파일+단위 테스트 성공; 기본값 교체·시딩/동기화 미결(P1 게이트) |
| 2026-11-15 | 의존성 정합 조정 | KSP 2.4.x 미출시(업스트림 최신 2.3.14)·AGP 9.5 built-in Kotlin과 kapt 비호환 → KSP 경로; Coil 3.6.3(stdlib 2.4.10 요구)+Kotlin 2.4.20 대신 Coil 3.4.0/Kotlin 2.3.20 채택(11-14 항목 갱신) |
| 2026-11-15 | D9 근거 실측·선택지 정리 | 원격: origin `kwpark0047/gangnam3-android`·upstream `SurvivalCodingCampus/gangnam3-android`(추적 ref 존재); 저장소 `docs/` 이미 존재(TIL 관행); `master`+미커밋 WIP 31건 — 선택지: ① 문서 정본(워크스페이스 유지/저장소 docs/ 이관·move 권장/이중 정본 비권장) ② 브랜치(형식 `student/01_박강원`, 번호·이름 확인; 생성 시 WIP 동반 이동·삭제 파일 보존 확인) ③ push 전 read-only `git ls-remote` 확인 — 결정 시 즉시 실행(D9) |
| 2026-11-15 | D9 실행 | 브랜치 `student/01_박강원` 생성(master 미커밋 WIP 31건 동반 이동; 삭제 6건 동반)·계획서 copy→`docs/RecipeApp_Project_Plan.md`·원격 `recipe` 추가 후 push(사전 read-only로 main `04ae32a…` 확인) — ① 정본 운영 표기 확인 대기 |
| 2026-11-14 | P0.6-점검 터치 타깃/a11y 1차 | 홈 프로필 아바타 히트영역 40→48dp(시각 원 유지)+`onClickLabel`; 스톡 M3 컴포넌트 높이 정합 확인 — 컴파일·단위 테스트 검증 |
| 2026-11-15 | P0.6-점검 콘텐츠 설명 | 홈 검색창 필터("필터 열기")·레시피 행("레시피 상세 열기: <제목>") 클릭 라벨 추가; 프로필·하트 기존 라벨 확인, AsyncImage 장식 문서화 유지 — 컴파일·단위 테스트 검증 |
| 2026-11-15 | Room 계측 테스트 | in-memory Room으로 시딩 1회·도메인 매핑 왕복·북마크 재인스턴스 유지 3건 작성 — androidTest 컴파일 성공, 실행은 미검증(기기/KVM 부재) |
| 2026-11-15 | P1-γ Room 기본값 | DB v2(`saved_recipes` CASCADE)·`MIGRATION_1_2`·빈 DB 트랜잭션 시딩·Flow 구독·북마크 영속화; AppRoot/Home/Saved Room 전환 — 컴파일·단위 테스트 9건·androidTest 컴파일 성공; 기기/에뮬레이터 실행은 KVM 권한 없음으로 미검증 |
| 2026-11-15 | 온보딩 진입 연결 | `OnboardingScreen`을 AppRoot 첫 실행 게이트로 연결(SharedPreferences), 명세 인용 문구·전폭 CTA 보완; 온보딩 UI 테스트 1건 추가 — 컴파일·단위 테스트 9건·androidTest 컴파일 성공, 기기 미실행 |
| 2026-11-15 | 문자열/접근성 마감 | 하단 탭 4종·프로필/레시피 행 클릭 라벨 stringResource 통일, CookingMode 중복 import 제거 — 컴파일·단위 테스트 9건·androidTest 컴파일 성공 |
| 2026-11-15 | Figma 정밀 프레임 대조 | Design 페이지 24프레임 node ID 매핑·13화면 375×812 추출. 인사말 22sp·섹션 제목 18sp·Saved 제목 22sp·썸네일 80dp·상세 헤더 순서·필터 시트 흰 배경 보정. Figma·명세 충돌은 명세 우선으로 기록(§5.5) — 컴파일·단위 테스트 9건·androidTest 컴파일 성공, 기기 렌더 미검증 |
| 2026-11-15 | 다크/RTL/글자 확대/TalkBack 점검 | 다크 색 역할 보완(보라 폴백 제거)·조리/버튼 하드코딩 색 제거·야간 windowBackground, RTL 화살표 미러링, 고정 높이→heightIn·상세 스크롤·온보딩/조리 스크롤·lineHeight 보정, 장식 이모지 semantics 제거·탭/체크박스 선택 상태·heading·liveRegion. Lint 정리 6건. 계측 테스트 3건 추가(총 12건) — 컴파일·단위 테스트 9건 통과, 기기 실행 미검증(§5.6) |
| 2026-11-15 | 로컬 URL 프리뷰 | TCG 에뮬레이터는 `system_server` 반복 사망으로 중단 → Robolectric native graphics + Roborazzi로 실제 Compose를 375×812 PNG 12장(라이트 6·다크 4·200% 2) 렌더, 8000 포트 정적 갤러리로 서빙. 프리뷰 하네스에 `Surface`를 추가해 앱과 동일한 배경/콘텐츠 색 재현 — `PreviewScreenshotTest` 통과, 터치·TalkBack·RTL·애니메이션은 미검증(§5.7) |

## 부록 B — 환경/빌드 점검

- 구현 턴마다 컴파일로 검증 예정. 1차 점검 결과·명령 로그는 채팅 보고에 기록하고, 필요 시 본 절 갱신.
- 2026-11-14: JDK 17 / Gradle 9.6.0(캐시) / git 계정 "박강원 <kwpark0047@gmail.com>" / 저장소 `master`에 미커밋 WIP 다수. `ANDROID_HOME` 미설정이었고 SDK는 `/home/kwpark/Android/Sdk`(platforms 34–36, build-tools 28.0.3/34/36). 네트워크 도달 가능 → 1차 컴파일에서 **SDK Platform 37.0(rev2) 자동 설치** 후 컴파일 진행.
- 2026-11-14: 1차 컴파일 이슈 — (a) `ui/theme/Type.kt`: `Font(Typeface,…)` 오버로드 없음 → path+AssetManager 방식으로 재작성(재컴파일 필요); (b) `screens/HomeScreen.kt:17` `Modifier.weight(1f)`가 현행 Compose에서 internal — **제 변경과 무관한 기존 WIP 깨짐**으로, 의도(가변 갭) 유지한 최소 수정 `Spacer(Modifier.width(12.dp))` 적용(되돌리기 쉬운 1줄, 사용자 확인 권장).
- 2026-11-14: Coil 3.x 좌표 확인 — `app.cash.coil3`·2.x 트랙 가정은 중앙 저장소 메타데이터/POM 404로 폐기, 저장소 README(`coil-kt/coil`)의 `io.coil-kt.coil3:coil-compose:3.6.3` 좌표를 메타데이터·POM·jar로 검증(패키지 `coil3.compose`). 3.6.3의 stdlib 2.4.10 요구에 맞춰 툴체인 Kotlin 2.4.20 정렬(KGP·Compose 플러그인 동반). 재컴파일·단위 테스트 성공.
- 2026-11-15: 의존성 재조정 — KSP 2.4.x 미출시(업스트림 최신 2.3.14, GitHub tags 확인)·AGP 9.5 "built-in Kotlin 지원과 kapt 비호환" → **KSP 경로**; Room 2.8.5 + KSP 2.3.14 코드젠·컴파일 검증. Coil 3.6.3(stdlib 2.4.10 요구)은 Kotlin 2.3.20과 충돌 → **Coil 3.4.0(stdlib 2.3.10)** 조정. gradle 초기 실행은 간헐적 FileHasher I/O 오류 재발(재시도/데몬 재시작으로 통과).
- 2026-11-15: 환경 경고 — `/mnt/c` 사용량 100%(약 231/238G). 빌드는 통과했으나 용량 압박; 빌드 산출물·불필요 파일 정리는 사용자 판단(권한·대상 확인 필요).
- 2026-11-15: 화면 테스트 실행 시도 — AVD `wemarket_test`·시스템 이미지·`/dev/kvm`은 존재하나 현재 계정이 `kvm` 그룹에 속하지 않아 에뮬레이터가 하드웨어 가속을 얻지 못함(`This user doesn't have permissions to use KVM`). 소프트웨어 에뮬레이션은 현실적이지 않아 **기기 테스트 미실행**으로 기록.
- 참고: 과거 모델은 이미지 입력 미지원이었으나 **현재 모델은 `figma_thumb.webp`를 직접 확인 가능**(2026-11-15). 정밀 Figma 프레임은 여전히 접근 불가.

---

## 부록 A — 현재 코드맵(요약)

- `app/.../MainActivity.kt` — `RecipeAppTheme { AppRoot() }`
- `AppRoot.kt` — 탭/라우팅/필터 시트 오케스트레이션 + Room 저장소 연결(Flow/시딩/북마크)
- `data/Recipe.kt` — `RecipeCategory`/`TimeFilter`/`RatingFilter`/`Recipe`/`FilterState` + 순수 함수 `matches`/`formatRating`/`formatReviews`
- `data/RoomRecipeRepository.kt` — Room DB 싱글턴·마이그레이션·시딩·Flow·북마크 토글
- `data/SampleData.kt` — 샘플 6레시피, `featuredRecipeIds`/`sampleFeatured`/`sampleNew`
- `screens/` — `OnboardingScreen`, `HomeScreen`, `RecipeDetailScreen`, `SavedScreen`, `SearchFilterSheet`, `SharedComponents`(`RecipeMedia`, `HeartToggle`), `CookingModeScreen`
- `ui/theme/` — `Color.kt`(브랜드 토큰), `Theme.kt`(라이트/다크/다이나믹 옵션), `Type.kt`(기본 타이포)
- `ui/` — 학습용 실험 파일군(Buttons/InputField/Tabs/UIExam 등, 내용 확인 범위 제한)
- `test/` — `data/RecipeFilterTest`(8건), `ExampleUnitTest`; `androidTest/` — 예시 테스트들
- 참고: 동일 클래스의 두 경로(예: `app/src/MainActivity.kt` vs `.../recipeapp/MainActivity.kt`)와 플러터 흔적 등 정리 필요.
