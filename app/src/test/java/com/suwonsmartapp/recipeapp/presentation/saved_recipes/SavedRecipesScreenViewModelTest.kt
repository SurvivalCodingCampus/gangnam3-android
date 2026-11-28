@file:Suppress("NonAsciiCharacters")

package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.core.data.datasource.FakeBookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepositoryImpl
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedRecipesScreenViewModelTest {
    private val repository = BookmarkRepositoryImpl(FakeBookmarkDataSource())

    @BeforeEach
    fun setUp() {
        // Repository의 delay(1초)를 advanceUntilIdle()로 넘기려고 StandardTestDispatcher 사용
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ViewModel을 만들고 로딩이 끝날 때까지 시간을 넘김
    private fun TestScope.loadedViewModel(
        repository: BookmarkRepositoryImpl = this@SavedRecipesScreenViewModelTest.repository,
    ): SavedRecipesScreenViewModel {
        val viewModel = SavedRecipesScreenViewModel(repository)
        advanceUntilIdle()
        return viewModel
    }

    private fun SavedRecipesScreenViewModel.success() =
        state.value as SavedRecipesState.Success

    @Test
    fun `생성 직후에는 Loading 상태다`() = runTest {
        val viewModel = SavedRecipesScreenViewModel(repository)

        assertThat(viewModel.state.value).isEqualTo(SavedRecipesState.Loading)
    }

    @Test
    fun `생성되면 저장된 레시피 3개를 불러온다`() = runTest {
        val viewModel = loadedViewModel()

        assertThat(viewModel.success().recipes).hasSize(3)
    }

    @Test
    fun `불러온 레시피가 Repository 데이터와 같다`() = runTest {
        val viewModel = loadedViewModel()

        val expected = (repository.getBookmarkedRecipes() as Result.Success).value
        assertThat(viewModel.success().recipes).isEqualTo(expected)
    }

    @Test
    fun `첫 번째 레시피 이름이 맞다`() = runTest {
        val viewModel = loadedViewModel()

        assertThat(viewModel.success().recipes.first().name)
            .isEqualTo("Traditional spare ribs baked")
    }

    @Test
    fun `북마크를 누르면 목록은 그대로고 북마크만 해제된다`() = runTest {
        val viewModel = loadedViewModel()
        val target = viewModel.success().recipes.first()

        viewModel.onBookmarkClick(target)

        assertThat(viewModel.success().recipes).hasSize(3)
        assertThat(viewModel.unbookmarkedIds.value).containsExactly(target.id)
    }

    @Test
    fun `북마크를 두 번 누르면 다시 활성화된다`() = runTest {
        val viewModel = loadedViewModel()
        val target = viewModel.success().recipes.first()

        viewModel.onBookmarkClick(target)
        viewModel.onBookmarkClick(target)

        assertThat(viewModel.unbookmarkedIds.value).isEmpty()
    }

    @Test
    fun `Repository가 실패하면 Error 상태가 된다`() = runTest {
        val errorRepository = BookmarkRepositoryImpl(
            FakeBookmarkDataSource(statusCode = StatusCode.InternalServerError)
        )
        val viewModel = loadedViewModel(errorRepository)

        assertThat(viewModel.state.value).isEqualTo(SavedRecipesState.Error("서버 문제"))
    }
}
