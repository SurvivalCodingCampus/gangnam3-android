package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

// 로그인 요청 (앱 → 서버)
@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String,
)
