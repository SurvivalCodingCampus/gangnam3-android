package com.suwonsmartapp.recipeapp.core.data.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class FlowExamTest {
    @Test
    fun run() = runBlocking {
        exam2()
    }
}

suspend fun exam1() {
    (1..5).asFlow()
        .onEach { delay(100.milliseconds) }
        .filter { it % 2 == 0 }
        .map { "Step $it 완료" }
        .collect { println(it) }
}

suspend fun exam2() {
    val recipeFlow = flowOf("Recipe 1", "Recipe 2", "Recipe 3")
        .onEach { delay(300.milliseconds) }
    val bookmarkFlow = flowOf("북마크 OFF", "북마크 ON", "북마크 OFF", "북마크 ON")
        .onEach { delay(100.milliseconds) }

    // zip
    println("=======zip=======")
    recipeFlow.zip(bookmarkFlow) { recipe, bookmark ->
        Pair(recipe, bookmark)
    }.collect { (recipe, bookmark) ->
        println("레시피: $recipe, 북마크: $bookmark")
    }

    // combine
    println("=======combine=======")
    recipeFlow.combine(bookmarkFlow) { recipe, bookmark ->
        Pair(recipe, bookmark)
    }.collect { (recipe, bookmark) ->
        println("레시피: $recipe, 북마크: $bookmark")
    }
}