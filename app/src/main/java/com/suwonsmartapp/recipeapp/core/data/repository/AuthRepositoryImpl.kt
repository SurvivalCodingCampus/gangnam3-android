package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.datasource.AuthDataSource
import com.suwonsmartapp.recipeapp.core.data.dto.SignInRequestDto
import com.suwonsmartapp.recipeapp.core.data.mapper.toModel
import com.suwonsmartapp.recipeapp.core.data.model.Auth
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import java.net.SocketException

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<Auth, NetworkError> {
        try {
            val request = SignInRequestDto(email = email, password = password)
            val response = dataSource.signIn(request)

            when {
                response.statusCode.isServerError() -> return Result.Error(NetworkError.SERVER_ERR)
                response.statusCode.isClientError() -> return Result.Error(NetworkError.CLIENT_ERR)
            }

            val responseDto = response.body ?: return Result.Error(NetworkError.NULL_DATA_ERR)

            return Result.Success(responseDto.toModel())
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
