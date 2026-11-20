package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.data.data_source.MockRecipeDataSourceImpl
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import com.suwonsmartapp.recipeapp.data.repository.CounterRepositoryImpl
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {
    
    // 지연된 초기화 by lazy
    val counterRepository: CounterRepository by lazy {  // 꼭 인터페이스인 :CounterRepository를 정의해놓기
        CounterRepositoryImpl()
    }
    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(MockRecipeDataSourceImpl())
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // 의존성 주입하는 부분
    }
}