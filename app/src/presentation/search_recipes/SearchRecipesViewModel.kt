package com.example.gangnam3.presentation.search_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gangnam3.data.repository.RecipeRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchRecipesState())
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    private val _searchQueryFlow = MutableStateFlow("")

    init {
        loadAllRecipes()
        observeSearchQuery()
    }

    private fun loadAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val recipes = recipeRepository.getSavedRecipes()
                _state.update { current ->
                    current.copy(
                        allRecipes = recipes,
                        filteredRecipes = filterByQuery(recipes, current.query),
                        isLoading = false
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun observeSearchQuery() {
        _searchQueryFlow
            .debounce(300L) // 연속 입력 시 300ms 대기 후 필터링 실행
            .distinctUntilChanged()
            .onEach { debouncedQuery ->
                _state.update { current ->
                    current.copy(
                        filteredRecipes = filterByQuery(current.allRecipes, debouncedQuery)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    // 입력 필드에는 즉시 반영하고, 필터링은 debounce Flow로 전달
    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
        _searchQueryFlow.value = newQuery
    }

    private fun filterByQuery(
        recipes: List<Recipe>,
        query: String
    ): List<Recipe> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return recipes
        return recipes.filter { recipe ->
            recipe.title.contains(trimmed, ignoreCase = true)
        }
    }
}