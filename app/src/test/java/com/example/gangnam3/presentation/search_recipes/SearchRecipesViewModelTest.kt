package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.FakeRecipeRepository
import com.example.gangnam3.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

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
    fun `검색어는 즉시 반영되고 결과는 300ms 뒤에 필터링된다`() = runTest {
        val viewModel = SearchRecipesViewModel(repository)

        viewModel.onQueryChange(" LAMB ")
        assertEquals(" LAMB ", viewModel.state.value.query)
        advanceTimeBy(299)
        assertEquals(2, viewModel.state.value.filteredRecipes.size)

        advanceTimeBy(2)
        assertEquals(listOf(2), viewModel.state.value.filteredRecipes.map { it.id })
    }
}
