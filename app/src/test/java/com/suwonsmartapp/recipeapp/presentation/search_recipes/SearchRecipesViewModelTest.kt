package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.core.NetworkError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRecipesViewModelTest {

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

    private val ratedRecipes = listOf(
        fakeRecipes[0].copy(id = 1, rating = 4.0),
        fakeRecipes[0].copy(id = 2, rating = 4.9),
        fakeRecipes[1].copy(id = 3, rating = 5.0),
        fakeRecipes[1].copy(id = 4, rating = 3.5),
    )

    private fun fakeRepository(
        loadMillis: Long = 0L,
        recipes: List<Recipe> = fakeRecipes,
    ) = object : RecipeRepository {
        override suspend fun getRecipes(): Result<List<Recipe>, NetworkError> {
            delay(loadMillis)
            return Result.Success(recipes)
        }

        override suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError> =
            Result.Success(emptyList())
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `화면 진입 시 모든 레시피를 표시한다`() {
        val viewModel = SearchRecipesViewModel(fakeRepository())

        val state = viewModel.state.value
        assertEquals(fakeRecipes, state.recipes)
        assertEquals(fakeRecipes, state.filteredRecipes)
        assertFalse(state.isLoading)
    }

    @Test
    fun `레시피를 불러오는 동안 로딩 상태이다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository(loadMillis = 1_000L))
        assertTrue(viewModel.state.value.isLoading)

        advanceTimeBy(1_001L)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `검색어는 debounce 이후 제목 기준으로 대소문자 구분 없이 필터링된다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository())

        viewModel.onQueryChange("RIBS")
        assertEquals("RIBS", viewModel.state.value.query)

        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS - 1)
        assertEquals(fakeRecipes, viewModel.state.value.filteredRecipes)

        advanceTimeBy(2L)
        assertEquals(listOf(fakeRecipes[0]), viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `debounce 시간 안에 연속 입력하면 마지막 검색어로만 필터링된다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository())

        viewModel.onQueryChange("ribs")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS - 100)
        viewModel.onQueryChange("chicken")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS - 100)
        assertEquals(fakeRecipes, viewModel.state.value.filteredRecipes)

        advanceTimeBy(101L)
        assertEquals(listOf(fakeRecipes[1]), viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `검색어를 지우면 모든 레시피를 다시 표시한다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository())

        viewModel.onQueryChange("ribs")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)
        viewModel.onQueryChange("")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)

        assertEquals(fakeRecipes, viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `Rate 필터는 해당 점수대 레시피만 debounce 없이 즉시 표시한다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository(recipes = ratedRecipes))
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)

        viewModel.onFilterApply(FilterSearchState(rate = 4))

        assertEquals(listOf(ratedRecipes[0], ratedRecipes[1]), viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `Rate 5는 5점 레시피만 표시한다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository(recipes = ratedRecipes))
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)

        viewModel.onFilterApply(FilterSearchState(rate = 5))

        assertEquals(listOf(ratedRecipes[2]), viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `Rate 필터와 검색어를 함께 적용한다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository(recipes = ratedRecipes))

        viewModel.onFilterApply(FilterSearchState(rate = 4))
        viewModel.onQueryChange("chicken")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)
        assertEquals(emptyList<Recipe>(), viewModel.state.value.filteredRecipes)

        viewModel.onQueryChange("ribs")
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)
        assertEquals(listOf(ratedRecipes[0], ratedRecipes[1]), viewModel.state.value.filteredRecipes)
    }

    @Test
    fun `Rate 선택을 해제하면 모든 레시피를 다시 표시한다`() = runTest {
        val viewModel = SearchRecipesViewModel(fakeRepository(recipes = ratedRecipes))
        advanceTimeBy(SearchRecipesViewModel.DEBOUNCE_MILLIS + 1)

        viewModel.onFilterApply(FilterSearchState(rate = 4))
        viewModel.onFilterApply(FilterSearchState())

        assertEquals(ratedRecipes, viewModel.state.value.filteredRecipes)
    }
}
