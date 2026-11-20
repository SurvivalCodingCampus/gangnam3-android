package com.suwonsmartapp.recipeapp.data.data_source

import com.suwonsmartapp.recipeapp.domain.model.recipe.Recipe

interface RecipeDataSource {
    suspend fun loadRecipes(): List<Recipe>
}