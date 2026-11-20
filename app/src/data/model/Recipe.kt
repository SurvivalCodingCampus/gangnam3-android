package com.example.gangnam3.data.model

// 불변 데이터 클래스로 레시피 정보 정의
data class Recipe(
    val id: Int,
    val title: String,
    val chefName: String,
    val cookingTime: String,
    val rating: Double,
    val imageUrl: String,
    val isBookmarked: Boolean = true
)