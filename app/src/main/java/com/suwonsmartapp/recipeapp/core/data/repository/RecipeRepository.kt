package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe

interface RecipeRepository {
    // 조건을 안 넘기면 전체 목록
    suspend fun getRecipes(
        query: String? = null,
        rate: Int? = null,
        category: String? = null,
        sort: String? = null,
    ): Result<List<Recipe>, NetworkError>
}
