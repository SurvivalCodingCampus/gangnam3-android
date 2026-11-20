package com.suwonsmartapp.recipeapp.data.data_source

interface RecipeDataSource {
    suspend fun getRecipes(): List<RecipeDto>
}
