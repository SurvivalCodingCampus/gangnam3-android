import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/** 실행해서 출력으로 비교하는 예제(JUnit 테스트 아님). zip은 짝을 맞추고 combine은 최신 값을 조합한다. */
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