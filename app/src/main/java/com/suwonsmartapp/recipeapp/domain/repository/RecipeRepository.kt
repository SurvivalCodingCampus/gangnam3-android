package com.suwonsmartapp.recipeapp.domain.repository

import com.suwonsmartapp.recipeapp.core.NetworkError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.model.Recipe

interface RecipeRepository {

    suspend fun getRecipes(): Result<List<Recipe>, NetworkError>

    suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError>
}
