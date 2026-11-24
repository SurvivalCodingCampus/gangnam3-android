package com.suwonsmartapp.recipeapp.presentation.search_recipes

// 필터 하단 시트에서 고른 값
data class FilterSearchState(
    val time: FilterTime = FilterTime.ALL,
    val rate: FilterRate? = null, // null이면 별점 필터 없음
    val category: FilterCategory = FilterCategory.ALL,
) {
    // 아무 필터도 안 골랐는지
    val isEmpty: Boolean
        get() = time == FilterTime.ALL && rate == null && category == FilterCategory.ALL
}

// 정렬 (label: 화면에 보이는 글자)
enum class FilterTime(val label: String) {
    ALL("All"),
    NEWEST("Newest"),
    OLDEST("Oldest"),
    POPULARITY("Popularity"),
}

// 별점
enum class FilterRate(val value: Int) {
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
}

// 카테고리 (label: 화면에 보이는 글자이자 서버로 보내는 값)
enum class FilterCategory(val label: String) {
    ALL("All"),
    CEREAL("Cereal"),
    VEGETABLES("Vegetables"),
    DINNER("Dinner"),
    CHINESE("Chinese"),
    LOCAL_DISH("Local Dish"),
    FRUIT("Fruit"),
    BREAKFAST("BreakFast"),
    SPANISH("Spanish"),
    LUNCH("Lunch"),
}
