package com.suwonsmartapp.recipeapp.core.data.model

import androidx.annotation.DrawableRes

data class Ingredient(
    val name: String,
    @param:DrawableRes val imageResId: Int,
    val grams: Int,
    val description: String,
)