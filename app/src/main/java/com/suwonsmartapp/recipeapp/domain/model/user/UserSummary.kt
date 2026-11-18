package com.suwonsmartapp.recipeapp.domain.model.user

data class UserSummary(
    val id: String,
    val name: String,
    val profileImageUrl: String?,
    val location: String?,
)
