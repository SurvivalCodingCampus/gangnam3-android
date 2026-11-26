package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.domain.model.Recipe

data class HomeState(
    val userName: String = "Jega",
    val selectedCategory: RecipeCategory = RecipeCategory.ALL,
    val recipes: List<Recipe> = emptyList(),
    val bookmarkedIds: Set<Int> = emptySet(),
) {
    val filteredRecipes: List<Recipe>
        get() = if (selectedCategory == RecipeCategory.ALL) {
            recipes
        } else {
            recipes.filter { it.category.equals(selectedCategory.name, ignoreCase = true) }
        }
}

enum class RecipeCategory {
    ALL,
    INDIAN,
    ITALIAN,
    ASIAN,
    CHINESE,
}
