package com.suwonsmartapp.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.network.toMessage
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
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        observeCategory()
    }

    // 카테고리가 바뀌면 다시 불러옴
    private fun observeCategory() {
        viewModelScope.launch {
            _state
                .map { it.selectedCategory }
                .distinctUntilChanged()
                .collectLatest { category -> // 새 값이 오면 이전 요청은 자동 취소
                    _state.update { it.copy(result = HomeResult.Loading) }

                    val result = recipeRepository.getRecipes(
                        category = if (category == HomeCategory.ALL) null else category.label,
                    )
                    val homeResult = when (result) {
                        is Result.Success -> HomeResult.Success(result.value)
                        is Result.Error -> HomeResult.Error(result.error.toMessage())
                    }

                    _state.update { it.copy(result = homeResult) }
                }
        }
    }

    fun onCategoryClick(category: HomeCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onBookmarkClick(recipeId: Long) {
        _state.update { state ->
            val ids = state.bookmarkedIds
            state.copy(bookmarkedIds = if (recipeId in ids) ids - recipeId else ids + recipeId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository = (this[APPLICATION_KEY] as RecipeApplication)
                    .recipeRepository
                HomeScreenViewModel(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}
