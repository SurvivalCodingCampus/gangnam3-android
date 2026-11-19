package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.core.data.repository.FakeRecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository

class RecipeApplication : Application() {

    // 지연된 초기화
    val recipeRepository: RecipeRepository by lazy {
        FakeRecipeRepository()
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}