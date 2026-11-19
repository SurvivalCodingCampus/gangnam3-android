package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {

    // 지연된 초기화
    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(FakeRecipeDataSource())
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}
