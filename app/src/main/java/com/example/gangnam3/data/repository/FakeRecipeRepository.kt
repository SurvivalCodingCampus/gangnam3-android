package com.example.gangnam3.data.repository

import com.example.gangnam3.data.model.Recipe

/** 프리뷰·테스트용 저장소 — 생성 시 전달한 목록을 그대로 돌려준다. */
class FakeRecipeRepository(
    private val recipes: List<Recipe> = emptyList()
) : RecipeRepository {
    override suspend fun getSavedRecipes(): List<Recipe> = recipes
}
