package com.suwonsmartapp.recipeapp.core.util

/*
각 단어의 첫 글자만 대문자로 변경
- 공백 기준으로 단어들을 쪼개서 각 단어의 첫 글자만 대문자로 변경
 */
fun String.capitalizeWords(): String {
    return this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}