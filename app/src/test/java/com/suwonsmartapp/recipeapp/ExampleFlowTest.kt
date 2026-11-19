package com.suwonsmartapp.recipeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ExampleFlowTest {

    // 연습문제 1. 레시피 조리 단게 필터링
    fun recipeStepFlow() = flow {
        for (i in 1..5) {
            delay(100.milliseconds)
            emit(i)
        }
    }

    @Test
    fun collectRecipeStepFlow() = runBlocking {
        recipeStepFlow().filter { it % 2 == 0 }.map { "Step $it 완료" }.collect { println(it) }
    }

    // 연습문제 2. 레시피 목록과 북마크 상태 합성
    fun recipeNumFlow() = flow {
        for (i in 1..3) {
            delay(300.milliseconds)
            emit("Recipe $i")
        }
    }

    fun bookmarkStatusFlow() = flow {
        for (i in 1..4) {
            delay(100.milliseconds)
            emit(if (i % 2 == 0) "북마크 ON" else "북마크 OFF")
        }
    }

    @Test
    fun collectRecipeBookmarkFlowZip() = runBlocking {
        recipeNumFlow().zip(bookmarkStatusFlow()) { recipe, bookmark ->
            "$recipe - $bookmark"
        }.collect { println("Zipped: $it") }
    }

    @Test
    fun collectRecipeBookmarkFlowCombine() = runBlocking {
        recipeNumFlow().combine(bookmarkStatusFlow()) { recipe, bookmark ->
            "$recipe - $bookmark"
        }.collect { println("Combined: $it") }
    }

    // 연습문제 3. 레시피 실시간 검색 디바운스 및 스레드 분리
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    @Test
    fun collectRecipeFlowDebounce() = runBlocking {
        flow {
            println("Flow: ${Thread.currentThread().name}")
            emit("레시피 목록 로딩 중...")
        }.flowOn(Dispatchers.IO)
            .flatMapMerge { message ->
                println(message)
                flow {
                    emit("토")
                    delay(100.milliseconds)
                    emit("토마")
                    delay(150.milliseconds)
                    emit("토마토")
                    delay(350.milliseconds)
                }.debounce(300.milliseconds)
            }
            .flowOn(Dispatchers.Default)
            .collect {
                println("Collect: ${Thread.currentThread().name}")
                println("Debounced: $it")
            }
    }

    @Test
    fun collectExperienceFlow() = runBlocking {
        experienceFlow().collect { println("level: $it") }
    }

    @Test
    fun collectHpFlow() = runBlocking {
        hpFlow().collect { println("hp: $it") }
    }

    @Test
    fun collectBasicFlow() = runBlocking {
        basicFlow().collect { value ->
            println("Collected: $value")
        }
    }

    @Test
    fun collectDownFlow() = runBlocking {
        downFlow().collect { value ->
            println("Collected: $value")
        }
    }

    @Test
    fun collectFlowOf() = runBlocking {
        flowOf(1, 2, 3).collect { println("flowOf: $it") }
    }

    @Test
    fun collectAsFlow() = runBlocking {
        (1..3).asFlow().collect { println("asFlow: $it") }
    }

    @Test
    fun collectFlowOperator() = runBlocking {
        flow {
            emit(1)
            emit(2)
            emit(3)
        }.map { it * 2 }.filter { it > 2 }.collect { println("Transformed: $it") }
    }

    @Test
    fun collectFlowZip() = runBlocking {
        val flow1 = flowOf("A", "B", "C")
        val flow2 = flowOf(1, 2, 3)

        flow1.zip(flow2) { a, b -> "$a$b" }.collect { println("Zipped: $it") }
    }

    @Test
    fun collectFlowCombine() = runBlocking {
        val flow1 = flowOf("A", "B", "C").onEach { delay(100.milliseconds) }
        val flow2 = flowOf(1, 2, 3).onEach { delay(200.milliseconds) }

        flow1.combine(flow2) { a, b -> "$a$b" }.collect { value -> println(value) }
    }

    @Test
    fun collectFlowError() = runBlocking {
        flow {
            emit(1)
            throw RuntimeException("에러 발생")
        }.catch { e ->
            println("에러: ${e.message}")
        }.onCompletion { cause ->
            println("완료: ${cause?.let { "${it.message}" } ?: ""}")
        }.collect { value ->
            println("값: $value")
        }
    }

    @Test
    fun collectStateFlow() = runBlocking {
        val stateFlow = MutableStateFlow(0)
        val job = launch {
            stateFlow.collect { println("StateFlow: $it") }
        }

        delay(100.milliseconds)
        stateFlow.value = 1
        delay(100.milliseconds)
        stateFlow.value = 2
        delay(100.milliseconds)
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun collectFlowParallel() = runBlocking {
        (1..5).asFlow().flatMapMerge { num ->
            flow {
                delay(100.milliseconds)
                emit("처리된 $num")
            }
        }.collect { println(it) }
    }

    @Test
    fun collectFlowContextSwitching() = runBlocking {
        flow {
            println("Flow: ${Thread.currentThread().name}")
            emit(1)
        }.flowOn(Dispatchers.IO).collect {
            println("Collect: ${Thread.currentThread().name}")
            println("값 $it")
        }
    }

    @OptIn(FlowPreview::class)
    @Test
    fun collectFlowDebounce() = runBlocking {
        flow {
            emit("A")
            delay(100.milliseconds)
            emit("B")
            delay(90.milliseconds)
            emit("C")
            delay(110.milliseconds)
            emit("D")
        }.debounce(100.milliseconds).collect { println("Debounced: $it") }
    }

    fun experienceFlow() = flow {
        var level = 1
        while (level <= 5) {
            delay(1.seconds)
            emit(level++)
        }
    }

    fun hpFlow() = flow {
        var hp = 100
        while (hp > 0) {
            delay(500.milliseconds)
            hp -= 10
            emit(hp)
        }
    }

    fun basicFlow() = flow {
        for (i in 1..3) {
            delay(100.milliseconds)
            println("Emitting $i")
            emit(i)
        }
    }

    fun downFlow() = flow {
        for (i in 10 downTo 1) {
            emit(i)
            delay(1000.milliseconds)
        }
    }
}
