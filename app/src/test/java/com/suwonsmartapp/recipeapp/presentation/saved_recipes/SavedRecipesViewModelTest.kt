package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.MainDispatcherRule
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.RecipeCategory
import com.suwonsmartapp.recipeapp.data.model.UserSummary
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedRecipesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeRecipeRepository(
        private val recipes: List<Recipe>,
    ) : RecipeRepository {
        override suspend fun getRecipes(): List<Recipe> = recipes
    }

    private fun recipe(id: String) = Recipe(
        id = id,
        title = "Recipe $id",
        imageUrl = "",
        videoUrl = "",
        chef = UserSummary(
            id = "chef",
            name = "Chef John",
            profileImageUrl = "",
            location = "",
        ),
        category = RecipeCategory.INDIAN,
        reviewCount = 0,
        cookingTimeMinutes = 20,
        createAt = 0L,
        rating = 4.0,
    )

    private val recipes = listOf(recipe("1"), recipe("2"), recipe("3"))

    @Test
    fun `데이터 로드 전에는 빈 목록이다`() = runTest {
        val viewModel = SavedRecipesViewModel(FakeRecipeRepository(recipes))

        // init 의 코루틴이 아직 실행되기 전
        assertTrue(viewModel.uiState.value.recipes.isEmpty())
    }

    @Test
    fun `init 블록에서 레시피를 로드한다`() = runTest {
        val viewModel = SavedRecipesViewModel(FakeRecipeRepository(recipes))

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(recipes, uiState.recipes)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `레시피가 없으면 빈 목록이다`() = runTest {
        val viewModel = SavedRecipesViewModel(FakeRecipeRepository(emptyList()))

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.recipes.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `북마크를 해제하면 목록에서 제거된다`() = runTest {
        val viewModel = SavedRecipesViewModel(FakeRecipeRepository(recipes))
        advanceUntilIdle()

        viewModel.removeRecipe("2")

        assertEquals(listOf("1", "3"), viewModel.uiState.value.recipes.map { it.id })
    }
}
