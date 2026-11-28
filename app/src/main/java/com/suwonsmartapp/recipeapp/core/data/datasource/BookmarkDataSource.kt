package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.RecipesResponseDto
import com.suwonsmartapp.recipeapp.core.network.Response

interface BookmarkDataSource {
    // 서버 쿼리 예: /bookmarks?category=Chinese → 북마크한 레시피만 내려줌
    // category가 null이면 전체
    suspend fun getBookmarkedRecipes(
        category: String? = null,
    ): Response<RecipesResponseDto>
}
