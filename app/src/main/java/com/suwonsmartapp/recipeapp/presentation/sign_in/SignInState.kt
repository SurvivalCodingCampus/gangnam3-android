package com.suwonsmartapp.recipeapp.presentation.sign_in

// 로그인 화면 상태
data class SignInState(
    val email: String = "",
    val password: String = "",
    // 로그인 실패하면 보여줄 문구 (null이면 안 보임)
    val errorMessage: String? = null,
)
