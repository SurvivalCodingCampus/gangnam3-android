package com.suwonsmartapp.recipeapp.data

import android.content.Context
import androidx.room.Room
import org.json.JSONArray
import org.json.JSONObject
<<<<<<< HEAD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
=======
>>>>>>> recipe/student/01_박강원

/**
 * 로컬(Room) 저장소 구현 — 저장소 인터페이스(RecipeRepository) 뒤 격리.
 * 기본 배선은 샘플 저장소 유지(2026-11-15 결정): Room 백엔드 활성화 시점·시딩 정책은
 * P1 게이트 ③(샘플 규모/출처)·D4 후속 — 앱 시작 시 빈 목록을 보여주는 기본값 교체는
 * 시딩 경로 확정 후. (교체 지점은 이 클래스 한 곳.)
 * Context 필요 → 순수 JVM 유닛테스트 불가(로컬 환경에 Robolectric 없음, 부록 B) —
 * instrumented 테스트/추상 팩토리 경로는 화면 테스트 경로 결정(사용자 항목 ①)과 함께.
 */
<<<<<<< HEAD
class RoomRecipeRepository(private val db: RecipeDatabase) : RecipeRepository {
    constructor(context: Context) : this(database(context))

    val recipeFlow = db.recipeDao().observeRecipes().map { entities -> entities.map(Companion::toDomain) }
    val bookmarkFlow = db.recipeDao().observeBookmarks()

    /** 기존 데이터는 덮어쓰지 않는다. 빈 DB일 때만 데모 데이터로 초기화한다. */
    suspend fun initialize() = withContext(Dispatchers.IO) {
        db.recipeDao().seedIfEmpty(sampleRecipes.map(Companion::toEntity))
    }

    suspend fun toggleBookmark(id: String) = withContext(Dispatchers.IO) { db.recipeDao().toggleBookmark(id) }
    suspend fun clearBookmarks() = withContext(Dispatchers.IO) { db.recipeDao().clearBookmarks() }

=======
class RoomRecipeRepository(private val context: Context) : RecipeRepository {

    private val db by lazy {
        Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "recipeapp.db").build()
    }

>>>>>>> recipe/student/01_박강원
    override fun recipes(): List<Recipe> = db.recipeDao().getAll().map(Companion::toDomain)

    /** 시딩/동기화용(미사용 골격) — 정책 확정 시 호출. */
    fun replaceAll(recipes: List<Recipe>) {
        db.recipeDao().upsertAll(recipes.map(Companion::toEntity))
    }

    companion object {
<<<<<<< HEAD
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `saved_recipes` (`recipeId` TEXT NOT NULL, PRIMARY KEY(`recipeId`), FOREIGN KEY(`recipeId`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)")
            }
        }

        @Volatile private var instance: RecipeDatabase? = null

        private fun database(context: Context): RecipeDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "recipeapp.db")
                .addMigrations(MIGRATION_1_2).build().also { instance = it }
        }

=======
>>>>>>> recipe/student/01_박강원
        fun toDomain(entity: RecipeEntity): Recipe = Recipe(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            chef = entity.chef,
            category = categoryOf(entity.category),
            cookingMinutes = entity.cookingMinutes,
            rating = entity.rating,
            reviews = entity.reviews,
            emoji = entity.emoji,
            isNew = entity.isNew,
            ingredients = ingredientsOf(entity.ingredientsJson),
            steps = stepsOf(entity.stepsJson),
            thumbnailUrl = entity.thumbnailUrl,
        )

        fun toEntity(recipe: Recipe): RecipeEntity = RecipeEntity(
            id = recipe.id,
            title = recipe.title,
            description = recipe.description,
            chef = recipe.chef,
            category = categoryName(recipe.category),
            cookingMinutes = recipe.cookingMinutes,
            rating = recipe.rating,
            reviews = recipe.reviews,
            emoji = recipe.emoji,
            isNew = recipe.isNew,
            ingredientsJson = ingredientsJson(recipe.ingredients),
            stepsJson = stepsJson(recipe.steps),
            thumbnailUrl = recipe.thumbnailUrl,
        )

        private fun categoryName(category: RecipeCategory): String = category.name

        private fun categoryOf(value: String): RecipeCategory =
            runCatching { RecipeCategory.valueOf(value) }.getOrDefault(RecipeCategory.Asian)

        private fun ingredientsJson(ingredients: List<Ingredient>): String =
            JSONArray(ingredients.map { JSONObject().put("name", it.name).put("amount", it.amount) }).toString()

        private fun ingredientsOf(value: String): List<Ingredient> = runCatching {
            val array = JSONArray(value)
            List(array.length()) { i -> val o = array.getJSONObject(i); Ingredient(o.getString("name"), o.getString("amount")) }
        }.getOrDefault(emptyList())

        private fun stepsJson(steps: List<String>): String = JSONArray(steps).toString()

        private fun stepsOf(value: String): List<String> = runCatching {
            val array = JSONArray(value)
            List(array.length()) { array.getString(it) }
        }.getOrDefault(emptyList())
    }
}
