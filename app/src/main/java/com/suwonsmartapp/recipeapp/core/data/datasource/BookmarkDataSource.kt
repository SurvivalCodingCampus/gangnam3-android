package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.RecipesResponseDto
import com.suwonsmartapp.recipeapp.core.network.Response

interface BookmarkDataSource {
    // 서버 쿼리 예: /bookmarks → 북마크한 레시피만 내려줌
    suspend fun getBookmarkedRecipes(): Response<RecipesResponseDto>
}
