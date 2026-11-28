package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

// 로그인 응답 (서버 → 앱)
@Serializable
data class SignInResponseDto(
    val accessToken: String? = null,
)
