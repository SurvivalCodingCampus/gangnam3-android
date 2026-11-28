package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.datasource.BookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.mapper.toModel
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException
import java.net.SocketException
import kotlin.time.Duration.Companion.seconds

class BookmarkRepositoryImpl(
    private val dataSource: BookmarkDataSource,
) : BookmarkRepository {
    override suspend fun getBookmarkedRecipes(
        category: String?,
    ): Result<List<Recipe>, NetworkError> {
        try {
            delay(1.seconds)
            val response = dataSource.getBookmarkedRecipes(category = category)

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
            throw e
        } catch (_: Exception) {
            return Result.Error(NetworkError.UNKNOWN_ERR)
        }
    }
}
