package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.core.data.model.Recipe

// 검색어가 바뀌어도 화면 구조는 그대로라 data class 하나로 표현
data class SearchRecipesState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val query: String? = null,
    val recipes: List<Recipe>,
    val filterSearchState: FilterSearchState = FilterSearchState(),
)
