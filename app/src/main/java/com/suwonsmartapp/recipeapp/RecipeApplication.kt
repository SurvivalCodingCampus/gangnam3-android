package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import com.suwonsmartapp.recipeapp.data.repository.CounterRepositoryImpl
import com.suwonsmartapp.recipeapp.data.repository.FakeRecipeRepository
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository

class RecipeApplication : Application() {

    // 지연된 초기화
    val counterRepository: CounterRepository by lazy {
        CounterRepositoryImpl()
    }

    val recipeRepository: RecipeRepository by lazy {
        FakeRecipeRepository()
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}