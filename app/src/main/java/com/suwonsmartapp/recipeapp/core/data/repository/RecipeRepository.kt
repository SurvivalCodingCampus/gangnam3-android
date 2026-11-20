package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.RecipeError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe

interface RecipeRepository {
    suspend fun getRecipe(id: Long): Result<Recipe, RecipeError>
    suspend fun getRecipes(): Result<List<Recipe>, RecipeError>
}
