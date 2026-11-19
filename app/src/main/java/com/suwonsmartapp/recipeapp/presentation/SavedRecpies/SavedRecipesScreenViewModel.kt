package com.suwonsmartapp.recipeapp.presentation.SavedRecpies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.repository.FakeRecipeRepository
import com.suwonsmartapp.recipeapp.core.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedRecipesScreenViewModel(
    private val recipeRepository: RecipeRepository = FakeRecipeRepository(),
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
}
