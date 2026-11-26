package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.core.data.datasource.BookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeBookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepository
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepositoryImpl
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl

class RecipeApplication : Application() {
    val recipeDataSource: RecipeDataSource by lazy {
        FakeRecipeDataSource()
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeDataSource)
    }

    val bookmarkDataSource: BookmarkDataSource by lazy {
        FakeBookmarkDataSource()
    }

    val bookmarkRepository: BookmarkRepository by lazy {
        BookmarkRepositoryImpl(bookmarkDataSource)
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}
