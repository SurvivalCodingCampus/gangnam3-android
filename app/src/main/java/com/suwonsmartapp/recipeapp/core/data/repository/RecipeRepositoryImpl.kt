package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.RecipeError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource,
) : RecipeRepository {
    override fun getRecipe(id: Long): Flow<Result<Recipe, RecipeError>> {
        return getRecipes().map { result ->
            when (result) {
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
    }

    override fun getRecipes(): Flow<Result<List<Recipe>, RecipeError>> = flow {
        // emit은 try 밖에서 해야 함 (Flow 예외 투명성 규칙)
        val result: Result<List<Recipe>, RecipeError> = try {
            val response = dataSource.getRecipes()

            when (response.statusCode) {
                in 500..599 -> Result.Error(RecipeError.SERVER_ERR)
                in 400..499 -> Result.Error(RecipeError.CLIENT_ERR)
                else -> {
                    val body = response.body
                    if (body == null) {
                        Result.Error(RecipeError.NULL_DATA_ERR)
                    } else {
                        Result.Success(body.recipes)
                    }
                }
            }
        } catch (_: SerializationException) {
            Result.Error(RecipeError.PARSE_ERR)
        } catch (_: Exception) {
            Result.Error(RecipeError.UNKNOWN_ERR)
        }
        emit(result)
    }
}
