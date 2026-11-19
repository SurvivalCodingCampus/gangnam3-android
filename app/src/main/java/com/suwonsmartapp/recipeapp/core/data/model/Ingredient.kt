package com.suwonsmartapp.recipeapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Long,
    val name: String,
    val image: String,
)
