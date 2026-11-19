package com.suwonsmartapp.recipeapp.data.repository

interface CounterRepository {
    fun getCounter(): Int
    fun increment()
    fun decrement()
}