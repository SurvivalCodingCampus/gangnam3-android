package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedRecipesViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    // vmstateflow
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()
    
    init {
        loadSavedRecipes()
    }
    
    private fun loadSavedRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getRecipes()
        }
    }
    
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeRepository: RecipeRepository = (
                        this[APPLICATION_KEY] as RecipeApplication
                        ).recipeRepository
                
                SavedRecipesViewModel(
                    repository = recipeRepository
                )
            }
        }
    }
}