package com.example.gangnam3.data.repository

import com.example.gangnam3.data.model.Recipe

interface RecipeRepository {
    suspend fun getSavedRecipes(): List<Recipe>
}

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {
    override suspend fun getSavedRecipes(): List<Recipe> {
        return dataSource.fetchSavedRecipes()
    }
}