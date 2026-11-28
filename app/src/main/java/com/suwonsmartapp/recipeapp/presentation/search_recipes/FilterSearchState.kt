package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory
import com.suwonsmartapp.recipeapp.presentation.model.FilterRate
import com.suwonsmartapp.recipeapp.presentation.model.FilterTime

// 필터 하단 시트에서 고른 값
data class FilterSearchState(
    val time: FilterTime = FilterTime.ALL,
    val rate: FilterRate? = null, // null이면 별점 필터 없음
    val category: FilterCategory = FilterCategory.ALL,
) {
    // 아무 필터도 안 골랐는지
    val isEmpty: Boolean
        get() = time == FilterTime.ALL && rate == null && category == FilterCategory.ALL
}
