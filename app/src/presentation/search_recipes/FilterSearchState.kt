package com.example.gangnam3.presentation.search_recipes

// 필터 바텀시트 내부 상태 홀더
data class FilterSearchState(
    val selectedTime: String = "Newest",
    val selectedRate: Int? = 4,
    val selectedCategory: String = "Local Dish"
) {
    companion object {
        val timeOptions = listOf("All", "Newest", "Oldest", "Popularity")
        val rateOptions = listOf(5, 4, 3, 2, 1)
        val categoryOptions = listOf(
            "All",
            "Cereal",
            "Vegetables",
            "Dinner",
            "Chinese",
            "Local Dish",
            "Fruit",
            "BreakFast",
            "Spanish",
            "Lunch"
        )
    }
}