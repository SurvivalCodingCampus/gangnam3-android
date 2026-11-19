package com.suwonsmartapp.recipeapp._practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class FlowPracticeTest {
    @Test
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
        /*
        1. 기본 Flow
        emit 1
        Collect : 1
        emit 3
        Collect : 3
        emit 5
        Collect : 5
         */
    }
    
    @Test
    fun practice1() = runBlocking {
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
        /*
        Step 2
        Step 4
         */
    }
    
    @Test
    fun flowBuilderPractice() = runBlocking {
        flowOf(1, 2, 3).collect { println("flowOf: $it") }
        (1..3).asFlow().collect { println("asFlow: $it") }
        /*
        flowOf: 1
        flowOf: 2
        flowOf: 3
        asFlow: 1
        asFlow: 2
        asFlow: 3
         */
    }
    
    @Test
    fun flowZipPractice() = runBlocking {
        val flow1 = flowOf("A", "B", "C")
        val flow2 = flowOf(1, 2, 3)
        
        flow1.zip(flow2) { a, b ->
            "$a$b"
        }.collect {
            println("Zipped: $it")
        }
        /*
        Zipped: A1
        Zipped: B2
        Zipped: C3
         */
    }
    
    @Test
    fun flowCombinePractice() = runBlocking {
        // combine은 둘 중 하나만 바뀌어도 바뀐다
        val flow1 = flowOf("A", "B", "C")
            .onEach {
                delay(100.milliseconds)  // 0.1초 대기
            }
        val flow2 = flowOf(1, 2, 3)
            .onEach {
                delay(200.milliseconds)
            }
        
        flow1.combine(flow2) { a, b ->
            "$a - $b"
        }.collect {
            println("Combined: $it")
        }
        /*
        Combined: A - 1
        Combined: B - 1
        Combined: C - 1
        Combined: C - 2
        Combined: C - 3
         */
    }
    
    @Test
    fun flowErrorPractice() = runBlocking {
        flow {
            emit(1)
            throw RuntimeException("에러 발생!!")
        }.catch { e ->
            println("Error catch: ${e.message}")
            emit(-1)
        }.onCompletion { cause ->  // 모든 방출과 수집이 끝나고 Flow가 종료될 때 호출됨
            println("Completed ${cause?.let { ": ${it.message}" } ?: ""}")
        }.collect {
            println("값 : $it")
        }
        /*
        값 : 1
        Error catch: 에러 발생!!
        값 : -1
        Completed
         */
    }
    
    @Test
    fun stateFlowPractice() = runBlocking {
        val stateFlow = MutableStateFlow(0)
        val job = launch {
            stateFlow.collect {
                println("StateFlow: $it")
            }
        }
        
        delay(100.milliseconds)
        stateFlow.value = 1
        delay(100.milliseconds)
        stateFlow.value = 2
        delay(100.milliseconds)
        job.cancel()
        
        println("Final : ${stateFlow.value}")
        /*
        StateFlow: 0
        StateFlow: 1
        StateFlow: 2
        Final : 2
         */
    }
    
}