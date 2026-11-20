package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import com.suwonsmartapp.recipeapp.core.data.model.Recipe

// 초기 로딩 → 성공/실패가 명확히 갈리는 화면이라 sealed interface로 상호 배타적인 상태를 표현
sealed interface SavedRecipesState {
    data object Loading : SavedRecipesState

    data class Error(val errorMessage: String) : SavedRecipesState

    data class Success(val recipes: List<Recipe>) : SavedRecipesState
}
