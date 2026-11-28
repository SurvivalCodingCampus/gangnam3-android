package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe

// 검색 화면 상태를 한 곳에서 관리하는 UI State 홀더
/**
 * @property allRecipes 불러온 전체 목록(원본)
 * @property filteredRecipes 검색어와 필터를 적용한 표시용 목록
 * @property query 입력칸에 표시할 검색어
 * @property isLoading 불러오는 중인지 여부
 * @property filter 현재 적용된 필터
 */
data class SearchRecipesState(
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val filter: FilterSearchState = FilterSearchState()
)