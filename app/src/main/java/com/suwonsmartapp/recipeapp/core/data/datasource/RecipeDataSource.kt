package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.network.Response
import com.suwonsmartapp.recipeapp.core.data.dto.RecipesResponseDto

interface RecipeDataSource {
    // 서버 쿼리 예: /recipes?query=chicken&rate=4&category=Dinner&sort=newest
    // 값이 null이면 그 조건은 안 보냄 → 전체
    suspend fun getRecipes(
        query: String? = null,    // 이름 검색어
        rate: Int? = null,        // 4면 4.0 이상 5.0 미만
        category: String? = null, // 이 카테고리만
        sort: String? = null,     // "newest", "oldest", "popularity"
    ): Response<RecipesResponseDto>
}
