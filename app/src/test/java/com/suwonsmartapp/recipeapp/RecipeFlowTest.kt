import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // 1부터 5까지 100ms 간격으로 방출하는 Flow 생성
    flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }
        .filter { it % 2 == 0 } // 짝수 단계(2, 4)만 남기기
        .map { "Step $it 완료" } // 문자열 변환
        .collect { println(it) } // 결과 수집 및 출력
}