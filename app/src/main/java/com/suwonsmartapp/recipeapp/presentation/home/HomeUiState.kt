package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.model.User
import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory

// 홈 화면 전체 상태
data class HomeUiState(
    // 불러오기 전이거나 실패하면 null
    val user: User? = null,
    val selectedCategory: FilterCategory = FilterCategory.ALL,
    // 화면에 들어오자마자 불러오므로 처음엔 Loading
    val result: HomeResult = HomeResult.Loading,
)

// 레시피 목록 결과 (한 번에 하나의 상태만 가능)
sealed interface HomeResult {
    data object Loading : HomeResult

    data class Success(val recipes: List<Recipe>) : HomeResult

    data class Error(val message: String) : HomeResult
}
