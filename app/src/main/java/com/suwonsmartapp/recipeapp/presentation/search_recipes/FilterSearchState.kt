package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.annotation.StringRes
import com.suwonsmartapp.recipeapp.R

data class FilterSearchState(
    val time: FilterTime = FilterTime.ALL,
    val rate: Int? = null,
    val category: FilterCategory = FilterCategory.ALL,
)

enum class FilterTime(@StringRes val label: Int) {
    ALL(R.string.filter_search_all),
    NEWEST(R.string.filter_time_newest),
    OLDEST(R.string.filter_time_oldest),
    POPULARITY(R.string.filter_time_popularity),
}

enum class FilterCategory(@StringRes val label: Int) {
    ALL(R.string.filter_search_all),
    CEREAL(R.string.filter_category_cereal),
    VEGETABLES(R.string.filter_category_vegetables),
    DINNER(R.string.filter_category_dinner),
    CHINESE(R.string.filter_category_chinese),
    LOCAL_DISH(R.string.filter_category_local_dish),
    FRUIT(R.string.filter_category_fruit),
    BREAKFAST(R.string.filter_category_breakfast),
    SPANISH(R.string.filter_category_spanish),
    LUNCH(R.string.filter_category_lunch),
}
