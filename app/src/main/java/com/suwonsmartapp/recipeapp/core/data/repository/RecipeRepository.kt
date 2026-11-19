package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipe(id: Long): Recipe
    suspend fun getRecipes(): List<Recipe>
}