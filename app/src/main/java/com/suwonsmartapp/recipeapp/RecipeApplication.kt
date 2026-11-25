package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.data.data_source.MockRecipeDataSourceImpl
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(MockRecipeDataSourceImpl())
    }
}
