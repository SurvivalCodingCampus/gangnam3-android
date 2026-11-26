package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.core.NetworkError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.utils.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val indianRecipe = Recipe(
        id = 1,
        category = "Indian",
        name = "Traditional spare ribs baked",
        imageUrl = "",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0,
    )
    private val asianRecipe = indianRecipe.copy(id = 2, category = "Asian")
    private val recipes = listOf(indianRecipe, asianRecipe)

    private val fakeRepository = object : RecipeRepository {
        override suspend fun getRecipes(): Result<List<Recipe>, NetworkError> = Result.Success(recipes)

        override suspend fun getSavedRecipes(): Result<List<Recipe>, NetworkError> =
            Result.Success(emptyList())
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `초기에는 All이 선택되고 모든 레시피를 표시한다`() {
        val viewModel = HomeViewModel(fakeRepository)

        val state = viewModel.state.value
        assertEquals(RecipeCategory.ALL, state.selectedCategory)
        assertEquals(recipes, state.filteredRecipes)
    }

    @Test
    fun `카테고리를 선택하면 해당 카테고리 레시피만 표시한다`() {
        val viewModel = HomeViewModel(fakeRepository)

        viewModel.onSelectCategory(RecipeCategory.ASIAN)

        val state = viewModel.state.value
        assertEquals(RecipeCategory.ASIAN, state.selectedCategory)
        assertEquals(listOf(asianRecipe), state.filteredRecipes)
    }

    @Test
    fun `해당하는 레시피가 없는 카테고리는 빈 목록을 표시한다`() {
        val viewModel = HomeViewModel(fakeRepository)

        viewModel.onSelectCategory(RecipeCategory.ITALIAN)

        assertEquals(emptyList<Recipe>(), viewModel.state.value.filteredRecipes)
    }
}
