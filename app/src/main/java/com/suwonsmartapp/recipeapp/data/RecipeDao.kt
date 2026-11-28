package com.suwonsmartapp.recipeapp.data

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * 레시피와 북마크 테이블 접근 객체(DAO).
 * Flow 함수를 제외한 함수는 동기 실행이므로 반드시 IO 스레드에서 호출한다.
 */
@Dao
interface RecipeDao {
    /** 전체 레시피를 한 번 조회한다. 저장한 순서(rowid)를 유지한다. */
    @Query("SELECT * FROM recipes ORDER BY rowid")
    fun getAll(): List<RecipeEntity>

    /** 테이블이 바뀔 때마다 최신 목록을 다시 내보내는 Flow. */
    @Query("SELECT * FROM recipes ORDER BY rowid")
    fun observeRecipes(): Flow<List<RecipeEntity>>

    /** 북마크한 레시피 id 목록을 관찰한다. */
    @Query("SELECT recipeId FROM saved_recipes ORDER BY recipeId")
    fun observeBookmarks(): Flow<List<String>>

    /** 저장된 레시피 수. 시딩이 필요한지 판단할 때 쓴다. */
    @Query("SELECT COUNT(*) FROM recipes")
    fun count(): Int

    /** 같은 id가 이미 있으면 무시하고 새 항목만 넣는다. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInitial(entities: List<RecipeEntity>)

    /** 개수 확인과 삽입을 한 트랜잭션으로 묶어 중복 시딩을 막는다. */
    @Transaction
    fun seedIfEmpty(entities: List<RecipeEntity>) {
        if (count() == 0) insertInitial(entities)
    }

    /** 해당 레시피가 북마크되어 있는지 확인한다. */
    @Query("SELECT EXISTS(SELECT 1 FROM saved_recipes WHERE recipeId = :id)")
    fun isSaved(id: String): Boolean

    /** 북마크를 추가한다. 이미 있으면 무시한다. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(entity: SavedRecipeEntity)

    /** 북마크를 해제한다. */
    @Query("DELETE FROM saved_recipes WHERE recipeId = :id")
    fun unsave(id: String)

    /** 확인과 변경을 한 트랜잭션으로 처리해 빠르게 연속으로 눌러도 상태가 꼬이지 않는다. */
    @Transaction
    fun toggleBookmark(id: String) {
        if (isSaved(id)) unsave(id) else save(SavedRecipeEntity(id))
    }

    /** 북마크만 모두 지운다. 레시피는 유지된다. */
    @Query("DELETE FROM saved_recipes")
    fun clearBookmarks()

    /** 있으면 수정, 없으면 추가한다. REPLACE와 달리 행을 지우지 않아 북마크(FK)가 유지된다. */
    @androidx.room.Upsert
    fun upsertAll(entities: List<RecipeEntity>)
}
