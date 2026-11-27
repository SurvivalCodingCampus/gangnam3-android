package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.data.model.Recipe

data class HomeState(
    val userName: String = "Jega",
    val selectedCategory: RecipeCategory = RecipeCategory.ALL,
    val recipes: List<Recipe> = emptyList(),
    val bookmarkedIds: Set<String> = emptySet(),
) {
    val filteredRecipes: List<Recipe>
        get() = if (selectedCategory == RecipeCategory.ALL) {
            recipes
        } else {
            recipes.filter { it.category.name == selectedCategory.name }
        }
}

enum class RecipeCategory {
    ALL,
    INDIAN,
    ITALIAN,
    ASIAN,
    CHINESE,
}
