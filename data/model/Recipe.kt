package com.example.gangnam3.data.model

/**
 * 화면에 표시할 레시피.
 * @property id 레시피를 구분하는 고유 번호(목록의 key로 사용)
 * @property title 요리 이름
 * @property chefName 요리사 이름
 * @property cookingTime 화면에 그대로 표시하는 조리 시간(예: "15 Mins")
 * @property rating 별점
 * @property imageUrl 사진 주소
 * @property category 홈 화면 필터에 쓰는 분류. 지정하지 않으면 "All"이다.
 * @property isBookmarked 북마크 여부
 */
data class Recipe(
    val id: Int,
    val title: String,
    val chefName: String,
    val cookingTime: String,
    val rating: Double,
    val imageUrl: String,
    val category: String = "All",
    val isBookmarked: Boolean = false
)