@file:Suppress("NonAsciiCharacters")

package com.suwonsmartapp.recipeapp.presentation.search_recipes

import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepositoryImpl
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRecipesScreenViewModelTest {
    private val repository = RecipeRepositoryImpl(FakeRecipeDataSource())

    @BeforeEach
    fun setUp() {
        // debounce(0.5초), Repository delay(1초)를 직접 시간 조절하려고 StandardTestDispatcher 사용
        // runTest도 같은 시계를 써서 advanceUntilIdle()로 시간을 넘길 수 있음
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `생성 직후에는 로딩 중이다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)

        assertThat(viewModel.state.value.result).isEqualTo(SearchResult.Loading)
    }

    @Test
    fun `조건이 없으면 전체 레시피 10개를 불러온다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes()).hasSize(10)
    }

    @Test
    fun `검색어를 바꾸면 state의 query가 바로 바뀐다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)

        viewModel.onQueryChange("chicken")

        assertThat(viewModel.state.value.query).isEqualTo("chicken")
    }

    @Test
    fun `검색어로 이름이 포함된 레시피만 불러온다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("chicken")
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes().map { it.name })
            .containsExactly(
                "Spice roasted chicken with flavored rice",
                "Spicy fried rice mix chicken bali",
            )
    }

    @Test
    fun `검색어 앞뒤 공백은 무시한다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("  chicken  ")
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes()).hasSize(2)
    }

    @Test
    fun `검색어 입력 후 0_5초 전에는 검색하지 않는다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("chicken")
        advanceTimeBy(400.milliseconds)

        // 아직 debounce 대기 중이라 이전 결과 그대로
        assertThat(viewModel.state.value.recipes()).hasSize(10)
    }

    @Test
    fun `빠르게 여러 번 입력하면 마지막 검색어로만 검색한다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("s")
        advanceTimeBy(300.milliseconds)
        viewModel.onQueryChange("sushi")
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes().map { it.name })
            .containsExactly("Sushi Platter")
    }

    @Test
    fun `카테고리 필터를 고르면 해당 카테고리만 불러온다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onFilterChange(FilterSearchState(category = "French"))
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes())
            .hasSize(2)
            .allMatch { it.category == "French" }
    }

    @Test
    fun `별점 필터 5를 고르면 5점 레시피만 불러온다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onFilterChange(FilterSearchState(rate = 5))
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes())
            .hasSize(2)
            .allMatch { it.rating == 5.0 }
    }

    @Test
    fun `Newest를 고르면 최신순(id 내림차순)으로 정렬된다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onFilterChange(FilterSearchState(time = "Newest"))
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes().map { it.id })
            .containsExactlyElementsOf(10L downTo 1L)
    }

    @Test
    fun `검색어와 필터를 같이 쓰면 둘 다 만족하는 레시피만 불러온다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("rice")
        viewModel.onFilterChange(FilterSearchState(category = "Chinese"))
        advanceUntilIdle()

        assertThat(viewModel.state.value.recipes().map { it.name })
            .containsExactly("Spicy fried rice mix chicken bali")
    }

    @Test
    fun `필터를 바꿔도 검색어는 유지된다`() = runTest {
        val viewModel = SearchRecipesScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("chicken")
        viewModel.onFilterChange(FilterSearchState(category = "French"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.query).isEqualTo("chicken")
        assertThat(state.filter).isEqualTo(FilterSearchState(category = "French"))
    }

    @Test
    fun `Repository가 실패하면 에러 메시지가 들어간다`() = runTest {
        val errorRepository = RecipeRepositoryImpl(
            FakeRecipeDataSource(statusCode = StatusCode.InternalServerError)
        )
        val viewModel = SearchRecipesScreenViewModel(errorRepository)
        advanceUntilIdle()

        assertThat(viewModel.state.value.result).isEqualTo(SearchResult.Error("서버 문제"))
    }

    // Success일 때 레시피 목록을 꺼냄 (Success가 아니면 테스트 실패)
    private fun SearchUiState.recipes(): List<Recipe> {
        assertThat(result).isInstanceOf(SearchResult.Success::class.java)
        return (result as SearchResult.Success).recipes
    }
}
