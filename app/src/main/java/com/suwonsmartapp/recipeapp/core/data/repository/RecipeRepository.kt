package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>, NetworkError>
}
