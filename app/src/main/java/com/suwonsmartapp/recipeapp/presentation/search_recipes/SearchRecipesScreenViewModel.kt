package com.suwonsmartapp.recipeapp.presentation.search_recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.network.toMessage
import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory
import com.suwonsmartapp.recipeapp.presentation.model.FilterTime
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    init {
        observeSearch()
    }

    // 검색어나 필터가 바뀌면 다시 불러옴
    @OptIn(FlowPreview::class) // debounce가 아직 실험적 API라 필요
    private fun observeSearch() {
        viewModelScope.launch {
            combine(
                // 실제로 바뀔 때만 통과 -> 빈 검색어는 바로, 입력 중일 때만 0.5초 대기
                _state
                    .map { it.query }
                    .distinctUntilChanged()
                    .debounce { if (it.isBlank()) 0L else 500L },
                // 필터는 _state에서 꺼내 씀 (실제로 바뀔 때만 통과)
                _state
                    .map { it.filter }
                    .distinctUntilChanged(),
            ) { query, filter ->
                query.trim() to filter
            }.collectLatest { (query, filter) -> // 새 값이 오면 이전 요청은 자동 취소
                _state.update { it.copy(result = SearchResult.Loading) }

                val result = if (query.isEmpty() && filter.isEmpty) {
                    // 조건이 없으면 최근 검색
                    // TODO: 최근 검색 기능이 생기면 교체. 지금은 임시로 전체 목록
                    recipeRepository.getRecipes()
                } else {
                    recipeRepository.getRecipes(
                        query = query.ifEmpty { null },
                        rate = filter.rate?.value,
                        category = if (filter.category == FilterCategory.ALL) null else filter.category.label,
                        sort = if (filter.time == FilterTime.ALL) null else filter.time.label.lowercase(),
                    )
                }
                updateRecipes(result)
            }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    fun onFilterChange(filter: FilterSearchState) {
        _state.update { it.copy(filter = filter) }
    }

    private fun updateRecipes(result: Result<List<Recipe>, NetworkError>) {
        val searchResult = when (result) {
            is Result.Success -> SearchResult.Success(result.value)
            is Result.Error -> SearchResult.Error(result.error.toMessage())
        }

        _state.update { it.copy(result = searchResult) }
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
