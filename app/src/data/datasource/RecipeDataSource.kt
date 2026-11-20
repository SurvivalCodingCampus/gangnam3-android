package com.example.gangnam3.data.datasource

import com.example.gangnam3.data.model.Recipe

interface RecipeDataSource {
    suspend fun fetchSavedRecipes(): List<Recipe>
}

class MockRecipeDataSource : RecipeDataSource {
    override suspend fun fetchSavedRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                title = "Traditional spare ribs baked",
                chefName = "Chef John",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 2,
                title = "spice roasted chicken with flavored rice",
                chefName = "Mark Kelvin",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2017/05/07/08/56/pancakes-2291908_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 3,
                title = "Spicy fried rice mix chicken bali",
                chefName = "Spicy Nelly",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 4,
                title = "Lamb chops with fruity couscous and mint",
                chefName = "Spicy Nelly",
                cookingTime = "20 min",
                rating = 3.0,
                imageUrl = "https://cdn.pixabay.com/photo/2016/06/01/21/40/steak-1429796_1280.jpg",
                isBookmarked = true
            )
        )
    }
}