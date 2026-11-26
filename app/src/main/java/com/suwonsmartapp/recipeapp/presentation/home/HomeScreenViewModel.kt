package com.suwonsmartapp.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.BookmarkRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.network.toMessage
import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()
    private val _bookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
    val bookmarkedIds: StateFlow<Set<Long>> = _bookmarkedIds.asStateFlow()

    init {
        observeCategory()
        loadBookmarks()
    }

    // 북마크한 레시피를 불러와서 북마크 표시에 쓸 id만 저장
    private fun loadBookmarks() {
        viewModelScope.launch {
            val result = bookmarkRepository.getBookmarkedRecipes()
            if (result is Result.Success) {
                _bookmarkedIds.value = result.value.map { it.id }.toSet()
            }
        }
    }

    // 카테고리가 바뀌면 다시 불러옴
    private fun observeCategory() {
        viewModelScope.launch {
            _state
                .map { it.selectedCategory }
                .distinctUntilChanged()
                .collectLatest { category ->
                    _state.update { it.copy(result = HomeResult.Loading) }

                    val selectedCategory = if (category == FilterCategory.ALL) {
                        null
                    } else {
                        category.label
                    }

                    // ALL이면 카테고리 조건 없이 전체
                    val result = recipeRepository.getRecipes(category = selectedCategory)

                    val homeResult = when (result) {
                        is Result.Success -> HomeResult.Success(result.value)
                        is Result.Error -> HomeResult.Error(result.error.toMessage())
                    }

                    _state.update { it.copy(result = homeResult) }
                }
        }
    }

    fun onSelectCategory(category: FilterCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onBookmarkClick(recipe: Recipe) {
        _bookmarkedIds.update { ids ->
            if (recipe.id in ids) {
                ids - recipe.id
            } else {
                ids + recipe.id
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                HomeScreenViewModel(
                    recipeRepository = application.recipeRepository,
                    bookmarkRepository = application.bookmarkRepository,
                )
            }
        }
    }
}
