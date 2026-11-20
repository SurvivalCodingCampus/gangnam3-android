package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedRecipesScreenViewModelTest {
    private val repository = RecipeRepositoryImpl(FakeRecipeDataSource())

    @Before
    fun setUp() {
        // viewModelScope는 Dispatchers.Main을 쓰기 때문에 테스트용으로 교체
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun SavedRecipesScreenViewModel.success() =
        state.value as SavedRecipesState.Success

    @Test
    fun `생성되면 저장된 레시피 10개를 불러온다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)

        assertEquals(10, viewModel.success().recipes.size)
    }

    @Test
    fun `불러온 레시피가 Repository 데이터와 같다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)

        val expected = (repository.getRecipes() as Result.Success).value
        assertEquals(expected, viewModel.success().recipes)
    }

    @Test
    fun `첫 번째 레시피 이름이 맞다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)

        assertEquals("Traditional spare ribs baked", viewModel.success().recipes.first().name)
    }

    @Test
    fun `북마크를 누르면 목록은 그대로고 북마크만 해제된다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)
        val target = viewModel.success().recipes.first()

        viewModel.onBookmarkClick(target)

        assertEquals(10, viewModel.success().recipes.size)
        assertEquals(setOf(target.id), viewModel.unbookmarkedIds.value)
    }

    @Test
    fun `북마크를 두 번 누르면 다시 활성화된다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)
        val target = viewModel.success().recipes.first()

        viewModel.onBookmarkClick(target)
        viewModel.onBookmarkClick(target)

        assertEquals(emptySet<Long>(), viewModel.unbookmarkedIds.value)
    }

    @Test
    fun `Repository가 실패하면 Error 상태가 된다`() = runTest {
        val errorRepository = RecipeRepositoryImpl(FakeRecipeDataSource(statusCode = StatusCode.InternalServerError))
        val viewModel = SavedRecipesScreenViewModel(errorRepository)

        assertEquals(SavedRecipesState.Error("서버 문제"), viewModel.state.value)
    }
}
