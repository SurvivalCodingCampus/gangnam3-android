package com.suwonsmartapp.recipeapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val recipes: List<Recipe>,
)
