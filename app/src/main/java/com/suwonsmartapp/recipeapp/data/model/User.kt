package com.suwonsmartapp.recipeapp.data.model

data class User(
    val id: Int,
    val name: String,
    val profileImageUrl: String,
    val job: String,
    val introduction: String,
    val location: String,
    val followerCount: Int,
    val followingCount: Int,
)
