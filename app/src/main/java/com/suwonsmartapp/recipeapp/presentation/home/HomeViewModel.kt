package com.suwonsmartapp.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val recipes = recipeRepository.getRecipes()
            _state.update { it.copy(recipes = recipes) }
        }
    }

    fun onSelectCategory(category: RecipeCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onBookmarkClick(recipeId: String) {
        _state.update {
            val ids = it.bookmarkedIds
            it.copy(bookmarkedIds = if (recipeId in ids) ids - recipeId else ids + recipeId)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                HomeViewModel(application.recipeRepository)
            }
        }
    }
}
