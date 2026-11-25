package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe

// 검색 화면 상태를 한 곳에서 관리하는 UI State 홀더
data class SearchRecipesState(
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false
)