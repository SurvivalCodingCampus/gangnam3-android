package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.SignInRequestDto
import com.suwonsmartapp.recipeapp.core.data.dto.SignInResponseDto
import com.suwonsmartapp.recipeapp.core.network.Response
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import kotlinx.serialization.json.Json

// 서버 대신 qwer / 1234 계정 하나만 통과시키는 가짜 로그인
class FakeAuthDataSource(
    private val authJson: String = """
        {
            "accessToken": "fake-access-token"
        }
    """.trimIndent(),
) : AuthDataSource {
    override suspend fun signIn(request: SignInRequestDto): Response<SignInResponseDto> {
        // 계정이 틀리면 401
        if (request.email != VALID_EMAIL || request.password != VALID_PASSWORD) {
            return Response(statusCode = StatusCode.Unauthorized, body = null)
        }

        return Response(
            statusCode = StatusCode.Ok,
            body = Json.decodeFromString<SignInResponseDto>(authJson),
        )
    }

    companion object {
        private const val VALID_EMAIL = "qwer"
        private const val VALID_PASSWORD = "1234"
    }
}
