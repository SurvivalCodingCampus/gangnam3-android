@file:Suppress("NonAsciiCharacters")

package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.core.data.datasource.FakeBookmarkDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeUserDataSource
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepositoryImpl
import com.suwonsmartapp.recipeapp.core.data.repository.UserRepositoryImpl
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory
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
class HomeScreenViewModelTest {
    private val bookmarkRepository = BookmarkRepositoryImpl(FakeBookmarkDataSource())
    private val userRepository = UserRepositoryImpl(FakeUserDataSource())

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
    // this@HomeScreenViewModelTest.bookmarkRepository HomeScreenViewModelTest의 변수 명시
    private fun TestScope.loadedViewModel(
        bookmarkRepository: BookmarkRepositoryImpl = this@HomeScreenViewModelTest.bookmarkRepository,
        userRepository: UserRepositoryImpl = this@HomeScreenViewModelTest.userRepository,
    ): HomeScreenViewModel {
        val viewModel = HomeScreenViewModel(bookmarkRepository, userRepository)
        // 가상 시간을 필요한 만큼 앞으로 진행시키며 예약된 모든 코루틴 작업이 남지 않을 때까지 실행
        advanceUntilIdle()
        return viewModel
    }

    private fun HomeScreenViewModel.success() =
        state.value.result as HomeResult.Success

    @Test
    fun `생성 직후에는 Loading 상태이고 카테고리는 ALL이다`() = runTest {
        val viewModel = HomeScreenViewModel(bookmarkRepository, userRepository)

        assertThat(viewModel.state.value.result).isEqualTo(HomeResult.Loading)
        assertThat(viewModel.state.value.selectedCategory).isEqualTo(FilterCategory.ALL)
        assertThat(viewModel.state.value.user).isNull()
    }

    @Test
    fun `생성되면 유저 정보를 불러온다`() = runTest {
        val viewModel = loadedViewModel()

        assertThat(viewModel.state.value.user?.name).isEqualTo("Jega")
    }

    @Test
    fun `ALL이면 북마크한 레시피 전체 3개를 불러온다`() = runTest {
        val viewModel = loadedViewModel()

        assertThat(viewModel.success().recipes.map { it.id }).containsExactly(1L, 3L, 5L)
    }

    @Test
    fun `카테고리를 선택하면 상태에 반영된다`() = runTest {
        val viewModel = loadedViewModel()

        viewModel.onSelectCategory(FilterCategory.CHINESE)

        assertThat(viewModel.state.value.selectedCategory).isEqualTo(FilterCategory.CHINESE)
    }

    @Test
    fun `카테고리를 바꾸면 다시 불러오는 동안 Loading 상태다`() = runTest {
        val viewModel = loadedViewModel()

        viewModel.onSelectCategory(FilterCategory.CHINESE)
        // delay(1초)가 끝나기 전까지만 진행
        testScheduler.runCurrent()

        assertThat(viewModel.state.value.result).isEqualTo(HomeResult.Loading)
    }

    @Test
    fun `Chinese를 선택하면 Chinese 레시피만 불러온다`() = runTest {
        val viewModel = loadedViewModel()

        viewModel.onSelectCategory(FilterCategory.CHINESE)
        advanceUntilIdle()

        val recipes = viewModel.success().recipes
        assertThat(recipes).hasSize(1)
        assertThat(recipes.first().name).isEqualTo("Spicy fried rice mix chicken bali")
    }

    @Test
    fun `북마크한 레시피가 없는 카테고리면 빈 목록이다`() = runTest {
        val viewModel = loadedViewModel()

        viewModel.onSelectCategory(FilterCategory.DINNER)
        advanceUntilIdle()

        assertThat(viewModel.success().recipes).isEmpty()
    }

    @Test
    fun `다시 ALL을 선택하면 전체 레시피를 불러온다`() = runTest {
        val viewModel = loadedViewModel()

        viewModel.onSelectCategory(FilterCategory.CHINESE)
        advanceUntilIdle()
        viewModel.onSelectCategory(FilterCategory.ALL)
        advanceUntilIdle()

        assertThat(viewModel.success().recipes).hasSize(3)
    }

    @Test
    fun `로딩 중에 카테고리를 바꾸면 마지막 카테고리 결과만 남는다`() = runTest {
        val viewModel = HomeScreenViewModel(bookmarkRepository, userRepository)

        // 첫 로딩(ALL)이 끝나기 전에 바로 바꿈 → collectLatest가 이전 요청을 취소
        viewModel.onSelectCategory(FilterCategory.CHINESE)
        advanceUntilIdle()

        assertThat(viewModel.success().recipes.map { it.id }).containsExactly(3L)
    }

    @Test
    fun `북마크를 누르면 해제 목록에 추가된다`() = runTest {
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
    fun `카테고리를 바꿔도 해제한 북마크는 유지된다`() = runTest {
        val viewModel = loadedViewModel()
        val target = viewModel.success().recipes.first()

        viewModel.onBookmarkClick(target)
        viewModel.onSelectCategory(FilterCategory.CHINESE)
        advanceUntilIdle()

        assertThat(viewModel.unbookmarkedIds.value).containsExactly(target.id)
    }

    @Test
    fun `북마크 Repository가 실패하면 Error 상태가 된다`() = runTest {
        val errorRepository = BookmarkRepositoryImpl(
            FakeBookmarkDataSource(statusCode = StatusCode.InternalServerError)
        )
        val viewModel = loadedViewModel(bookmarkRepository = errorRepository)

        assertThat(viewModel.state.value.result).isEqualTo(HomeResult.Error("서버 문제"))
    }

    @Test
    fun `유저 Repository가 실패하면 user는 null이고 레시피는 정상으로 불러온다`() = runTest {
        val errorRepository = UserRepositoryImpl(
            FakeUserDataSource(statusCode = StatusCode.NotFound)
        )
        val viewModel = loadedViewModel(userRepository = errorRepository)

        assertThat(viewModel.state.value.user).isNull()
        assertThat(viewModel.success().recipes).hasSize(3)
    }
}
