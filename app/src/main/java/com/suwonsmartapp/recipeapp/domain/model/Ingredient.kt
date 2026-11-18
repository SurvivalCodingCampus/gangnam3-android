package com.suwonsmartapp.recipeapp.domain.model

data class Ingredient(
    val name: String,
    val imageUrl: String,
    val grams: Int,
    val description: String,
)