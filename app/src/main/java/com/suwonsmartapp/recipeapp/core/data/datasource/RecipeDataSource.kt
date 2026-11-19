package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.Response
import com.suwonsmartapp.recipeapp.core.data.model.RecipesResponse

interface RecipeDataSource {
    suspend fun getRecipes(): Response<RecipesResponse>
}
