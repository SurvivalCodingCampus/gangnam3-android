package com.example.gangnam3.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.RecipeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SavedRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)

class SavedRecipesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavedRecipesUiState())
    val uiState: StateFlow<SavedRecipesUiState> = _uiState.asStateFlow()

    init {
        // ViewModel 생성 시 init 블록에서 데이터 로드
        loadSavedRecipes()
    }

    fun loadSavedRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val savedList = recipeRepository.getSavedRecipes()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipes = savedList
                    )
                }
            } catch (e: CancellationException) {
                throw e // 코루틴 취소는 오류가 아니므로 다시 던진다.
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "저장된 레시피를 불러오지 못했습니다."
                    )
                }
            }
        }
    }

    fun onToggleBookmark(recipeId: Int) {
        _uiState.update { state ->
            val updated = state.recipes.map { recipe ->
                if (recipe.id == recipeId) {
                    recipe.copy(isBookmarked = !recipe.isBookmarked)
                } else {
                    recipe
                }
            }
            state.copy(recipes = updated)
        }
    }
}