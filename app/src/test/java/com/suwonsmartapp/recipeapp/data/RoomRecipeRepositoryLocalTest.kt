package com.suwonsmartapp.recipeapp.data

import androidx.room.Room
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** 기기 없이 메모리 DB로 저장소의 시딩·변환·북마크 동작을 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RoomRecipeRepositoryLocalTest {
    private lateinit var db: RecipeDatabase
    private lateinit var repository: RoomRecipeRepository

    @Before
    fun setUp() {
        // 테스트마다 새 메모리 DB를 만들어 서로 영향을 주지 않게 한다.
        db = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.getApplication(), RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = RoomRecipeRepository(db)
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun `빈 DB는 샘플로 초기화되고 순서와 내용이 보존된다`() = runTest {
        repository.initialize()

        // 재료·단계가 JSON 문자열을 거쳐도 원본과 같아야 한다.
        assertEquals(sampleRecipes, repository.recipeFlow.first())
    }

    @Test
    fun `이미 데이터가 있으면 초기화해도 덮어쓰지 않는다`() = runTest {
        val edited = sampleRecipes.first().copy(title = "수정한 제목")
        repository.replaceAll(listOf(edited))

        repository.initialize()

        assertEquals(listOf(edited), repository.recipes())
    }

    @Test
    fun `북마크는 토글되고 전체 삭제된다`() = runTest {
        repository.initialize()

        repository.toggleBookmark("r1")
        repository.toggleBookmark("r2")
        assertEquals(listOf("r1", "r2"), repository.bookmarkFlow.first())

        repository.toggleBookmark("r1")
        assertEquals(listOf("r2"), repository.bookmarkFlow.first())

        repository.clearBookmarks()
        assertTrue(repository.bookmarkFlow.first().isEmpty())
    }

    @Test
    fun `레시피를 갱신해도 북마크가 유지된다`() = runTest {
        repository.initialize()
        repository.toggleBookmark("r1")

        // REPLACE였다면 CASCADE로 북마크가 사라진다. Upsert라서 유지되어야 한다.
        repository.replaceAll(sampleRecipes)

        assertEquals(listOf("r1"), repository.bookmarkFlow.first())
    }

    @Test
    fun `손상된 JSON과 알 수 없는 분류는 기본값으로 복구된다`() {
        val entity = RoomRecipeRepository.toEntity(sampleRecipes.first())
            .copy(category = "Unknown", ingredientsJson = "{깨짐", stepsJson = "")

        val recipe = RoomRecipeRepository.toDomain(entity)

        assertEquals(RecipeCategory.Asian, recipe.category)
        assertTrue(recipe.ingredients.isEmpty())
        assertTrue(recipe.steps.isEmpty())
    }
}
