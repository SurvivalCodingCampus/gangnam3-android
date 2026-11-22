package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.mapper.toModel
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException
import java.net.SocketException
import kotlin.time.Duration.Companion.seconds

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource,
) : RecipeRepository {
    override suspend fun getRecipes(
        query: String?,
        rate: Int?,
        category: String?,
        sort: String?,
    ): Result<List<Recipe>, NetworkError> {
        try {
            delay(1.seconds)
            val response = dataSource.getRecipes(
                query = query,
                rate = rate,
                category = category,
                sort = sort,
            )

            when {
                response.statusCode.isServerError() -> return Result.Error(NetworkError.SERVER_ERR)
                response.statusCode.isClientError() -> return Result.Error(NetworkError.CLIENT_ERR)
            }

            val recipesDto = response.body ?: return Result.Error(NetworkError.NULL_DATA_ERR)

            return Result.Success(recipesDto.recipes.map { it.toModel() })
        } catch (_: SerializationException) {
            return Result.Error(NetworkError.PARSE_ERR)
        } catch (_: SocketException) {
            return Result.Error(NetworkError.NO_INTERNET_ERR)
        } catch (_: TimeoutCancellationException) {
            return Result.Error(NetworkError.TIMEOUT_ERR)
        } catch (e: CancellationException) {
            // 새 검색으로 이전 요청이 취소된 것 → 에러가 아니니까 그대로 던짐
            throw e
        } catch (_: Exception) {
            return Result.Error(NetworkError.UNKNOWN_ERR)
        }
    }
}
