package com.example.gangnam3.presentation.saved_recipes

import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.RecipeRepository
import com.example.gangnam3.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedRecipesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeRecipeRepository : RecipeRepository {
        override suspend fun getSavedRecipes(): List<Recipe> {
            return listOf(
                Recipe(
                    id = 1,
                    title = "Traditional spare ribs baked",
                    chefName = "Chef John",
                    cookingTime = "20 min",
                    rating = 4.0,
                    imageUrl = "",
                    isBookmarked = true
                ),
                Recipe(
                    id = 2,
                    title = "spice roasted chicken with flavored rice",
                    chefName = "Mark Kelvin",
                    cookingTime = "20 min",
                    rating = 4.0,
                    imageUrl = "",
                    isBookmarked = true
                )
            )
        }
    }

    @Test
    fun `ViewModel 생성 시 init 블록에서 저장된 레시피 목록이 StateFlow에 로드된다`() = runTest {
        val fakeRepository = FakeRecipeRepository()
        val viewModel = SavedRecipesViewModel(recipeRepository = fakeRepository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(2, state.recipes.size)
        assertEquals("Traditional spare ribs baked", state.recipes[0].title)
    }

    @Test
    fun `북마크 토글 호출 시 해당 레시피의 북마크 상태가 반전된다`() = runTest {
        val fakeRepository = FakeRecipeRepository()
        val viewModel = SavedRecipesViewModel(recipeRepository = fakeRepository)

        viewModel.onToggleBookmark(recipeId = 1)

        val toggledRecipe = viewModel.uiState.value.recipes.first { it.id == 1 }
        assertFalse(toggledRecipe.isBookmarked)
    }
}