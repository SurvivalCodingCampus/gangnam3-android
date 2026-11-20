package com.suwonsmartapp.recipeapp.core.data.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class FlowExamTest {
    @Test
    fun run() = runTest {
        exam3()
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

    // 2-3
    // zip
    println("=======zip=======")
    recipeFlow.zip(bookmarkFlow) { recipe, bookmark ->
        Pair(recipe, bookmark)
    }.collect { (recipe, bookmark) ->
        println("레시피: $recipe, 북마크: $bookmark")
    }

    // 2-4
    // combine
    println("=======combine=======")
    recipeFlow.combine(bookmarkFlow) { recipe, bookmark ->
        Pair(recipe, bookmark)
    }.collect { (recipe, bookmark) ->
        println("레시피: $recipe, 북마크: $bookmark")
    }
}

fun exam3() = runBlocking {
    // 3-2
    flow {
        println("Flow: ${Thread.currentThread().name}")
        emit("레시피 목록 로딩 중...")
    }.flowOn(Dispatchers.Default)
        .collect {
            println("Collect: ${Thread.currentThread().name}")
            println(it)
        }

    // 3-4
    val searchFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val job = launch {
        searchFlow
            .debounce(300.milliseconds)
            .collect { println("searchFlow: $it") }
    }

    delay(100.milliseconds)
    searchFlow.value = "토"
    delay(100.milliseconds)
    searchFlow.value = "토마"
    delay(100.milliseconds)
    searchFlow.value = "토마토"
    delay(400.milliseconds)
    job.cancel()
}