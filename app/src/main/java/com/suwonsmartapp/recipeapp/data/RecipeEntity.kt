package com.suwonsmartapp.recipeapp.data

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 로컬(Room) 저장 엔티티 — 명세 §2.2 Recipes 필드 매핑(2026-11-15, P1-α 후속).
 * 스칼라 컬럼만 유지(열거형/목록은 문자열로 정규화 — 매핑 코드는 RoomRecipeRepository 매퍼가 담당,
 * 의존성 추가 회피 위해 org.json 사용). org.json은 Android 전용(로컬 JVM 유닛테스트
 * 클래스패스엔 없음 — 변환 로직 테스트는 instrumented/Robolectric 경로 필요, 부록 B 기록).
 * 시딩/동기화 정책은 P1 게이트 ③(샘플 규모·출처) — 이 엔티티/DAO는 골격이며 기본 배선은 샘플 유지.
 */
@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val chef: String,
    val category: String,
    val cookingMinutes: Int,
    val rating: Double,
    val reviews: Int,
    val emoji: String,
    val isNew: Boolean,
    val ingredientsJson: String,
    val stepsJson: String,
    val thumbnailUrl: String? = null,
)

/** 저장(북마크) 목록 — 레시피 삭제 시 함께 정리되도록 CASCADE FK. */
@Entity(tableName = "saved_recipes", foreignKeys = [androidx.room.ForeignKey(
    entity = RecipeEntity::class,
    parentColumns = ["id"], childColumns = ["recipeId"],
    onDelete = androidx.room.ForeignKey.CASCADE
)])
data class SavedRecipeEntity(@PrimaryKey val recipeId: String)

@Database(entities = [RecipeEntity::class, SavedRecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : androidx.room.RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
