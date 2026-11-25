package com.suwonsmartapp.recipeapp.presentation.home

import com.suwonsmartapp.recipeapp.core.data.model.Recipe

// 홈 화면 전체 상태
data class HomeUiState(
    val selectedCategory: HomeCategory = HomeCategory.ALL,
    val bookmarkedIds: Set<Long> = emptySet(),
    // 화면에 들어오자마자 불러오므로 처음엔 Loading
    val result: HomeResult = HomeResult.Loading,
)

// 레시피 목록 결과 (한 번에 하나의 상태만 가능)
sealed interface HomeResult {
    data object Loading : HomeResult

    data class Success(val recipes: List<Recipe>) : HomeResult

    data class Error(val message: String) : HomeResult
}

// 홈 카테고리 탭 (label: 화면에 보이는 글자이자 서버로 보내는 값)
enum class HomeCategory(val label: String) {
    ALL("All"),
    INDIAN("Indian"),
    ITALIAN("Italian"),
    ASIAN("Asian"),
    CHINESE("Chinese"),
    FRUIT("Fruit"),
    VEGETABLES("Vegetables"),
    PROTEIN("Protein"),
    CEREAL("Cereal"),
    LOCAL_DISHES("Local Dishes"),
}
