package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponseDto(
    val recipes: List<RecipeDto>? = null,
)
