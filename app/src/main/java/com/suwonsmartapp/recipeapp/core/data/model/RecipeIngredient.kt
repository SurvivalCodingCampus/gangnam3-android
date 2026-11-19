package com.suwonsmartapp.recipeapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredient(
    val ingredient: Ingredient,
    val amount: Int,
)
