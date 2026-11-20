package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.network.Response
import com.suwonsmartapp.recipeapp.core.data.dto.RecipesResponseDto

interface RecipeDataSource {
    suspend fun getRecipes(): Response<RecipesResponseDto>
}
