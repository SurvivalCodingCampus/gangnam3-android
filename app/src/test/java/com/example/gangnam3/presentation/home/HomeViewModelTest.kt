package com.example.gangnam3.presentation.home

import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.FakeRecipeRepository
import com.example.gangnam3.data.repository.RecipeRepository
import com.example.gangnam3.utils.MainDispatcherRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/** 홈 ViewModel의 로드·카테고리 필터·북마크를 검증한다. */
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val recipes = listOf(
        Recipe(1, "Salad", "A", "15 Mins", 4.5, "", "Italian", isBookmarked = false),
        Recipe(2, "Tikka", "B", "25 Mins", 4.8, "", "Indian", isBookmarked = false)
    )

    private fun HomeViewModel.ids() = state.value.filteredRecipes.map { it.id }

    @Test
    fun `처음에는 All이 선택되어 전체가 표시된다`() = runTest {
        val viewModel = HomeViewModel(FakeRecipeRepository(recipes))

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(listOf(1, 2), viewModel.ids())
    }

    @Test
    fun `카테고리를 고르면 걸러지고 All을 고르면 전체로 돌아온다`() = runTest {
        val viewModel = HomeViewModel(FakeRecipeRepository(recipes))

        viewModel.onSelectCategory("Indian")
        assertEquals(listOf(2), viewModel.ids())

        viewModel.onSelectCategory("Chinese")
        assertTrue(viewModel.ids().isEmpty())

        viewModel.onSelectCategory("All")
        assertEquals(listOf(1, 2), viewModel.ids())
    }

    @Test
    fun `북마크를 바꿔도 선택한 카테고리가 유지된다`() = runTest {
        val viewModel = HomeViewModel(FakeRecipeRepository(recipes))
        viewModel.onSelectCategory("Indian")

        viewModel.onToggleBookmark(2)

        assertEquals(listOf(2), viewModel.ids())
        assertTrue(viewModel.state.value.filteredRecipes.single().isBookmarked)
        // 원본 목록도 함께 바뀌고 다른 레시피는 그대로여야 한다.
        assertEquals(listOf(false, true), viewModel.state.value.allRecipes.map { it.isBookmarked })
    }

    @Test
    fun `로딩 중 고른 카테고리가 완료 후 결과에 적용된다`() = runTest {
        // 완료 시점을 직접 정해 로딩 중인 상태를 만든다.
        val response = CompletableDeferred<List<Recipe>>()
        val viewModel = HomeViewModel(object : RecipeRepository {
            override suspend fun getSavedRecipes() = response.await()
        })
        assertTrue(viewModel.state.value.isLoading)

        viewModel.onSelectCategory("Italian")
        response.complete(recipes)

        assertEquals(listOf(1), viewModel.ids())
        assertNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun `카테고리는 데이터에서 만들고 사라진 선택은 전체로 되돌린다`() = runTest {
        // 값을 바꾸면 DB 변경처럼 다시 전달되는 저장소
        val source = MutableStateFlow(recipes)
        val viewModel = HomeViewModel(object : RecipeRepository {
            override suspend fun getSavedRecipes() = source.value
            override fun observeRecipes() = source
        })
        assertEquals(listOf("All", "Italian", "Indian"), viewModel.state.value.categories)
        viewModel.onSelectCategory("Indian")

        source.value = recipes.take(1)

        assertEquals("All", viewModel.state.value.selectedCategory)
        assertEquals(listOf(1), viewModel.ids())
    }

    @Test
    fun `로드가 실패하면 로딩이 끝나고 오류 문구가 표시된다`() = runTest {
        val viewModel = HomeViewModel(object : RecipeRepository {
            override suspend fun getSavedRecipes(): List<Recipe> = error("네트워크 오류")
        })

        assertFalse(viewModel.state.value.isLoading)
        assertEquals("네트워크 오류", viewModel.state.value.errorMessage)
    }
}
