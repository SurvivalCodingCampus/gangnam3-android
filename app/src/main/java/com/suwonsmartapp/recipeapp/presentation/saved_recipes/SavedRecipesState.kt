package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.core.data.model.Recipe

sealed interface SavedRecipesState {
    data object Loading : SavedRecipesState

    data class Error(val errorMessage: String) : SavedRecipesState

    data class Success(val recipes: List<Recipe>) : SavedRecipesState
}
