package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Long? = null,
    val category: String? = null,
    val name: String? = null,
    val image: String? = null,
    val chef: String? = null,
    val time: String? = null,
    val rating: Double? = null,
    val ingredients: List<RecipeIngredientDto>? = null,
)
