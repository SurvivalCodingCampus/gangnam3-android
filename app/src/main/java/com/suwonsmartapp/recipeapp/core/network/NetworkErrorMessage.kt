package com.suwonsmartapp.recipeapp.core.network

// 화면에 보여줄 에러 문구 (여러 화면에서 공용으로 사용)
fun NetworkError.toMessage(): String = when (this) {
    NetworkError.NO_INTERNET_ERR -> "인터넷 연결 확인"
    NetworkError.TIMEOUT_ERR -> "응답 시간 초과"
    NetworkError.SERVER_ERR -> "서버 문제"
    NetworkError.CLIENT_ERR -> "클라 에러"
    NetworkError.NULL_DATA_ERR -> "널 데이터 에러"
    NetworkError.PARSE_ERR -> "파싱 에러"
    NetworkError.UNKNOWN_ERR -> "언노운 에러"
}
