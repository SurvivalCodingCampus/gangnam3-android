package com.suwonsmartapp.recipeapp.data.data_source

// JSON 모양 그대로의 데이터. 앱에서 쓰는 Recipe 로는 Repository 에서 변환한다
data class RecipeDto(
    val id: Int?,
    val category: String?,
    val name: String?,
    val image: String?,
    val chef: String?,
    val time: String?,
    val rating: Double?,
)
