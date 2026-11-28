package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.SignInRequestDto
import com.suwonsmartapp.recipeapp.core.data.dto.SignInResponseDto
import com.suwonsmartapp.recipeapp.core.network.Response

interface AuthDataSource {
    // 서버 쿼리 예: POST /sign-in (body: email, password) → 로그인 토큰
    suspend fun signIn(request: SignInRequestDto): Response<SignInResponseDto>
}
