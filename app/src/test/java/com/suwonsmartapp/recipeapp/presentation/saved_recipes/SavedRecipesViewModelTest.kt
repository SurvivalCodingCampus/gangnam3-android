package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.utils.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SavedRecipesViewModelTest {

    private val fakeRecipes = listOf(
        Recipe(
            id = 1,
            category = "Indian",
            name = "Traditional spare ribs baked",
            imageUrl = "",
            chef = "Chef John",
            time = "20 min",
            rating = 4.0,
        ),
        Recipe(
            id = 2,
            category = "Asian",
            name = "Spice roasted chicken with flavored rice",
            imageUrl = "",
            chef = "Mark Kelvin",
            time = "20 min",
            rating = 4.0,
        ),
    )

    private val fakeRepository = object : RecipeRepository {
        override suspend fun getSavedRecipes(): List<Recipe> = fakeRecipes
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

//    @Before
//    fun setUp() {
//        Dispatchers.setMain(UnconfinedTestDispatcher())
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }

    @Test
    fun `ViewModel 생성 시 init 블록에서 저장된 레시피를 로드한다`() {
        val viewModel = SavedRecipesViewModel(fakeRepository)
        assertEquals(fakeRecipes, viewModel.recipes.value)
    }
}
