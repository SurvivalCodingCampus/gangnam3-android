package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.RecipeError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.mapper.toModel
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.serialization.SerializationException

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource,
) : RecipeRepository {
    override suspend fun getRecipe(id: Long): Result<Recipe, RecipeError> {
        return when (val result = getRecipes()) {
            is Result.Success -> {
                val recipe = result.value.firstOrNull { it.id == id }
                if (recipe == null) {
                    Result.Error(RecipeError.NOT_FOUND_ERR)
                } else {
                    Result.Success(recipe)
                }
            }

            is Result.Error -> result
        }
    }

    override suspend fun getRecipes(): Result<List<Recipe>, RecipeError> {
        return try {
            val response = dataSource.getRecipes()

            when (response.statusCode) {
                in 500..599 -> Result.Error(RecipeError.SERVER_ERR)
                in 400..499 -> Result.Error(RecipeError.CLIENT_ERR)
                else -> {
                    val body = response.body
                    if (body == null) {
                        Result.Error(RecipeError.NULL_DATA_ERR)
                    } else {
                        Result.Success(body.recipes.orEmpty().map { it.toModel() })
                    }
                }
            }
        } catch (_: SerializationException) {
            Result.Error(RecipeError.PARSE_ERR)
        } catch (_: Exception) {
            Result.Error(RecipeError.UNKNOWN_ERR)
        }
    }
}
