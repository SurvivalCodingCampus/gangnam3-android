package com.example.gangnam3.presentation.search_recipes

/**
 * 필터 검색 화면에서 현재 선택된 조건을 표현하는 불변 상태 모델입니다.
 *
 * 각 속성은 화면에 표시되는 필터 한 종류의 선택값을 나타냅니다. `copy`를 이용해
 * 한 조건만 바꾸면 나머지 조건은 유지되므로 Compose 상태 갱신과 화면 재구성이 단순해집니다.
 *
 * @property selectedTime 선택된 시간 또는 정렬 옵션입니다. 기본값은 최신순입니다.
 * @property selectedRate 선택한 최소 별점이며, `null`이면 별점 필터를 사용하지 않습니다.
 * @property selectedCategory 선택된 음식 분류입니다. 기본값은 지역 음식입니다.
 */
data class FilterSearchState(
    val selectedTime: String = "Newest",
    val selectedRate: Int? = 4,
    val selectedCategory: String = "Local Dish"
) {
    /** 화면의 선택 칩을 만들 때 사용하는 고정 옵션 모음입니다. */
    companion object {
        /** 정렬 및 시간 선택 칩에 표시할 옵션입니다. */
        val timeOptions = listOf("All", "Newest", "Oldest", "Popularity")

        /** 별점 필터에 표시할 점수입니다. 높은 점수부터 보여줍니다. */
        val rateOptions = listOf(5, 4, 3, 2, 1)

        /** 카테고리 필터에 표시할 음식 분류입니다. */
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
