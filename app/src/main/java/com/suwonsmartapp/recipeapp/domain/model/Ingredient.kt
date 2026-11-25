package com.suwonsmartapp.recipeapp.domain.model

data class Ingredient(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val amountInGrams: Int,
)
