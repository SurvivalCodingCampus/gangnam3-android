package com.suwonsmartapp.recipeapp.core.data.mapper

import com.suwonsmartapp.recipeapp.core.data.dto.SignInResponseDto
import com.suwonsmartapp.recipeapp.core.data.model.Auth

fun SignInResponseDto.toModel(): Auth = Auth(
    accessToken = accessToken.orEmpty(),
)
