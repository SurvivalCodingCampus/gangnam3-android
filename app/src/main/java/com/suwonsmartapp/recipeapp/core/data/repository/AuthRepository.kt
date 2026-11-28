package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.Auth
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result

interface AuthRepository {
    // 로그인 (계정이 틀리면(401) UNAUTHORIZED_ERR, 나머지 4xx는 CLIENT_ERR)
    suspend fun signIn(email: String, password: String): Result<Auth, NetworkError>
}
