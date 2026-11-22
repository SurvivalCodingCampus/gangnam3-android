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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SearchRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchRecipesState(recipes = emptyList()))
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")

    // 진행 중인 검색 요청
    private var searchJob: Job? = null

    init {
        loadRecentRecipes()
        observeQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            _query
                .drop(1) // 처음 값("")은 검색할 필요 없으니 건너뜀
                .debounce(0.5.seconds)
                .collect { searchRecipes() }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
        _query.value = query
    }

    fun onFilterChange(filterSearchState: FilterSearchState) {
        _state.update { it.copy(filterSearchState = filterSearchState) }
        searchRecipes()
    }

    // 최근 검색 목록 불러오기
    // TODO: 최근 검색 기능이 생기면 교체. 지금은 임시로 전체 목록
    private fun loadRecentRecipes() {
        searchJob?.cancel()
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        searchJob = viewModelScope.launch {
            updateRecipes(recipeRepository.getRecipes())
        }
    }

    // 현재 검색어 + 필터를 쿼리로 보내서 서버가 거르고 정렬한 결과를 받아옴
    private fun searchRecipes() {
        // 이전 요청이 늦게 도착해서 새 결과를 덮어쓰지 않게 취소
        searchJob?.cancel()

        val query = _query.value.trim().ifBlank { null } // 공백뿐이면 null → 검색어 없음
        val filter = _state.value.filterSearchState
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        searchJob = viewModelScope.launch {
            val result = recipeRepository.getRecipes(
                query = query,
                rate = filter.rate,
                category = if (filter.category == "All") null else filter.category,
                sort = if (filter.time == "All") null else filter.time.lowercase(),
            )
            updateRecipes(result)
        }
    }

    // 받아온 결과를 화면 상태에 반영 (최근 검색, 검색 둘 다 사용)
    private fun updateRecipes(result: Result<List<Recipe>, NetworkError>) {
        when (result) {
            is Result.Success -> _state.update {
                it.copy(isLoading = false, recipes = result.value)
            }

            is Result.Error -> _state.update {
                it.copy(isLoading = false, errorMessage = result.error.toMessage())
            }
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
