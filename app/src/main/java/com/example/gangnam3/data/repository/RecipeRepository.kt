package com.example.gangnam3.data.repository

import com.example.gangnam3.data.datasource.RecipeDataSource
import com.example.gangnam3.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** ViewModel이 의존하는 저장소 규격. 구현을 바꿔도 ViewModel은 수정하지 않는다. */
interface RecipeRepository {
    /** 저장한 레시피 목록을 돌려준다. */
    suspend fun getSavedRecipes(): List<Recipe>

    /** 목록의 변화를 계속 알려준다. 변화를 알 수 없는 구현은 한 번만 조회해 내보낸다. */
    fun observeRecipes(): Flow<List<Recipe>> = flow { emit(getSavedRecipes()) }
}

/** 데이터 소스에 조회를 위임하는 실제 구현. */
class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {
    override suspend fun getSavedRecipes(): List<Recipe> {
        return dataSource.fetchSavedRecipes()
    }
}