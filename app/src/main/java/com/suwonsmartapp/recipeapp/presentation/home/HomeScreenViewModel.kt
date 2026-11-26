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
import com.suwonsmartapp.recipeapp.core.data.repository.UserRepository
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
    private val bookmarkRepository: BookmarkRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()
    // 불러온 레시피는 전부 북마크된 상태 → 해제한 것만 저장
    private val _unbookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
    val unbookmarkedIds: StateFlow<Set<Long>> = _unbookmarkedIds.asStateFlow()

    init {
        observeCategory()
        loadUser()
    }

    // 헤더에 보여줄 유저 이름, 프로필 사진
    private fun loadUser() {
        viewModelScope.launch {
            val result = userRepository.getUser()
            if (result is Result.Success) {
                _state.update { it.copy(user = result.value) }
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

                    // ALL이면 카테고리 조건 없이 북마크 전체
                    val result = bookmarkRepository.getBookmarkedRecipes(category = selectedCategory)

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
        _unbookmarkedIds.update { ids ->
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
                    bookmarkRepository = application.bookmarkRepository,
                    userRepository = application.userRepository,
                )
            }
        }
    }
}
