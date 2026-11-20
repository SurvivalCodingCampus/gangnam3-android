package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    // 가변 상태는 _state 하나뿐
    private val _state = MutableStateFlow<SavedRecipesState>(SavedRecipesState.Loading)
    val state: StateFlow<SavedRecipesState> = _state.asStateFlow()

    init {
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        _state.value = SavedRecipesState.Loading
        viewModelScope.launch {
            _state.value = when (val result = recipeRepository.getRecipes()) {
                is Result.Success -> SavedRecipesState.Success(recipes = result.value)
                is Result.Error -> SavedRecipesState.Error(result.error)
            }
        }
    }

    fun onBookmarkClick(recipe: Recipe) {
        // update()로 thread-safe하게 copy
        _state.update { state ->
            when (state) {
                SavedRecipesState.Loading -> state
                is SavedRecipesState.Error -> error("Error 상태에서는 북마크를 토글할 수 없음: ${state.error}")
                is SavedRecipesState.Success -> {
                    val ids = state.unbookmarkedIds
                    state.copy(
                        unbookmarkedIds = if (recipe.id in ids) {
                            ids - recipe.id
                        } else {
                            ids + recipe.id
                        },
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository = (this[APPLICATION_KEY] as RecipeApplication)
                    .recipeRepository
                SavedRecipesScreenViewModel(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}
