package com.suwonsmartapp.recipeapp.presentation.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.data.repository.CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterViewModel(
    private val counterRepository: CounterRepository,
) : ViewModel() {

    // 상태 캡슐화 : 외부에는 읽기 전용 StateFlow 로 내보낸다
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    init {
        loadCount()
    }

    fun increment() {
        counterRepository.increment()
        loadCount()
    }

    fun decrement() {
        counterRepository.decrement()
        loadCount()
    }

    private fun loadCount() {
        viewModelScope.launch {
            _count.value = counterRepository.getCounter()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                CounterViewModel(application.counterRepository)
            }
        }
    }
}
