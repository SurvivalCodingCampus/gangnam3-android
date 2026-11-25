package com.example.gangnam3.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gangnam3.data.datasource.MockRecipeDataSource
import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.RecipeRepository
import com.example.gangnam3.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 홈 화면의 상태와 동작을 관리한다.
 * @param recipeRepository 레시피를 가져올 저장소. 테스트에서는 가짜 구현으로 바꿀 수 있다.
 */
class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // 수정 가능한 상태는 내부에만 두고, 외부에는 읽기 전용 StateFlow로 공개한다.
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // ViewModel이 만들어지면 바로 목록을 불러온다.
    init {
        loadRecipes()
    }

    /** 목록을 불러와 현재 선택된 카테고리로 걸러 저장한다. */
    private fun loadRecipes() {
        // ViewModel이 사라지면 자동으로 취소되는 코루틴
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // 저장소 함수 이름은 getSavedRecipes지만 여기서는 전체 목록의 원본으로 쓴다.
                val recipes = recipeRepository.getSavedRecipes()
                // update는 최신 상태(current)를 기준으로 바꾸므로, 로딩 중 선택한 카테고리도 반영된다.
                _state.update { current ->
                    current.copy(
                        allRecipes = recipes,
                        filteredRecipes = filterByCategory(recipes, current.selectedCategory),
                        isLoading = false
                    )
                }
            } catch (e: CancellationException) {
                throw e // 코루틴 취소는 오류가 아니므로 다시 던진다.
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "레시피를 불러오지 못했습니다."
                    )
                }
            }
        }
    }

    /** 선택한 카테고리를 저장하고 원본 목록에서 다시 걸러낸다. */
    fun onSelectCategory(category: String) {
        _state.update { current ->
            current.copy(
                selectedCategory = category,
                filteredRecipes = filterByCategory(current.allRecipes, category)
            )
        }
    }

    /** 해당 레시피의 북마크를 반전한다. 화면 상태만 바꾸며 저장소에는 기록하지 않는다. */
    fun onToggleBookmark(recipeId: Int) {
        _state.update { current ->
            val updatedAll = current.allRecipes.map { recipe ->
                // 불변 객체이므로 값을 바꾼 복사본으로 교체한다.
                if (recipe.id == recipeId) recipe.copy(isBookmarked = !recipe.isBookmarked) else recipe
            }
            current.copy(
                allRecipes = updatedAll,
                // 원본이 바뀌었으니 표시 목록도 다시 계산해 둘을 일치시킨다.
                filteredRecipes = filterByCategory(updatedAll, current.selectedCategory)
            )
        }
    }

    /** "All"이면 전체를, 아니면 대소문자 구분 없이 카테고리가 같은 레시피만 돌려준다. */
    private fun filterByCategory(
        recipes: List<Recipe>,
        category: String
    ): List<Recipe> {
        if (category == "All") return recipes
        return recipes.filter { it.category.equals(category, ignoreCase = true) }
    }

    companion object {
        /** 생성자에 저장소가 필요하므로 기본 viewModel() 대신 만드는 방법을 알려준다. */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { HomeViewModel(RecipeRepositoryImpl(MockRecipeDataSource())) }
        }
    }
}
