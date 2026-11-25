package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.FakeRecipeRepository
import com.example.gangnam3.data.repository.RecipeRepository
import com.example.gangnam3.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

/** 검색 ViewModel의 초기 로드·오류·debounce 필터링을 검증한다. */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchRecipesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeRecipeRepository(
        listOf(
            Recipe(1, "Traditional spare ribs baked", "Chef John", "20 min", 4.0, ""),
            Recipe(2, "Lamb chops with mint", "Spicy Nelly", "20 min", 3.0, "")
        )
    )

    @Test
    fun `생성 시 전체 레시피가 결과에 표시된다`() = runTest {
        val state = SearchRecipesViewModel(repository).state.value

        assertFalse(state.isLoading)
        assertEquals(listOf(1, 2), state.filteredRecipes.map { it.id })
    }

    @Test
    fun `로드 실패 시 로딩이 끝나고 오류 문구가 표시된다`() = runTest {
        val failing = object : RecipeRepository {
            override suspend fun getSavedRecipes(): List<Recipe> = error("네트워크 오류")
        }
        val state = SearchRecipesViewModel(failing).state.value

        assertFalse(state.isLoading)
        assertEquals("네트워크 오류", state.errorMessage)
    }

    @Test
    fun `검색어는 즉시 반영되고 결과는 300ms 뒤에 필터링된다`() = runTest {
        val viewModel = SearchRecipesViewModel(repository)

        viewModel.onQueryChange(" LAMB ")
        assertEquals(" LAMB ", viewModel.state.value.query)
        // 실제로 기다리지 않고 가상 시간을 진행한다. 300ms 직전에는 결과가 그대로여야 한다.
        advanceTimeBy(299)
        assertEquals(2, viewModel.state.value.filteredRecipes.size)

        // 300ms를 넘기면 앞뒤 공백과 대소문자를 무시하고 필터링된다.
        advanceTimeBy(2)
        assertEquals(listOf(2), viewModel.state.value.filteredRecipes.map { it.id })
    }

    /** 정렬과 조건을 구분할 수 있도록 별점, 카테고리, 순서를 서로 다르게 둔다. */
    private fun filterViewModel() = SearchRecipesViewModel(
        FakeRecipeRepository(
            listOf(
                Recipe(2, "Lamb Salad", "A", "20 min", 4.0, "", "Asian"),
                Recipe(1, "Greek Salad", "B", "20 min", 4.5, "", "Chinese"),
                Recipe(3, "Pork", "C", "20 min", 3.0, "", "Chinese")
            )
        )
    )

    private fun SearchRecipesViewModel.ids() = state.value.filteredRecipes.map { it.id }

    @Test
    fun `별점 필터는 선택한 점수 이상만 남긴다`() = runTest {
        val viewModel = filterViewModel()

        viewModel.onApplyFilter(FilterSearchState(selectedRate = 4))

        assertEquals(listOf(2, 1), viewModel.ids())
        assertEquals(4, viewModel.state.value.filter.selectedRate)
    }

    @Test
    fun `카테고리 필터는 대소문자를 무시하고 일치하는 것만 남긴다`() = runTest {
        val viewModel = filterViewModel()

        viewModel.onApplyFilter(FilterSearchState(selectedCategory = "chinese"))

        assertEquals(listOf(1, 3), viewModel.ids())
    }

    @Test
    fun `정렬 기준에 따라 순서가 바뀐다`() = runTest {
        val viewModel = filterViewModel()

        mapOf(
            "All" to listOf(2, 1, 3),
            "Newest" to listOf(3, 2, 1),
            "Oldest" to listOf(1, 2, 3),
            "Popularity" to listOf(1, 2, 3)
        ).forEach { (time, expected) ->
            viewModel.onApplyFilter(FilterSearchState(selectedTime = time))
            assertEquals(time, expected, viewModel.ids())
        }
    }

    @Test
    fun `검색어와 필터는 함께 적용되고 필터를 초기화하면 검색 결과로 돌아온다`() = runTest {
        val viewModel = filterViewModel()
        viewModel.onApplyFilter(FilterSearchState(selectedCategory = "Chinese"))

        viewModel.onQueryChange("salad")
        advanceTimeBy(301)
        assertEquals(listOf(1), viewModel.ids())

        viewModel.onApplyFilter(FilterSearchState())
        assertEquals(listOf(2, 1), viewModel.ids())
    }
}
