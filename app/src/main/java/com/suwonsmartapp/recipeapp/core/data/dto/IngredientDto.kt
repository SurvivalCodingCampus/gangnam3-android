package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: Long? = null,
    val name: String? = null,
    val image: String? = null,
)
