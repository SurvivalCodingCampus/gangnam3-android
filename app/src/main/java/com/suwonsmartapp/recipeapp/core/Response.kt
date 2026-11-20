package com.suwonsmartapp.recipeapp.core

data class Response<T>(
    val statusCode: Int,
    val headers: Map<String, List<String>> = emptyMap(),
    val body: T? = null,
)
