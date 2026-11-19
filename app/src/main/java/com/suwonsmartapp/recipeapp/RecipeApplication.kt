package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import com.suwonsmartapp.recipeapp.data.repository.CounterRepositoryImpl

class RecipeApplication : Application() {

    // 지연된 초기화
    val counterRepository: CounterRepository by lazy {
        CounterRepositoryImpl()
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}