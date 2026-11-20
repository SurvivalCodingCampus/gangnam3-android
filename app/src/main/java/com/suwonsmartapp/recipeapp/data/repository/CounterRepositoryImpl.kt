package com.suwonsmartapp.recipeapp.data.repository

class CounterRepositoryImpl: CounterRepository {
    private var _count = 0
    
    override fun getCounter(): Int = _count
    
    override fun increment() {
        _count++
    }
    
    override fun decrement() {
        _count--
    }
}