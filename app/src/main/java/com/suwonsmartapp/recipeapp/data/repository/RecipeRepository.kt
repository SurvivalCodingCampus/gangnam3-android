package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.domain.model.recipe.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
}