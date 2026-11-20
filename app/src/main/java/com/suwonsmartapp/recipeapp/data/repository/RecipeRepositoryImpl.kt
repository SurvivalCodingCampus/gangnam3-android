package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.data.data_source.RecipeDataSource
import com.suwonsmartapp.recipeapp.data.data_source.RecipeDto
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.RecipeCategory
import com.suwonsmartapp.recipeapp.data.model.UserSummary

class RecipeRepositoryImpl(
    private val recipeDataSource: RecipeDataSource,
) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return recipeDataSource.getRecipes().map { it.toRecipe() }
    }

    // mock JSON 에 없는 값(videoUrl, reviewCount, createAt 등)은 기본값으로 채운다
    private fun RecipeDto.toRecipe(): Recipe {
        val chefName = chef.orEmpty()

        return Recipe(
            id = id?.toString().orEmpty(),
            title = name.orEmpty(),
            imageUrl = image.orEmpty(),
            videoUrl = "",
            chef = UserSummary(
                id = chefName,
                name = chefName,
                profileImageUrl = "",
                location = "",
            ),
            category = RecipeCategory.entries
                .find { it.name.equals(category, ignoreCase = true) }
                ?: RecipeCategory.UNKNOWN,
            reviewCount = 0,
            // "20 min" -> 20
            cookingTimeMinutes = time.orEmpty().filter { it.isDigit() }.toIntOrNull() ?: 0,
            createAt = 0L,
            rating = rating ?: 0.0,
        )
    }
}
