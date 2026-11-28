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

/** 저장 화면 ViewModel의 로드·북마크·오류 회복을 검증한다. */
@OptIn(ExperimentalCoroutinesApi::class)
class SavedRecipesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    /** 서버 대신 항상 같은 두 건을 돌려주는 가짜 저장소 */
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

    @Test
    fun `로드에 실패하면 오류가 표시되고 다시 로드하면 회복된다`() = runTest {
        // 첫 호출만 실패하는 저장소로 재시도 흐름을 재현한다.
        val flaky = object : RecipeRepository {
            var calls = 0
            override suspend fun getSavedRecipes(): List<Recipe> =
                if (calls++ == 0) error("연결 실패") else emptyList()
        }
        val viewModel = SavedRecipesViewModel(recipeRepository = flaky)
        assertEquals("연결 실패", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)

        viewModel.loadSavedRecipes()

        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `북마크하지 않은 레시피는 저장 목록에서 제외된다`() = runTest {
        val repository = object : RecipeRepository {
            override suspend fun getSavedRecipes() = listOf(
                Recipe(1, "저장함", "A", "20 min", 4.0, "", isBookmarked = true),
                Recipe(2, "저장 안 함", "B", "20 min", 4.0, "", isBookmarked = false)
            )
        }

        val state = SavedRecipesViewModel(repository).uiState.value

        assertEquals(listOf(1), state.recipes.map { it.id })
    }
}
