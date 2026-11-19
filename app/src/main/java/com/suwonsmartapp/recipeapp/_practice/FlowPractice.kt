package com.suwonsmartapp.recipeapp._practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() {
    flowPractice()
    practice1()
    
    /* output
    1. 기본 Flow
    emit 1
    Collect : 1
    emit 3
    Collect : 3
    emit 5
    Collect : 5
    Step 2
    Step 4
     */
}

fun flowPractice(): Unit = runBlocking {
    println("1. 기본 Flow")
    
    // Hot 스트림 (수도꼭지)
    val stateFlow = MutableStateFlow(0)
    
    // Cold 스트림 (collect하면 방출)
    val basicFlow: Flow<Int> = flow {
        for (i in 1..5) {
            delay(1.seconds)
            emit(i)
        }
    }.filter {
        it % 2 == 1
    }.onEach {
        println("emit $it")
    }
    
    basicFlow.collect { value ->
        println("Collect : $value")
    }
}

private fun practice1() = runBlocking {
    val recipeNumber: Flow<String> = flow {
        delay(100.milliseconds)
        emit(1)
        delay(100.milliseconds)
        emit(2)
        delay(100.milliseconds)
        emit(3)
        delay(100.milliseconds)
        emit(4)
        delay(100.milliseconds)
        emit(5)
    }.filter {
        it % 2 == 0
    }.map {
        "Step $it"
    }
    
    recipeNumber.collect { value ->
        println(value)
    }
}