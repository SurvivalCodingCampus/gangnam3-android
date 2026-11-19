import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // 1. Dispatchers.IO에서 동작하며 로딩 메시지를 방출하는 Flow
    flow {
        emit("레시피 목록 로딩 중...")
        delay(200)
    }
        .flowOn(Dispatchers.IO) // 연산 스레드 변경[cite: 7]
        .collect { println(it) }

    // 2. 사용자가 "토", "토마", "토마토"를 연달아 입력하는 상황 가정 (Debounce 적용)
    flow {
        emit("토")
        delay(100)
        emit("토마")
        delay(100)
        emit("토마토")
        delay(400) // 300ms 이상 멈춤
    }
        .debounce(300) // 300ms 동안 추가 입력이 없으면 마지막 값("토마토")만 방출[cite: 7]
        .collect { searchTerm ->
            println("최종 검색어 API 요청: $searchTerm")
        }
}