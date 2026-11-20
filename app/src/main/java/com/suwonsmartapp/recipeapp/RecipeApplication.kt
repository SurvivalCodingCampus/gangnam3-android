package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import com.suwonsmartapp.recipeapp.data.repository.CounterRepositoryImpl
import com.suwonsmartapp.recipeapp.data.data_source.MockRecipeDataSource
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {
    // 지연된 초기화
    val counterRepository: CounterRepository by lazy {
        CounterRepositoryImpl()
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(MockRecipeDataSource())
    }

    // 의존성 주입
    override fun onCreate() {
        super.onCreate()
    }
}
