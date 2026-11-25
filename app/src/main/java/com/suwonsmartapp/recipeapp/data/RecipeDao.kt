package com.suwonsmartapp.recipeapp.data

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Recipes 테이블 접근 — 골격(조회 + 전체 교체).
 * 시딩/동기화/업데이트 정책은 P1 게이트 ③(샘플 규모·출처)·D4 후속.
 */
@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY rowid")
    fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM recipes ORDER BY rowid")
    fun observeRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT recipeId FROM saved_recipes ORDER BY recipeId")
    fun observeBookmarks(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM recipes")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInitial(entities: List<RecipeEntity>)

    @Transaction
    fun seedIfEmpty(entities: List<RecipeEntity>) {
        if (count() == 0) insertInitial(entities)
    }

    @Query("SELECT EXISTS(SELECT 1 FROM saved_recipes WHERE recipeId = :id)")
    fun isSaved(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(entity: SavedRecipeEntity)

    @Query("DELETE FROM saved_recipes WHERE recipeId = :id")
    fun unsave(id: String)

    @Transaction
    fun toggleBookmark(id: String) {
        if (isSaved(id)) unsave(id) else save(SavedRecipeEntity(id))
    }

    @Query("DELETE FROM saved_recipes")
    fun clearBookmarks()

    @androidx.room.Upsert
    fun upsertAll(entities: List<RecipeEntity>)
}
