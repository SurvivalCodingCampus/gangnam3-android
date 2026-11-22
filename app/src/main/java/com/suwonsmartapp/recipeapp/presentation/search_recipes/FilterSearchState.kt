package com.suwonsmartapp.recipeapp.presentation.search_recipes

// 필터 하단 시트에서 고른 값
data class FilterSearchState(
    val time: String = "All",
    val rate: Int? = null, // null이면 별점 필터 없음
    val category: String = "All",
) {
    // 아무 필터도 안 골랐는지
    val isEmpty: Boolean
        get() = time == "All" && rate == null && category == "All"
}
