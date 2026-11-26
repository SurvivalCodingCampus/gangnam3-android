package com.suwonsmartapp.recipeapp

import android.app.Application
import com.suwonsmartapp.recipeapp.core.data.datasource.BookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeBookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeUserDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.RecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.UserDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepository
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepositoryImpl
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl
import com.suwonsmartapp.recipeapp.core.data.repository.UserRepository
import com.suwonsmartapp.recipeapp.core.data.repository.UserRepositoryImpl

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

    val userDataSource: UserDataSource by lazy {
        FakeUserDataSource()
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userDataSource)
    }

    override fun onCreate() {
        super.onCreate()

        // 의존성 주입하는 부분
    }
}
