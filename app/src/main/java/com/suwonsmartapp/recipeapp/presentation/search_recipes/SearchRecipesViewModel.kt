package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(SearchRecipesState())
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadRecipes()
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }

        searchJob?.cancel() // 이전 타이머 취소

        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_MILLIS.milliseconds)
            filterRecipes()
        }
    }

    fun onFilterApply(filter: FilterSearchState) {
        _state.update { it.copy(filterSearchState = filter) }

        searchJob?.cancel() // 이전 타이머 취소

        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_MILLIS.milliseconds)
            filterRecipes()
        }
    }

    private fun filterRecipes() {
        val filteredRecipes = _state.value.recipes
            .filter {
                it.title.lowercase()
                    .contains(_state.value.query.lowercase())
            }
            .filter {
                it.rating >= (_state.value.filterSearchState.rate ?: 1)
            }

        _state.update {
            it.copy(
                filteredRecipes = filteredRecipes
            )
        }
    }

    private suspend fun loadRecipes() {
        _state.update { it.copy(isLoading = true) }
        val recipes = recipeRepository.getRecipes()
        _state.update {
            it.copy(
                recipes = recipes,
                filteredRecipes = recipes,
                isLoading = false,
            )
        }
    }

    companion object {
        const val DEBOUNCE_MILLIS = 300L

        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                SearchRecipesViewModel(application.recipeRepository)
            }
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        searchJob = null
    }
}
