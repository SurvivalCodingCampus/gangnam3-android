package com.suwonsmartapp.recipeapp.data.mapper

import com.suwonsmartapp.recipeapp.data.dto.RecipeDto
import com.suwonsmartapp.recipeapp.domain.model.Recipe

fun RecipeDto.toModel(): Recipe {
    return Recipe(
        id = id ?: 0,
        category = category.orEmpty(),
        name = name.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        chef = chef.orEmpty(),
        time = time.orEmpty(),
        rating = rating?.coerceIn(0.0, 5.0) ?: 0.0,
    )
}
