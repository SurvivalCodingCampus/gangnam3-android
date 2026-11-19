package com.suwonsmartapp.recipeapp.core.data.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking {
    exam1()
}

suspend fun exam1() {
    (1..5).asFlow()
        .onEach {
            delay(100.milliseconds)
        }
        .filter { it % 2 == 0 }
        .map { "$ Step $it 완료" }
        .collect {
            println(it)
        }
}

suspend fun exam2() {

}