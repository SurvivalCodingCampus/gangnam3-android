package com.suwonsmartapp.recipeapp.core.data.mapper

import com.suwonsmartapp.recipeapp.core.data.dto.UserDto
import com.suwonsmartapp.recipeapp.core.data.model.User

fun UserDto.toModel(): User = User(
    name = name.orEmpty(),
    profileUrl = profileUrl.orEmpty(),
)

fun User.toDto(): UserDto = UserDto(
    name = name,
    profileUrl = profileUrl,
)
