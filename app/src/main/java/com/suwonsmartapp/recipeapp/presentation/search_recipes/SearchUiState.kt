package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.core.data.model.Recipe

// 화면 전체 상태
// query, filter는 검색 결과가 바뀌어도 유지돼야 하므로 필드로 둔다
data class SearchUiState(
    val query: String = "",
    val filter: FilterSearchState = FilterSearchState(),
    // 화면에 들어오자마자 검색을 시작하므로 처음엔 Loading
    val result: SearchResult = SearchResult.Loading,
)

// 검색 결과 (한 번에 하나의 상태만 가능)
sealed interface SearchResult {
    data object Loading : SearchResult

    data class Success(val recipes: List<Recipe>) : SearchResult

    data class Error(val message: String) : SearchResult
}
