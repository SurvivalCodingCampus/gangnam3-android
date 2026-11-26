package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchRecipesState())
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadRecipes()
            combine(
                _state.map { it.query.trim() }.distinctUntilChanged().debounce(DEBOUNCE_MILLIS.milliseconds),
                _state.map { it.filter }.distinctUntilChanged(),
            ) { query, filter -> query to filter }
                .collect { (query, filter) ->
                    _state.update { state ->
                        state.copy(
                            filteredRecipes = state.recipes.filter {
                                it.name.contains(query, ignoreCase = true) &&
                                    (filter.rate == null || it.rating.toInt() == filter.rate)
                            },
                        )
                    }
                }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    fun onFilterClick(filter: FilterSearchState) {
        _state.update { it.copy(filter = filter) }
    }

    private suspend fun loadRecipes() {
        _state.update { it.copy(isLoading = true) }
        val recipes = when (val result = recipeRepository.getRecipes()) {
            is Result.Success -> result.data
            is Result.Error -> emptyList()
        }
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
}
