package com.suwonsmartapp.recipeapp.domain.model.recipe

data class Ingredient (
    val name: String,
//    val imageUrl: String,
    val imageUrl: Int,
    val amount: String,  // 500g, 1 cup, 약간 등 표시하기 위함
)