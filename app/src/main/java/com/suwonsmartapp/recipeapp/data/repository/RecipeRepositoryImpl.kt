package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.core.NetworkError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.data.data_source.RecipeDataSource
import com.suwonsmartapp.recipeapp.data.mapper.toModel
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
import kotlin.coroutines.cancellation.CancellationException

class RecipeRepositoryImpl(
    private val recipeDataSource: RecipeDataSource,
) : RecipeRepository {

    override suspend fun getRecipes(): Result<List<Recipe>, NetworkError> {
        return try {
            Result.Success(recipeDataSource.getRecipes().map { it.toModel() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError> = getRecipes()
}
