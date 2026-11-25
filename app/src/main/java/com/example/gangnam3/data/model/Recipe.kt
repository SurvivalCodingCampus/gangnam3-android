package com.example.gangnam3.data.model

// 불변 데이터 클래스로 레시피 정보 정의
data class Recipe(
    // 목록에서 항목을 구분하는 고유 번호
    val id: Int,
    val title: String,
    val chefName: String,
    // 화면에 그대로 표시하는 문자열(예: "20 min")
    val cookingTime: String,
    val rating: Double,
    val imageUrl: String,
    // 홈 화면 카테고리 필터에 쓰는 분류. 지정하지 않으면 "All"이다.
    val category: String = "All",
    // 북마크 여부. 저장 목록용 모델이므로 기본값이 true다.
    val isBookmarked: Boolean = true
)