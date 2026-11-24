package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.domain.model.Recipe

data class SearchRecipesState(
    val recipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val filter: FilterSearchState = FilterSearchState(),
)
