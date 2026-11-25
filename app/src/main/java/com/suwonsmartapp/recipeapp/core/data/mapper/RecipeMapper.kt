package com.suwonsmartapp.recipeapp.core.data.mapper

import com.suwonsmartapp.recipeapp.core.data.dto.IngredientDto
import com.suwonsmartapp.recipeapp.core.data.dto.RecipeDto
import com.suwonsmartapp.recipeapp.core.data.dto.RecipeIngredientDto
import com.suwonsmartapp.recipeapp.core.data.model.Ingredient
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.model.RecipeIngredient

fun IngredientDto.toModel(): Ingredient = Ingredient(
    id = id ?: 0L,
    name = name.orEmpty(),
    image = image.orEmpty(),
)

fun Ingredient.toDto(): IngredientDto = IngredientDto(
    id = id,
    name = name,
    image = image,
)

fun RecipeIngredientDto.toModel(): RecipeIngredient = RecipeIngredient(
    ingredient = (ingredient ?: IngredientDto()).toModel(),
    amount = amount ?: 0,
)

fun RecipeIngredient.toDto(): RecipeIngredientDto = RecipeIngredientDto(
    ingredient = ingredient.toDto(),
    amount = amount,
)

fun RecipeDto.toModel(): Recipe = Recipe(
    id = id ?: 0L,
    category = category.orEmpty(),
    name = name.orEmpty(),
    image = image.orEmpty(),
    chef = chef.orEmpty(),
    time = time.orEmpty(),
    rating = rating ?: 0.0,
    ingredients = ingredients.map { it.toModel() },
)

fun Recipe.toDto(): RecipeDto = RecipeDto(
    id = id,
    category = category,
    name = name,
    image = image,
    chef = chef,
    time = time,
    rating = rating,
    ingredients = ingredients.map { it.toDto() },
)
