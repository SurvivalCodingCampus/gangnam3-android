package com.suwonsmartapp.recipeapp.data.data_source

import com.suwonsmartapp.recipeapp.data.model.Recipe

interface RecipeDataSource {

    suspend fun getRecipes(): List<Recipe>
}
