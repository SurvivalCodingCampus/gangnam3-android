package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.network.toMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<SavedRecipesState>(SavedRecipesState.Loading)
    val state: StateFlow<SavedRecipesState> = _state.asStateFlow()
    private val _unbookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
    val unbookmarkedIds: StateFlow<Set<Long>> = _unbookmarkedIds.asStateFlow()

    init {
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        _state.value = SavedRecipesState.Loading
        viewModelScope.launch {
            _state.value = when (val result = recipeRepository.getRecipes()) {
                is Result.Success -> SavedRecipesState.Success(recipes = result.value)
                is Result.Error -> SavedRecipesState.Error(result.error.toMessage())
            }
        }
    }

    fun onBookmarkClick(recipe: Recipe) {
        // update()로 thread-safe하게 토글
        _unbookmarkedIds.update { ids ->
            if (recipe.id in ids) ids - recipe.id else ids + recipe.id
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
