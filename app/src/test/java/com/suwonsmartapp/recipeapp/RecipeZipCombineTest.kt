import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val recipeFlow = flowOf("Recipe 1", "Recipe 2", "Recipe 3").onEach { delay(300) }
    val bookmarkFlow = flowOf("북마크 OFF", "북마크 ON", "북마크 OFF", "북마크 ON").onEach { delay(100) }

    println("--- Zip 결과 (짝을 맞춤) ---")
    recipeFlow.zip(bookmarkFlow) { recipe, bookmark ->
        "$recipe - $bookmark"
    }.collect { println(it) }

    val recipeFlow2 = flowOf("Recipe 1", "Recipe 2", "Recipe 3").onEach { delay(100) }
    val bookmarkFlow2 = flowOf("북마크 OFF", "북마크 ON", "북마크 OFF", "북마크 ON").onEach { delay(300) }

    println("\n--- Combine 결과 (가장 최근 값과 실시간 조합) ---")
    recipeFlow2.combine(bookmarkFlow2) { recipe, bookmark ->
        "$recipe - $bookmark"
    }.collect { println(it) }
}