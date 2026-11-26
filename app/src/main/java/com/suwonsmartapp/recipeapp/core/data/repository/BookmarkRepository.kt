package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result

interface BookmarkRepository {
    // 북마크한 레시피 목록
    suspend fun getBookmarkedRecipes(): Result<List<Recipe>, NetworkError>
}
