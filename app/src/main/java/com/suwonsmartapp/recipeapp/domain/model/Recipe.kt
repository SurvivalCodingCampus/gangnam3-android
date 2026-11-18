package com.suwonsmartapp.recipeapp.domain.model

data class Recipe(
    val title: String,
    val chef: String,
    val time: String,
    val rating: Double,
    val imageUrl: String,
)