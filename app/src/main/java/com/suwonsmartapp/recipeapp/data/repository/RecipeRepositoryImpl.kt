package com.suwonsmartapp.recipeapp.data.repository

import com.suwonsmartapp.recipeapp.data.data_source.RecipeDataSource
import com.suwonsmartapp.recipeapp.data.model.Recipe

class RecipeRepositoryImpl(
    private val recipeDataSource: RecipeDataSource,
) : RecipeRepository {

    override suspend fun getSavedRecipes(): List<Recipe> = recipeDataSource.getRecipes()
}
