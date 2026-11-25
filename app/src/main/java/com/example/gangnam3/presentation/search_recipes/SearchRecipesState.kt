package com.example.gangnam3.presentation.search_recipes

import com.example.gangnam3.data.model.Recipe

/**
 * 검색 화면이 그리는 데 필요한 값을 한 번에 전달하는 불변 UI 상태입니다.
 *
 * ViewModel은 검색어, 원본 레시피 목록, 현재 검색 결과와 로딩 여부를 이 객체로 묶어
 * StateFlow에 게시합니다. 화면은 이 값을 읽어 UI를 표시하고, 상태 변경은 ViewModel의
 * 이벤트 함수에 요청합니다. `copy`를 사용해 일부 값만 바꾸면 다른 상태는 그대로 유지됩니다.
 *
 * 적용이 확정된 필터는 [filter]에 보관합니다. 시트에서 고르는 중인 임시 선택값과
 * 시트 표시 여부는 포함하지 않으며, 각각 바텀시트와 Root가 관리합니다.
 *
 * @property allRecipes 검색어 또는 필터를 적용하기 전 기준이 되는 전체 레시피 목록입니다.
 * @property filteredRecipes 현재 query와 필터 조건을 적용해 화면에 표시할 결과 목록입니다.
 * @property query 검색 입력창에 표시되는 현재 검색 문자열입니다.
 * @property isLoading 목록을 불러오거나 검색 결과를 준비 중인지 나타냅니다.
 * @property errorMessage 목록을 불러오지 못했을 때 표시할 문구이며, 정상이면 `null`입니다.
 * @property filter 현재 적용된 필터입니다. 기본값은 아무것도 제한하지 않습니다.
 */
data class SearchRecipesState(
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val filter: FilterSearchState = FilterSearchState()
)
