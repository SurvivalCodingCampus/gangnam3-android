package com.suwonsmartapp.recipeapp.data

/**
 * 데이터 소스 추상화 — UI/도메인은 구현체에 대해 무지(stateless + 콜백 원칙, 계획서 P1-α).
 *
 * 구현체:
 *  - `RoomRecipeRepository` — **기본값**(2026-11-15 연결). 빈 DB만 시딩하고 Flow로 관찰.
 *  - `SampleRecipeRepository` — 시딩 원본·프리뷰/테스트용. 앱 런타임 화면은 Room을 사용.
 *  - `rating`/`reviews`는 소스가 제공하는 **집계값**으로 취급(D5 잠정) — 클라이언트 측 리뷰 저장 없음.
 * 범위 규약: 저장소는 "레시피 목록"만 공급 — 검색/필터(`Recipe.matches`)·정렬·데모 큐레이션
 * (`sampleFeatured`/`sampleNew` 등)은 저장소 무관 계층으로 유지, 소스 교체 시 로직 재작성 불필요·테스트 경계 보존.
 * 상태ful 소스(페이징/캐시/오프라인)는 나중에 필요해지면 이 인터페이스를 유지한 채 내부 구현으로 흡수.
 */
interface RecipeRepository {
    /** 현재 표시 가능한 레시피 전체 목록. */
    fun recipes(): List<Recipe>
}

/** 데모 기본값 — 샘플 데이터 감싸기(교체 1점: 이 객체 참조처). */
object SampleRecipeRepository : RecipeRepository {
    override fun recipes(): List<Recipe> = sampleRecipes
}
