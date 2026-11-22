package com.suwonsmartapp.recipeapp.presentation.search_recipes

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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SearchRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchRecipesState(recipes = emptyList()))
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    // 서버에서 받아온 전체 목록 (검색할 때마다 여기서 거름)
    private var _allRecipes: List<Recipe> = emptyList()


    private val _query = MutableStateFlow("")

    init {
        loadRecipes()
        observeQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            _query
                .debounce(0.5.seconds)
                .collect { query ->
                    _state.update { state ->
                        state.copy(recipes = filterRecipes(query, state.filterSearchState))
                    }
                }
        }
    }

    private fun loadRecipes() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            when (val result = recipeRepository.getRecipes()) {
                is Result.Success -> {
                    _allRecipes = result.value
                    _state.update { it.copy(isLoading = false, recipes = result.value) }
                }

                is Result.Error -> _state.update {
                    it.copy(isLoading = false, errorMessage = result.error.toMessage())
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
        _query.value = query
    }

    fun onFilterChange(filterSearchState: FilterSearchState) {
        _state.update { state ->
            state.copy(
                filterSearchState = filterSearchState,
                recipes = filterRecipes(_query.value, filterSearchState),
            )
        }
    }

    // 검색어 + 필터를 한 번에 적용
    private fun filterRecipes(query: String, filter: FilterSearchState): List<Recipe> {
        val trimmedQuery = query.trim()

        val filtered = _allRecipes.filter { recipe ->
            val matchesQuery = recipe.name.contains(trimmedQuery, ignoreCase = true)
            val matchesRate = filter.rate == null || recipe.rating >= filter.rate
            val matchesCategory = filter.category == "All" ||
                recipe.category.equals(filter.category, ignoreCase = true)

            matchesQuery && matchesRate && matchesCategory
        }

        return when (filter.time) {
            "Newest" -> filtered.sortedByDescending { it.id }
            "Oldest" -> filtered.sortedBy { it.id }
            "Popularity" -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository = (this[APPLICATION_KEY] as RecipeApplication)
                    .recipeRepository
                SearchRecipesScreenViewModel(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}
