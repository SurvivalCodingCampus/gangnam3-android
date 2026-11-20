package com.suwonsmartapp.recipeapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

// 실행할 연습문제 호출
fun main() {
    exam3()
}

fun exam1(): Unit = runBlocking {
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

fun exam2() = runBlocking {
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

private fun threadName(): String = Thread.currentThread().name

// 연습문제 2. 레시피 목록과 북마크 상태 합성
fun exam3(): Unit = runBlocking {
    val start = System.currentTimeMillis()
    fun elapsed() = "${System.currentTimeMillis() - start}ms"

    // Cold 스트림이라 collect 할 때마다 처음부터 다시 방출된다.
    val recipes = flowOf("Recipe 1", "Recipe 2", "Recipe 3")
        .onEach { delay(300) }
    val bookmarks = flowOf("북마크 OFF", "북마크 ON", "북마크 OFF", "북마크 ON")
        .onEach { delay(100) }

    println("== 연습문제 2 - zip ==")
    // 순서대로 짝을 짓고, 짧은 쪽(recipes)이 끝나면 종료 -> 3쌍
    recipes.zip(bookmarks) { recipe, bookmark -> "$recipe : $bookmark" }
        .collect { println("[${elapsed()}] $it") }

    // recipe 1 : bookmark off 300ms
    // recipe 2 : bookmark on 600ms
    // recipe 3 : bookmark off 900ms
    // 방출을 기다렸다가 출력

    println("== 연습문제 2 - combine ==")
    // 둘 다 한 번씩 방출한 뒤부터, 어느 한쪽이 바뀔 때마다 최신 값끼리 조합
    recipes.combine(bookmarks) { recipe, bookmark -> "$recipe : $bookmark" }
        .collect { println("[${elapsed()}] $it") }
}

// (recipe 1 : bookmark on 200ms)
// recipe 1 : bookmark on(최근 bookmark) 300ms
// (recipe 1 : bookmark off 400ms)
// (recipe 1 : bookmark on 500ms)
// recipe 2 : bookmark on 600ms
// (recipe 2 : bookmark off 700ms)
// (recipe 2 : bookmark on 800ms)
// recipe 3 : bookmark on 900ms


// 연습문제 3. 레시피 실시간 검색 디바운스 & 스레드 분리
@OptIn(FlowPreview::class)
fun exam4(): Unit = runBlocking {
    println("== 연습문제 3 - flowOn ==")
    flow {
        println("flow 빌더 : ${threadName()}")
        emit("레시피 목록 로딩 중...")
    }
        .flowOn(Dispatchers.IO)        // 위쪽(flow 빌더)은 IO
        .map {
            println("map       : ${threadName()}")
            it
        }
        .flowOn(Dispatchers.Default)   // 위쪽(map)은 Default
        .collect {                     // collect 는 호출한 쪽(main)
            println("collect   : ${threadName()}")
            println("값: $it")
        }

    println("== 연습문제 3 - debounce ==")
    flow {
        emit("토")
        delay(100)
        emit("토마")
        delay(100)
        emit("토마토")
    }
        .debounce(300)  // 300ms 안에 다음 입력이 오면 이전 값은 버려진다
        .collect { println("검색어: $it") }
}
