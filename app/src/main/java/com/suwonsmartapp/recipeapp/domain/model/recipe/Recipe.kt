package com.suwonsmartapp.recipeapp.domain.model.recipe

import com.suwonsmartapp.recipeapp.domain.model.user.UserSummary

data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val videoUrl: String,
    val chef: UserSummary,
    val category: RecipeCategory,
    val reviewCount: Int,
    val cookingTimeMinutes: Int,
    val createAt: Long,  // 레시피 등록 시간
    val rating: Double,  // 평균 별점 (서버 집계)
)
