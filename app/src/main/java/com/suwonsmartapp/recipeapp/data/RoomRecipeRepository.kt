package com.suwonsmartapp.recipeapp.data

import android.content.Context
import androidx.room.Room
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * 로컬(Room) 저장소 구현 — 저장소 인터페이스(RecipeRepository) 뒤 격리.
 * 앱이 실제로 사용하는 저장소이며, 빈 DB는 initialize()에서 샘플로 채운다.
 * Context가 필요해 순수 JVM 테스트 대신 Robolectric/계측 테스트로 검증한다.
 */
class RoomRecipeRepository(private val db: RecipeDatabase) : RecipeRepository {
    /** 앱에서 쓰는 생성자. 공용 DB 인스턴스를 사용한다. 주 생성자는 테스트에서 메모리 DB를 넣을 때 쓴다. */
    constructor(context: Context) : this(database(context))

    /** DB 엔티티를 화면용 Recipe로 바꿔 내보내는 Flow. */
    val recipeFlow = db.recipeDao().observeRecipes().map { entities -> entities.map(Companion::toDomain) }
    /** 북마크한 레시피 id 목록 Flow. */
    val bookmarkFlow = db.recipeDao().observeBookmarks()

    /** 기존 데이터는 덮어쓰지 않는다. 빈 DB일 때만 데모 데이터로 초기화한다. */
    suspend fun initialize() = withContext(Dispatchers.IO) {
        db.recipeDao().seedIfEmpty(sampleRecipes.map(Companion::toEntity))
    }

    /** 북마크를 켜거나 끈다. DB 작업은 IO 스레드에서 실행한다. */
    suspend fun toggleBookmark(id: String) = withContext(Dispatchers.IO) { db.recipeDao().toggleBookmark(id) }
    /** 모든 북마크를 지운다. */
    suspend fun clearBookmarks() = withContext(Dispatchers.IO) { db.recipeDao().clearBookmarks() }

    /** 현재 목록을 즉시 조회한다. 동기 DB 접근이므로 메인 스레드에서 호출하면 안 된다. */
    override fun recipes(): List<Recipe> = db.recipeDao().getAll().map(Companion::toDomain)

    /** 시딩/동기화용(미사용 골격) — 정책 확정 시 호출. */
    fun replaceAll(recipes: List<Recipe>) {
        db.recipeDao().upsertAll(recipes.map(Companion::toEntity))
    }

    companion object {
        /** v1→v2: 북마크 테이블 추가. SQL이 엔티티 정의와 정확히 같아야 앱이 열린다. */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `saved_recipes` (`recipeId` TEXT NOT NULL, PRIMARY KEY(`recipeId`), FOREIGN KEY(`recipeId`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)")
            }
        }

        // 앱 전체에서 DB를 하나만 쓴다. Volatile로 다른 스레드에도 최신 값이 보이게 한다.
        @Volatile private var instance: RecipeDatabase? = null

        /** 이중 확인 잠금으로 여러 스레드가 동시에 호출해도 한 번만 생성한다. */
        private fun database(context: Context): RecipeDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "recipeapp.db")
                .addMigrations(MIGRATION_1_2).build().also { instance = it }
        }

        /** DB 엔티티 → 화면용 모델. */
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

        /** 화면용 모델 → DB 엔티티. */
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

        // 알 수 없는 분류가 저장되어 있어도 앱이 죽지 않도록 기본값을 사용한다.
        private fun categoryOf(value: String): RecipeCategory =
            runCatching { RecipeCategory.valueOf(value) }.getOrDefault(RecipeCategory.Asian)

        private fun ingredientsJson(ingredients: List<Ingredient>): String =
            JSONArray(ingredients.map { JSONObject().put("name", it.name).put("amount", it.amount) }).toString()

        // JSON이 손상된 경우 예외 대신 빈 목록으로 처리한다.
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
