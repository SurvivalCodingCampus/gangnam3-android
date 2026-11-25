package com.suwonsmartapp.recipeapp.core.network

enum class NetworkError {
    SERVER_ERR,      // 5xx
    CLIENT_ERR,      // 4xx
    NULL_DATA_ERR,   // body가 null
    PARSE_ERR,       // JSON 파싱 실패
    NO_INTERNET_ERR, // 인터넷 연결 없음
    TIMEOUT_ERR,     // 응답 시간 초과
    UNKNOWN_ERR,
}
