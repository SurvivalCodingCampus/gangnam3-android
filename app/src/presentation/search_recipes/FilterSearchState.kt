package com.example.gangnam3.presentation.search_recipes

// 필터 바텀시트 내부 상태 홀더
/**
 * 필터 선택값.
 * @property selectedTime 정렬 기준
 * @property selectedRate 최소 별점. null이면 별점을 제한하지 않는다.
 * @property selectedCategory 선택한 카테고리
 *
 * 주의: 기본 별점이 4라서 필터를 열지 않아도 별점 4 미만은 결과에서 빠진다.
 */
data class FilterSearchState(
    val selectedTime: String = "Newest",
    val selectedRate: Int? = 4,
    val selectedCategory: String = "Local Dish"
) {
    // 객체마다 만들지 않고 모든 상태가 함께 쓰는 선택지 목록
    companion object {
        val timeOptions = listOf("All", "Newest", "Oldest", "Popularity")
        val rateOptions = listOf(5, 4, 3, 2, 1)
        val categoryOptions = listOf(
            "All",
            "Cereal",
            "Vegetables",
            "Dinner",
            "Chinese",
            "Local Dish",
            "Fruit",
            "BreakFast",
            "Spanish",
            "Lunch"
        )
    }
}