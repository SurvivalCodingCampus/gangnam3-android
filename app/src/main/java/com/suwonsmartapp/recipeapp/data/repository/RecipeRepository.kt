package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.data.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
}