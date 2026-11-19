package com.suwonsmartapp.recipeapp.data.repository

interface CounterRepository {
    suspend fun getCounter(): Int
    fun increment()
    fun decrement()
}