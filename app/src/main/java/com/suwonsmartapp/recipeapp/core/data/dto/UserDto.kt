package com.suwonsmartapp.recipeapp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val name: String? = null,
    val profileUrl: String? = null,
)
