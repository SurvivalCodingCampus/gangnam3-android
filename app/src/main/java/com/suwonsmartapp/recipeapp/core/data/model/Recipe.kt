package com.suwonsmartapp.recipeapp.core.data.model

import androidx.annotation.DrawableRes

data class Recipe(
    val title: String,
    @param:DrawableRes val imageResId: Int,
    val imageDescription: String,
    val userName: String,
    val requiredTime: Int,
    val rating: Float,
)