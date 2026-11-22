package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {
    val recipeDataSource: RecipeDataSource by lazy {
        FakeRecipeDataSource()
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeDataSource)
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}
