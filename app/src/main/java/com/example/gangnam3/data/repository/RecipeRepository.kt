package com.example.gangnam3.data.repository

import com.example.gangnam3.data.datasource.RecipeDataSource
import com.example.gangnam3.data.model.Recipe

/** ViewModel이 의존하는 저장소 규격. 구현을 바꿔도 ViewModel은 수정하지 않는다. */
interface RecipeRepository {
    /** 저장한 레시피 목록을 돌려준다. */
    suspend fun getSavedRecipes(): List<Recipe>
}

/** 데이터 소스에 조회를 위임하는 실제 구현. */
class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {
    override suspend fun getSavedRecipes(): List<Recipe> {
        return dataSource.fetchSavedRecipes()
    }
}