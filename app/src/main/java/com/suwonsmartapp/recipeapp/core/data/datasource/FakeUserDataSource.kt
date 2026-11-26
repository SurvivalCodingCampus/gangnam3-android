package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.UserDto
import com.suwonsmartapp.recipeapp.core.network.Response
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import kotlinx.serialization.json.Json

class FakeUserDataSource(
    private val statusCode: StatusCode = StatusCode.Ok,
    private val userJson: String = """
        {
            "name": "Jega",
            "profileUrl": "https://cdn.pixabay.com/photo/2015/03/03/08/55/portrait-657116_1280.jpg"
        }
    """.trimIndent(),
) : UserDataSource {
    override suspend fun getUser(): Response<UserDto> {
        if (!statusCode.isSuccess()) {
            return Response(statusCode = statusCode, body = null)
        }

        return Response(
            statusCode = statusCode,
            body = Json.decodeFromString<UserDto>(userJson),
        )
    }
}
