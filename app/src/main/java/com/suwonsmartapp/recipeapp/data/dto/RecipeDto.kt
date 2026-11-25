package com.suwonsmartapp.recipeapp.data.dto

data class RecipeDto(
    val id: Int? = null,
    val category: String? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val chef: String? = null,
    val time: String? = null,
    val rating: Double? = null,
)
