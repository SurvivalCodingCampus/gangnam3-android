package com.suwonsmartapp.recipeapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    println("1. 기본 Flow")

    // Hot 스트림 (수도꼭지)
    val stateFlow = MutableStateFlow(0)

    // Cold 스트림 (collect 하면 방출)
    val basicFlow: Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000.milliseconds)
            emit(i)
        }
    }.filter { it % 2 == 1 }
        .onEach {
            println("방출 $it")
        }

    basicFlow.collect { value ->
        println("수집 : $value")
    }
}

fun exam1() = runBlocking {
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
    }.filter { it % 2 == 0 }
        .map { "Step $it 완료" }

    recipeNumber.collect { value ->
        println(value)
    }
}