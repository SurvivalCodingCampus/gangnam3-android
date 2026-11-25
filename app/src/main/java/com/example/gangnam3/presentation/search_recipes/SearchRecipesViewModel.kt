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
import kotlinx.coroutines.CancellationException
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
 * 검색 화면의 입력 상태와 레시피 목록을 관리하는 화면 모델입니다.
 *
 * 화면은 [state]를 구독해 현재 검색어, 결과 목록, 로딩 상태를 표시하고 사용자 입력은
 * 이벤트 함수로 전달합니다. 검색어 입력은 즉시 UI 상태에 반영하지만, 실제 목록 필터링은
 * 짧은 입력 대기 시간을 둔 Flow에서 수행해 타이핑 중 불필요한 반복 계산을 줄입니다.
 *
 * 시트의 표시 여부와 고르는 중인 임시 선택값은 소유하지 않습니다.
 * 사용자가 적용을 확정하면 [onApplyFilter]로 전달받아 검색 결과에 반영합니다.
 *
 * @property recipeRepository 레시피 데이터를 가져오는 저장소입니다.
 */
@OptIn(FlowPreview::class)
class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // 화면이 구독하는 단일 상태 통입니다. 외부에는 읽기 전용 StateFlow만 공개합니다.
    private val _state = MutableStateFlow(SearchRecipesState())
    val state: StateFlow<SearchRecipesState> = _state.asStateFlow()

    // 입력값을 별도 Flow로 흘려 debounce를 적용합니다. UI의 즉시 표시 상태와 검색 계산 시점을 분리합니다.
    private val _searchQueryFlow = MutableStateFlow("")

    init {
        // 화면 모델이 만들어질 때 데이터 로드와 검색어 변경 관찰을 시작합니다.
        loadAllRecipes()
        observeSearchQuery()
    }

    /** 저장소에서 전체 레시피를 읽어 원본 목록과 현재 검색 결과를 함께 갱신합니다. */
    private fun loadAllRecipes() {
        viewModelScope.launch {
            // 요청이 진행 중임을 표시해 화면이 결과 대신 로딩 표시를 보여주게 합니다.
            // 다시 시도할 때 이전 오류 문구가 남지 않도록 함께 지운다.
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val recipes = recipeRepository.getSavedRecipes()
                // 로드 중 검색어가 바뀌었을 수 있으므로 완료 시점의 최신 상태를 기준으로 결과를 계산합니다.
                _state.update { current ->
                    current.copy(
                        allRecipes = recipes,
                        filteredRecipes = applyFilters(recipes, current.query, current.filter),
                        isLoading = false
                    )
                }
            } catch (e: CancellationException) {
                throw e // 코루틴 취소는 오류가 아니므로 다시 던진다.
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        // 예외에 설명이 없을 때는 기본 문구를 쓴다.
                        errorMessage = e.message ?: "레시피를 불러오지 못했습니다."
                    )
                }
            }
        }
    }

    /** 검색어 Flow를 관찰하고 입력이 잠시 멈춘 뒤 결과 목록을 다시 계산합니다. */
    private fun observeSearchQuery() {
        _searchQueryFlow
            // 연속 입력 중에는 계산을 미루고, 마지막 입력 뒤 300ms가 지나면 필터링합니다.
            .debounce(300L)
            // 같은 검색어가 다시 전달되면 중복으로 결과를 계산하지 않습니다.
            .distinctUntilChanged()
            .onEach { debouncedQuery ->
                // 최신 원본 목록을 사용해 결과만 바꾸므로 검색어 외 상태는 보존됩니다.
                _state.update { current ->
                    current.copy(
                        filteredRecipes = applyFilters(current.allRecipes, debouncedQuery, current.filter)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * 검색 입력 변경 이벤트입니다.
     *
     * 입력창에 보이는 문자열은 즉시 갱신하고, 동일한 문자열을 검색어 Flow에 전달해
     * debounce 이후 결과 목록이 갱신되도록 합니다.
     *
     * @param newQuery 사용자가 입력한 새 검색어입니다.
     */
    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
        _searchQueryFlow.value = newQuery
    }

    /**
     * 시트에서 확정한 필터를 적용합니다. 타이핑이 아니므로 기다리지 않고 바로 반영합니다.
     *
     * @param newFilter 사용자가 적용한 필터입니다.
     */
    fun onApplyFilter(newFilter: FilterSearchState) {
        _state.update { current ->
            current.copy(
                filter = newFilter,
                filteredRecipes = applyFilters(current.allRecipes, current.query, newFilter)
            )
        }
    }

    /** 검색어, 최소 별점, 카테고리를 모두 만족하는 레시피만 남긴 뒤 정렬합니다. */
    private fun applyFilters(
        recipes: List<Recipe>,
        query: String,
        filter: FilterSearchState
    ): List<Recipe> {
        // 앞뒤 공백은 무시합니다. 빈 검색어는 모든 제목에 포함된 것으로 처리됩니다.
        val trimmed = query.trim()
        val matched = recipes.filter { recipe ->
            recipe.title.contains(trimmed, ignoreCase = true) &&
                // 별점이 null이거나 카테고리가 All이면 해당 조건을 건너뜁니다.
                (filter.selectedRate == null || recipe.rating >= filter.selectedRate) &&
                (filter.selectedCategory == "All" ||
                    recipe.category.equals(filter.selectedCategory, ignoreCase = true))
        }
        // 등록일이 없으므로 id가 클수록 최신으로 봅니다.
        return when (filter.selectedTime) {
            "Newest" -> matched.sortedByDescending { it.id }
            "Oldest" -> matched.sortedBy { it.id }
            "Popularity" -> matched.sortedByDescending { it.rating }
            else -> matched // All: 원본 순서 유지
        }
    }

    companion object {
        /** 생성자에 저장소가 필요하므로 기본 viewModel() 대신 만드는 방법을 알려줍니다. */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { SearchRecipesViewModel(RecipeRepositoryImpl(MockRecipeDataSource())) }
        }
    }
}
