package com.suwonsmartapp.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
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
            when (val result = recipeRepository.getRecipes()) {
                is Result.Success -> _state.update { it.copy(recipes = result.data) }
                is Result.Error -> Unit
            }
        }
    }

    fun onSelectCategory(category: RecipeCategory) {
        _state.update { it.copy(selectedCategory = category) }
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
