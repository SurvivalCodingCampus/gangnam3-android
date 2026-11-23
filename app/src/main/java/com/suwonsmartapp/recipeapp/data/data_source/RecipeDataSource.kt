package com.suwonsmartapp.recipeapp.data.data_source

import com.suwonsmartapp.recipeapp.data.dto.RecipeDto

interface RecipeDataSource {

    suspend fun getRecipes(): List<RecipeDto>
}
