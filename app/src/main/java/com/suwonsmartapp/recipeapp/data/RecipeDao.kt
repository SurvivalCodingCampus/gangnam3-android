package com.suwonsmartapp.recipeapp.data

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Insert

/**
 * Recipes 테이블 접근 — 골격(조회 + 전체 교체).
 * 시딩/동기화/업데이트 정책은 P1 게이트 ③(샘플 규모·출처)·D4 후속.
 */
@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(entities: List<RecipeEntity>)
}
