package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.core.NetworkError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
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
        override suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError> =
            Result.Success(fakeRecipes)
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

    @Test
    fun `레포지토리가 에러를 반환하면 레시피 목록은 비어 있다`() {
        val errorRepository = object : RecipeRepository {
            override suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError> =
                Result.Error(NetworkError.UNKNOWN)
        }
        val viewModel = SavedRecipesViewModel(errorRepository)
        assertEquals(emptyList<Recipe>(), viewModel.recipes.value)
    }
}
