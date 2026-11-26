package com.suwonsmartapp.recipeapp.presentation.home

import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun `초기 선택 카테고리는 All이다`() {
        val viewModel = HomeViewModel()

        assertEquals(RecipeCategory.ALL, viewModel.state.value.selectedCategory)
    }

    @Test
    fun `onSelectCategory로 선택한 카테고리가 상태에 반영된다`() {
        val viewModel = HomeViewModel()

        viewModel.onSelectCategory(RecipeCategory.ITALIAN)

        assertEquals(RecipeCategory.ITALIAN, viewModel.state.value.selectedCategory)
    }
}
