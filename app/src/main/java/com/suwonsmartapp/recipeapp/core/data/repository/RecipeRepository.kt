package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipe(id: Long): Flow<Recipe?>
    suspend fun getRecipes(): Flow<List<Recipe>>
}