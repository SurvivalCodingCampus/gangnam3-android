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

/**
 * 저장 화면에 필요한 값을 하나로 묶은 UI State.
 * @property errorMessage 실패 문구. 정상이면 null
 */
data class SavedRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)

/** 저장 화면의 상태를 관리한다. 화면이 회전해도 ViewModel은 유지된다. */
class SavedRecipesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // 수정 가능한 상태는 내부에만 두고, 외부에는 읽기 전용으로 공개한다.
    private val _uiState = MutableStateFlow(SavedRecipesUiState())
    val uiState: StateFlow<SavedRecipesUiState> = _uiState.asStateFlow()

    init {
        // ViewModel 생성 시 init 블록에서 데이터 로드
        loadSavedRecipes()
    }

    /** 목록을 불러온다. 실패 후 다시 호출하면 재시도할 수 있다. */
    fun loadSavedRecipes() {
        // ViewModel이 사라지면 함께 취소되는 코루틴
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // 원본에는 저장하지 않은 샘플도 있으므로 북마크한 것만 보여준다.
                val savedList = recipeRepository.getSavedRecipes().filter { it.isBookmarked }
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

    /** 북마크 표시를 반전한다. 화면 상태만 바꾸며 저장소에는 기록하지 않는다. */
    fun onToggleBookmark(recipeId: Int) {
        _uiState.update { state ->
            val updated = state.recipes.map { recipe ->
                if (recipe.id == recipeId) {
                    // 불변 객체이므로 값을 바꾼 복사본을 만든다. 그래야 StateFlow가 변경을 감지한다.
                    recipe.copy(isBookmarked = !recipe.isBookmarked)
                } else {
                    recipe
                }
            }
            state.copy(recipes = updated)
        }
    }
}
