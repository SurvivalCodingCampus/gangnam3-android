package com.example.gangnam3.presentation.search_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gangnam3.data.datasource.MockRecipeDataSource
import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.data.repository.RecipeRepository
import com.example.gangnam3.data.repository.RecipeRepositoryImpl
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

/**
 * 검색 화면의 상태를 관리하고 검색어와 필터를 적용한다.
 *
 * debounce가 미리보기 API라서 FlowPreview 사용에 동의(@OptIn)한다.
 */
@OptIn(FlowPreview::class)
class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // 화면이 구독하는 상태. 외부에는 읽기 전용으로 공개한다.
    private val _state = MutableStateFlow(SearchRecipesState())
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    // 입력칸에는 즉시 반영하고, 검색만 늦추기 위해 검색어 흐름을 따로 둔다.
    private val _searchQueryFlow = MutableStateFlow("")

    init {
        loadAllRecipes()
        observeSearchQuery()
    }

    /** 전체 목록을 불러와 현재 검색어와 필터를 적용한다. */
    private fun loadAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val recipes = recipeRepository.getSavedRecipes()
                // 로딩 중 바뀐 검색어와 필터도 반영되도록 최신 상태를 쓴다.
                _state.update { current ->
                    current.copy(
                        allRecipes = recipes,
                        filteredRecipes = applyFilters(
                            recipes = recipes,
                            query = current.query,
                            filter = current.filter
                        ),
                        isLoading = false
                    )
                }
            // 주의: 코루틴 취소(CancellationException)까지 잡고, 실패 원인도 화면에 알리지 않는다.
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    /** 검색어 변화를 지켜보다가 입력이 멈추면 결과를 갱신한다. */
    private fun observeSearchQuery() {
        _searchQueryFlow
            // 입력이 300ms 동안 멈췄을 때만 다음 단계로 보낸다.
            .debounce(300L)
            // 직전과 같은 검색어면 다시 계산하지 않는다.
            .distinctUntilChanged()
            .onEach { debouncedQuery ->
                _state.update { current ->
                    current.copy(
                        filteredRecipes = applyFilters(
                            recipes = current.allRecipes,
                            query = debouncedQuery,
                            filter = current.filter
                        )
                    )
                }
            }
            // ViewModel이 살아 있는 동안 흐름을 수집한다.
            .launchIn(viewModelScope)
    }

    /** 입력칸은 즉시 갱신하고, 실제 검색은 debounce 흐름에 맡긴다. */
    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
        _searchQueryFlow.value = newQuery
    }

    /** 필터는 확정된 선택이므로 기다리지 않고 바로 결과에 적용한다. */
    fun onApplyFilter(newFilter: FilterSearchState) {
        _state.update { current ->
            current.copy(
                filter = newFilter,
                filteredRecipes = applyFilters(
                    recipes = current.allRecipes,
                    query = current.query,
                    filter = newFilter
                )
            )
        }
    }

    /**
     * 검색어 → 최소 별점 → 정렬 순으로 적용한다.
     *
     * 주의: filter.selectedCategory는 아직 사용하지 않아 카테고리를 골라도 결과가 바뀌지 않는다.
     */
    private fun applyFilters(
        recipes: List<Recipe>,
        query: String,
        filter: FilterSearchState
    ): List<Recipe> {
        val trimmedQuery = query.trim()

        // 1. 검색어가 비어 있으면 전체, 아니면 제목에 포함된 것만 남긴다.
        val searched = if (trimmedQuery.isEmpty()) {
            recipes
        } else {
            recipes.filter { it.title.contains(trimmedQuery, ignoreCase = true) }
        }

        // 2. 별점이 선택됐으면 그 이상만 남기고, null이면 그대로 둔다.
        val rated = filter.selectedRate?.let { minRate ->
            searched.filter { it.rating >= minRate }
        } ?: searched

        // 3. 등록일이 없어 id가 클수록 최신으로 보고 정렬한다(All·Newest는 else).
        return when (filter.selectedTime) {
            "Oldest" -> rated.sortedBy { it.id }
            "Popularity" -> rated.sortedByDescending { it.rating }
            else -> rated.sortedByDescending { it.id }
        }
    }

    companion object {
        /** 생성자에 저장소가 필요하므로 기본 viewModel() 대신 만드는 방법을 알려준다. */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { SearchRecipesViewModel(RecipeRepositoryImpl(MockRecipeDataSource())) }
        }
    }
}
