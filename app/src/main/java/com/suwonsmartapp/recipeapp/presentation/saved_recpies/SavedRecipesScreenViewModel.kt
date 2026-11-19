package com.suwonsmartapp.recipeapp.presentation.saved_recpies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes = _savedRecipes.asStateFlow()

    init {
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipes().collect { recipes ->
                _savedRecipes.value = recipes
            }
        }
    }

    // 북마크 해제된 레시피 id 목록
    private val _unbookmarkedIds = MutableStateFlow<Set<Long>>(emptySet())
    val unbookmarkedIds = _unbookmarkedIds.asStateFlow()

    fun onBookmarkClick(recipe: Recipe) {
        val ids = _unbookmarkedIds.value
        _unbookmarkedIds.value = if (recipe.id in ids) {
            ids - recipe.id // 새로운 인스턴스 생성
        } else {
            ids + recipe.id // 새로운 인스턴스 생성
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository = (this[APPLICATION_KEY] as RecipeApplication)
                    .recipeRepository
                SavedRecipesScreenViewModel(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}
