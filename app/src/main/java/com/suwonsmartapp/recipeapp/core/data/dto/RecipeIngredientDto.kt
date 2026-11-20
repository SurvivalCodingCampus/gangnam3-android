package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredientDto(
    val ingredient: IngredientDto? = null,
    val amount: Int? = null,
)
