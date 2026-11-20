package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.data.data_source.RecipeDataSource
import com.suwonsmartapp.recipeapp.domain.model.recipe.Recipe

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource,
): RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> = dataSource.loadRecipes()
}