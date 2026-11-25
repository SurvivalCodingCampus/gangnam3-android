package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe

/**
 * 검색 화면이 그리는 데 필요한 값을 한 번에 전달하는 불변 UI 상태입니다.
 *
 * ViewModel은 검색어, 원본 레시피 목록, 현재 검색 결과와 로딩 여부를 이 객체로 묶어
 * StateFlow에 게시합니다. 화면은 이 값을 읽어 UI를 표시하고, 상태 변경은 ViewModel의
 * 이벤트 함수에 요청합니다. `copy`를 사용해 일부 값만 바꾸면 다른 상태는 그대로 유지됩니다.
 *
 * 필터 바텀시트의 선택값은 이 모델에 포함되지 않습니다. 검색 화면은 필터 버튼을 눌렀을 때
 * 상위 화면에 콜백을 전달하고, 실제 시트 표시와 필터 적용은 별도의 화면 상태와 이벤트가 담당합니다.
 *
 * @property allRecipes 검색어 또는 필터를 적용하기 전 기준이 되는 전체 레시피 목록입니다.
 * @property filteredRecipes 현재 query와 필터 조건을 적용해 화면에 표시할 결과 목록입니다.
 * @property query 검색 입력창에 표시되는 현재 검색 문자열입니다.
 * @property isLoading 목록을 불러오거나 검색 결과를 준비 중인지 나타냅니다.
 */
data class SearchRecipesState(
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false
)
