package com.suwonsmartapp.recipeapp.presentation.home

import androidx.annotation.StringRes
import com.suwonsmartapp.recipeapp.R

data class HomeState(
    val userName: String = "Jega",
    val selectedCategory: RecipeCategory = RecipeCategory.ALL,
)

enum class RecipeCategory(@StringRes val label: Int) {
    ALL(R.string.home_category_all),
    INDIAN(R.string.home_category_indian),
    ITALIAN(R.string.home_category_italian),
    ASIAN(R.string.home_category_asian),
    CHINESE(R.string.home_category_chinese),
}
