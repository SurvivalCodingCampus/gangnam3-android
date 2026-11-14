package com.suwonsmartapp.recipeapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suwonsmartapp.recipeapp.data.RecipeDatabase
import com.suwonsmartapp.recipeapp.data.RoomRecipeRepository
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Room 영속성 계측 테스트 — 기기/에뮬레이터 연결 시 `connectedDebugAndroidTest`로 실행. */
@RunWith(AndroidJUnit4::class)
class RoomRecipeRepositoryTest {
    private lateinit var db: RecipeDatabase
    private lateinit var repository: RoomRecipeRepository

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        repository = RoomRecipeRepository(db)
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun initializeSeedsOnlyWhenEmpty() = runBlocking {
        assertEquals(0, repository.recipes().size)
        repository.initialize()
        assertEquals(sampleRecipes.size, repository.recipes().size)
        repository.initialize()
        assertEquals(sampleRecipes.size, repository.recipes().size)
    }

    @Test
    fun domainMappingRoundTripsSampleData() = runBlocking {
        repository.initialize()
        assertEquals(sampleRecipes, repository.recipes())
    }

    @Test
    fun bookmarkTogglePersistsAcrossRepositoryInstances() = runBlocking {
        repository.initialize()
        val id = sampleRecipes.first().id
        repository.toggleBookmark(id)
        assertEquals(listOf(id), repository.bookmarkFlow.first())

        val reopened = RoomRecipeRepository(db)
        assertTrue(reopened.bookmarkFlow.first().contains(id))

        reopened.toggleBookmark(id)
        assertTrue(reopened.bookmarkFlow.first().isEmpty())

        repository.toggleBookmark(id)
        repository.clearBookmarks()
        assertTrue(repository.bookmarkFlow.first().isEmpty())
    }
}
