package com.suwonsmartapp.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.network.toMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()
    private val _bookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
    val bookmarkedIds: StateFlow<Set<Long>> = _bookmarkedIds.asStateFlow()

    init {
        observeCategory()
    }

    // 카테고리가 바뀌면 다시 불러옴
    private fun observeCategory() {
        viewModelScope.launch {
            _state
                .map { it.selectedCategory }
                .distinctUntilChanged()
                .collectLatest {
                    _state.update { it.copy(result = HomeResult.Loading) }

                    val result = recipeRepository.getRecipes()

                    val homeResult = when (result) {
                        is Result.Success -> HomeResult.Success(result.value)
                        is Result.Error -> HomeResult.Error(result.error.toMessage())
                    }

                    _state.update { it.copy(result = homeResult) }
                }
        }
    }

    fun onSelectCategory(category: HomeCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onBookmarkClick(recipe: Recipe) {
        _bookmarkedIds.update { ids ->
            if (recipe.id in ids) {
                ids - recipe.id
            } else {
                ids + recipe.id
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository = (this[APPLICATION_KEY] as RecipeApplication)
                    .recipeRepository
                HomeScreenViewModel(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}
