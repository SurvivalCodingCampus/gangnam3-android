package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.RecipeError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipe(id: Long): Flow<Result<Recipe, RecipeError>>
    fun getRecipes(): Flow<Result<List<Recipe>, RecipeError>>
}
