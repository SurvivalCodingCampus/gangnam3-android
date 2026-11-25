package com.suwonsmartapp.recipeapp.core.network

data class Response<T>(
    val statusCode: StatusCode,
    val headers: Map<String, List<String>> = emptyMap(),
    val body: T? = null,
)
