package com.suwonsmartapp.recipeapp.core.data.model

data class Recipe(
    val id: Long,
    val category: String,
    val name: String,
    val image: String,
    val chef: String,
    val time: String,
    val rating: Double,
    val ingredients: List<RecipeIngredient>,
)
