# Food Recipe App — 프로젝트 기획안

- **작성일**: 2026-11-14
- **대상 프로젝트**: `/mnt/c/gangnam3-android` (`RecipeApp`, 패키지 `com.suwonsmartapp.recipeapp`, Kotlin + Jetpack Compose)
- **근거 소스**
  - `food_recipe_app_ui_ux_wireframe_specification.md` (v2.0, 2026-11-06) — **정본으로 채택**
  - Figma 링크 (`gSeg6yi2QMN0GajM3diTb2`, node-id=1-3) — **미추출**: 로그인/자바스크립트 렌더링 장벽. 명세를 1차 근거로 사용하며, 접근 가능해지면 §5 체크리스트로 대조 후 본 문서 갱신
- **문서 성격**: 명세 + 현재 구현 진단 + 로드맵 + 미결 결정 목록. 결정이 해소될 때마다 해당 표를 갱신한다.

---

## 0. 요약

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
- **구현됨**: 브랜드 컬러/라이트(+다크 근사치) 테마, `MainActivity` + `AppRoot`(탭·라우팅·필터 시트·북마크 로컬 상태), 5개 화면, 샘플 6레시피, 순수 함수 `Recipe.matches` + `formatRating`/`formatReviews`(로케일 무관), `RecipeFilterTest` 8건.
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

**원칙**: UI는 데이터 소스에 대해 무지(stateless + 콜백/상태호이스틀링 — 저장소 TIL 규칙과 일치). 썸네일/데이터 소스는 인터페이스 뒤로 교체 가능하게. 명세 필드와 모델 매핑을 문서로 유지.

### P0 — 명세 정합화 (데모 → 스펙 완성)
1. **타이포**: Poppins(OFL) 자산 등록 + 라이선스 동봉 — **완료(2026-11-14)**: `assets/fonts/Poppins-{Regular,Medium,SemiBold,Bold}.ttf` + `OFL.txt`, `poppinsTypography` 구현 + 로드 실패 폴백, 테마 연결(컴파일 검증 완료).
2. **조리 모드 화면**: 상세 CTA 연동, 스텝별 전체화면(진행 표시/인덱스, 단계 이미지 슬롯(플레이스홀더 허용), 완료 시 복귀). 명세에 독립 화면 없음 → 와이어프레임 수준 our own 결정, §4 D-기록. **구현 완료(2026-11-14)**, 컴파일 검증.
3. **이미지 로더 배선**: **완료(2026-11-14)** — `io.coil-kt.coil3:coil-compose` + `coil-network-okhttp`(좌표 `3.4.0`으로 정합 조정: 3.6.3의 stdlib 2.4.10 요구가 KSP 2.4.x 미출시·kapt 비호환 조합과 충돌 — 중앙 저장소 메타데이터/POM/jar로 좌표·패키지 `coil3.compose` 검증). 비-null `thumbnailUrl` → `coil3.compose.AsyncImage`(로딩/실패/성공은 로더 처리), 빈/공백 URL→null 정규화 후 기존 이모지/틴트 자리표시 유지. 샘플 데이터에 URL이 없어 시각적 변화 0 — 실제 URL 채움은 **D2 확정('서버 제공', 2026-11-15)**에 따른 데이터 확충 시점.
4. **상태 승격**: 북마크 회전 생존 — **완료(2026-11-14, rememberSaveable)**; 팔로우/재료 체크 리프트 + 영구 저장 여부는 P1 게이트(D5)로 유지.
5. **문자열 리소스화**: 하드코딩 텍스트 → `strings.xml`(다국어 대비) — **완료(2026-11-14)**, 7개 화면 이관, 컴파일 검증.
6. **테스트/접근성 보강**: 기존 순수 로직 테스트 **8+1 통과(2026-11-14, failures=0)**. 화면 테스트 경로는 **androidTest/에뮬레이터·실기기**로 확정(로컬 검증 2026-11-15 — `emulator`·`system-images/android-34/google_apis/x86_64`·사용자 AVD `wemarket_test`·`/dev/kvm`·`platform-tools/adb` 존재, 다운로드 불필요; 디스크 여유 563M로 신규 설치 비권장). 기존 `androidTest/com/example/gangnam3` 3종은 자체 컴포저블 테스트(앱 클래스 무관, 실행 시 최종 판정). 실행: 에뮬레이터/기기 부팅 후 `./gradlew :app:connectedDebugAndroidTest`. 터치 타깃 1차 코드 점검 완료(홈 프로필 아바타 40→48dp 히트영역·라벨) + 대화형 요소 콘텐츠 설명 부여(검색 필터·레시피 행) — 잔여: 글리프 심볼 컨트롤·대비·빈 상태 세부 육안 점검(아래 P2).

### P1 — 데이터 레이어
- **P1-α(2026-11-14): 저장소 인터페이스 스켈레톤** — `RecipeRepository.recipes()` + 샘플 구현체(`SampleRecipeRepository`); AppRoot·Home·Saved 호출부 전환(컴파일·단위 테스트 검증). 검색/필터(`Recipe.matches`)·정렬 등 순수 로직과 데모 큐레이션(`sampleFeatured`/`sampleNew`)은 저장소 무관 계층으로 유지. 네트워크/로컬(Room 등) 구현체와 모델↔테이블 매핑 확정(§1.3)은 **D4 게이트 유지**.
- **P1-β(2026-11-15): Room 골격 구현** — `RecipeEntity`(recipes 테이블)·`RecipeDao`·`RecipeDatabase`(v1)·`RoomRecipeRepository`(org.json 직렬화, `Context` 필요) — **Room 2.8.5 + KSP 2.3.14 / Kotlin 2.3.20 / Coil 3.4.0** 정합 재컴파일·단위 테스트 성공. 기본값 교체(시딩/동기화 포함)는 P1 게이트 유지.
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
| D4 | 데이터 소유 | 로컬 앱 완결/백엔드 연동 | **확정(2026-11-15): 로컬+Room** — `RecipeRepository` 인터페이스(2026-11-14, P1-α)로 교체 경로 확보 + **Room 2.8.5/KSP 2.3.14 골격 구현(2026-11-15, P1-β)**; 기본값 교체·시딩/동기화 정책 미결(P1 게이트) | P1/P3 | P3 진입 시 |
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

## 부록 B — 환경/빌드 점검

- 구현 턴마다 컴파일로 검증 예정. 1차 점검 결과·명령 로그는 채팅 보고에 기록하고, 필요 시 본 절 갱신.
- 2026-11-14: JDK 17 / Gradle 9.6.0(캐시) / git 계정 "박강원 <kwpark0047@gmail.com>" / 저장소 `master`에 미커밋 WIP 다수. `ANDROID_HOME` 미설정이었고 SDK는 `/home/kwpark/Android/Sdk`(platforms 34–36, build-tools 28.0.3/34/36). 네트워크 도달 가능 → 1차 컴파일에서 **SDK Platform 37.0(rev2) 자동 설치** 후 컴파일 진행.
- 2026-11-14: 1차 컴파일 이슈 — (a) `ui/theme/Type.kt`: `Font(Typeface,…)` 오버로드 없음 → path+AssetManager 방식으로 재작성(재컴파일 필요); (b) `screens/HomeScreen.kt:17` `Modifier.weight(1f)`가 현행 Compose에서 internal — **제 변경과 무관한 기존 WIP 깨짐**으로, 의도(가변 갭) 유지한 최소 수정 `Spacer(Modifier.width(12.dp))` 적용(되돌리기 쉬운 1줄, 사용자 확인 권장).
- 2026-11-14: Coil 3.x 좌표 확인 — `app.cash.coil3`·2.x 트랙 가정은 중앙 저장소 메타데이터/POM 404로 폐기, 저장소 README(`coil-kt/coil`)의 `io.coil-kt.coil3:coil-compose:3.6.3` 좌표를 메타데이터·POM·jar로 검증(패키지 `coil3.compose`). 3.6.3의 stdlib 2.4.10 요구에 맞춰 툴체인 Kotlin 2.4.20 정렬(KGP·Compose 플러그인 동반). 재컴파일·단위 테스트 성공.
- 2026-11-15: 의존성 재조정 — KSP 2.4.x 미출시(업스트림 최신 2.3.14, GitHub tags 확인)·AGP 9.5 "built-in Kotlin 지원과 kapt 비호환" → **KSP 경로**; Room 2.8.5 + KSP 2.3.14 코드젠·컴파일 검증. Coil 3.6.3(stdlib 2.4.10 요구)은 Kotlin 2.3.20과 충돌 → **Coil 3.4.0(stdlib 2.3.10)** 조정. gradle 초기 실행은 간헐적 FileHasher I/O 오류 재발(재시도/데몬 재시작으로 통과).
- 2026-11-15: 환경 경고 — `/mnt/c` 사용량 100%(약 231/238G). 빌드는 통과했으나 용량 압박; 빌드 산출물·불필요 파일 정리는 사용자 판단(권한·대상 확인 필요).
- 참고: 본 모델은 이 환경 툴에서 **이미지 입력 미지원** — Figma 썸네일(`figma_thumb.webp`) 등 시각 자료는 사용자가 직접 육안 확인 필요.

---

## 부록 A — 현재 코드맵(요약)

- `app/.../MainActivity.kt` — `RecipeAppTheme { AppRoot() }`
- `AppRoot.kt` — 탭/라우팅/필터 시트/북마크 로컬 상태 오케스트레이션
- `data/Recipe.kt` — `RecipeCategory`/`TimeFilter`/`RatingFilter`/`Recipe`/`FilterState` + 순수 함수 `matches`/`formatRating`/`formatReviews`
- `data/SampleData.kt` — 샘플 6레시피, `sampleFeatured`/`sampleNew`
- `screens/` — `OnboardingScreen`, `HomeScreen`, `RecipeDetailScreen`, `SavedScreen`, `SearchFilterSheet`, `SharedComponents`(`RecipeMedia`, `HeartToggle`), `CookingModeScreen`
- `ui/theme/` — `Color.kt`(브랜드 토큰), `Theme.kt`(라이트/다크/다이나믹 옵션), `Type.kt`(기본 타이포)
- `ui/` — 학습용 실험 파일군(Buttons/InputField/Tabs/UIExam 등, 내용 확인 범위 제한)
- `test/` — `data/RecipeFilterTest`(8건), `ExampleUnitTest`; `androidTest/` — 예시 테스트들
- 참고: 동일 클래스의 두 경로(예: `app/src/MainActivity.kt` vs `.../recipeapp/MainActivity.kt`)와 플러터 흔적 등 정리 필요.

## 부록 B — 환경/빌드 점검

- 구현 턴마다 컴파일로 검증 예정. 1차 점검 결과·명령 로그는 채팅 보고에 기록하고, 필요 시 본 절 갱신.
