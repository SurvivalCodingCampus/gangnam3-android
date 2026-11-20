package com.suwonsmartapp.recipeapp.presentation._practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterViewModel(
    private val repository: CounterRepository,  // interface로 타입 지정해주기
) : ViewModel() {
    private var _count = MutableStateFlow(0)
    val count = _count.asStateFlow()
    
    init {
        viewModelScope.launch {
            _count.value = repository.getCounter()
        }
    }
    
    fun increment() {
        repository.increment()
        
        viewModelScope.launch {
            _count.value = repository.getCounter()
        }
    }
    
    fun decrement() {
        repository.decrement()
        
        viewModelScope.launch {
            _count.value = repository.getCounter()
        }
    }
    
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val counterRepository = (
                        this[APPLICATION_KEY] as RecipeApplication
                        ).counterRepository
                
                CounterViewModel(
                    repository = counterRepository,
                )
            }
        }
    }
}