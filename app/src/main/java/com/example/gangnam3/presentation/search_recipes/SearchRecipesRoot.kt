package com.example.gangnam3.presentation.search_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gangnam3.data.model.Recipe

/**
 * 검색 화면의 연결 담당(Root).
 *
 * 검색 화면은 [SearchRecipesRoute]를 재사용하고, 필터 시트를 열고 닫는 일만 더한다.
 * 기본 ViewModel은 [SearchRecipesViewModel.Factory]가 저장소를 넣어 만든다.
 * @param onBackClick 뒤로 가기를 요청하는 콜백
 * @param onRecipeClick 검색 결과를 눌렀을 때의 콜백
 */
@Composable
fun SearchRecipesRoot(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory),
    onRecipeClick: (Recipe) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // 화면을 회전해도 시트가 열린 상태를 유지한다.
    var isFilterSheetVisible by rememberSaveable { mutableStateOf(false) }

    SearchRecipesRoute(
        viewModel = viewModel,
        onBackClick = onBackClick,
        onFilterClick = { isFilterSheetVisible = true },
        modifier = modifier,
        onRecipeClick = onRecipeClick
    )

    if (isFilterSheetVisible) {
        FilterSearchBottomSheet(
            // 이전에 적용한 필터를 이어서 보여준다.
            initialState = state.filter,
            // 실제 데이터에 있는 카테고리만 선택지로 보여준다.
            categories = listOf("All") + state.allRecipes.map { it.category }.distinct().filter { it != "All" },
            onDismissRequest = { isFilterSheetVisible = false },
            // 시트는 적용 직후 스스로 onDismissRequest를 호출해 닫힌다.
            onApplyFilter = viewModel::onApplyFilter
        )
    }
}
