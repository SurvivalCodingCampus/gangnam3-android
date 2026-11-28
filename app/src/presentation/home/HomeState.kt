package com.example.gangnam3.presentation.home

import com.example.gangnam3.data.model.Recipe

/**
 * 홈 화면을 그리는 데 필요한 값을 하나로 묶은 UI State.
 *
 * 모든 값이 val이므로 변경할 때는 copy()로 새 객체를 만든다.
 * @property userName 인사말에 표시할 이름
 * @property categories 선택할 수 있는 카테고리 목록
 * @property selectedCategory 현재 선택한 카테고리. "All"이면 전체를 뜻한다.
 * @property allRecipes 불러온 전체 목록(원본)
 * @property filteredRecipes 카테고리를 적용해 실제로 표시할 목록
 * @property isLoading 불러오는 중인지 여부
 */
data class HomeState(
    val userName: String = "Jega",
    val categories: List<String> = listOf("All", "Indian", "Italian", "Asian", "Chinese"),
    val selectedCategory: String = "All",
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)