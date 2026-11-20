package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SavedRecipesUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
)

class SavedRecipesViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    // 상태 캡슐화 : 외부에는 읽기 전용 StateFlow 로 내보낸다
    private val _uiState = MutableStateFlow(SavedRecipesUiState())
    val uiState: StateFlow<SavedRecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val recipes = recipeRepository.getRecipes()

            _uiState.update {
                it.copy(
                    recipes = recipes,
                    isLoading = false,
                )
            }
        }
    }

    // 북마크 해제 : 저장 목록에서 제거
    fun removeRecipe(recipeId: String) {
        _uiState.update { state ->
            state.copy(recipes = state.recipes.filterNot { it.id == recipeId })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                SavedRecipesViewModel(application.recipeRepository)
            }
        }
    }
}
