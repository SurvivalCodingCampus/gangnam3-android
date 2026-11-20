package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.data.model.Recipe

data class SavedRecipesState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
