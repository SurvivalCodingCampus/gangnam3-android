package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.RecipeCategory as DataRecipeCategory
import com.suwonsmartapp.recipeapp.data.model.UserSummary
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.utils.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val indianRecipe = Recipe(
        id = "1",
        category = DataRecipeCategory.INDIAN,
        title = "Traditional spare ribs baked",
        imageUrl = "",
        videoUrl = "",
        chef = UserSummary(id = "chef", name = "Chef John", profileImageUrl = "", location = ""),
        cookingTimeMinutes = 20,
        reviewCount = 0,
        createAt = 0L,
        rating = 4.0,
    )
    private val asianRecipe = indianRecipe.copy(id = "2", category = DataRecipeCategory.ASIAN)
    private val recipes = listOf(indianRecipe, asianRecipe)

    private val fakeRepository = object : RecipeRepository {
        override suspend fun getRecipes(): List<Recipe> = recipes
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

    @Test
    fun `북마크를 클릭하면 활성과 비활성이 전환된다`() {
        val viewModel = HomeViewModel(fakeRepository)
        assertEquals(emptySet<String>(), viewModel.state.value.bookmarkedIds)

        viewModel.onBookmarkClick(indianRecipe.id)
        assertEquals(setOf(indianRecipe.id), viewModel.state.value.bookmarkedIds)

        viewModel.onBookmarkClick(indianRecipe.id)
        assertEquals(emptySet<String>(), viewModel.state.value.bookmarkedIds)
    }
}
