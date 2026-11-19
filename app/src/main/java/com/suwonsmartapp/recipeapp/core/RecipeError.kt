package com.suwonsmartapp.recipeapp.core

enum class RecipeError {
    SERVER_ERR,     // 5xx
    CLIENT_ERR,     // 4xx
    NULL_DATA_ERR,  // body가 null
    NOT_FOUND_ERR,  // 해당 id 레시피 없음
    PARSE_ERR,      // JSON 파싱 실패
    UNKNOWN_ERR,
}
