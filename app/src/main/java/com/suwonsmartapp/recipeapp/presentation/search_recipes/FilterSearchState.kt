package com.suwonsmartapp.recipeapp.presentation.search_recipes

data class FilterSearchState(
    val time: FilterTime = FilterTime.ALL,
    val rate: Int? = null,
    val category: FilterCategory = FilterCategory.ALL,
)

enum class FilterTime {
    ALL,
    NEWEST,
    OLDEST,
    POPULARITY,
}

enum class FilterCategory {
    ALL,
    CEREAL,
    VEGETABLES,
    DINNER,
    CHINESE,
    LOCAL_DISH,
    FRUIT,
    BREAKFAST,
    SPANISH,
    LUNCH,
}
