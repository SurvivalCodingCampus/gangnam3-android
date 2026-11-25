package com.suwonsmartapp.recipeapp.data

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 로컬(Room) 저장 엔티티 — 명세 §2.2 Recipes 필드 매핑(2026-11-15, P1-α 후속).
 * 스칼라 컬럼만 유지(열거형/목록은 문자열로 정규화 — 매핑 코드는 RoomRecipeRepository 매퍼가 담당,
 * 의존성 추가 회피 위해 org.json 사용). org.json은 Android 전용(로컬 JVM 유닛테스트
 * 클래스패스엔 없음 — 변환 로직은 Robolectric으로 테스트한다).
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
    // 목록은 컬럼으로 나누지 않고 JSON 문자열로 저장한다.
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

/** Room 데이터베이스. 스키마를 바꾸면 version을 올리고 Migration을 추가해야 한다. */
@Database(entities = [RecipeEntity::class, SavedRecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : androidx.room.RoomDatabase() {
    /** Room이 구현을 자동 생성하는 DAO 접근점. */
    abstract fun recipeDao(): RecipeDao
}
