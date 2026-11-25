package com.example.gangnam3.presentation.search_recipes

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * 검색 화면의 연결 담당(Root).
 *
 * 상태 구독, 이벤트 연결, 필터 시트 표시를 맡고 그리는 일은 [SearchRecipesScreen]에 맡긴다.
 * 기본 ViewModel은 [SearchRecipesViewModel.Factory]가 저장소를 넣어 만든다.
 * @param onBackClick 뒤로 가기를 요청하는 콜백
 */
@Composable
fun SearchRecipesRoot(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory)
) {
    // 화면이 보이는 동안에만 ViewModel의 상태를 수집한다.
    val state by viewModel.state.collectAsStateWithLifecycle()
    // 시트 표시 여부. remember이므로 화면을 회전하면 닫힌다.
    var isFilterSheetVisible by remember { mutableStateOf(false) }

    SearchRecipesScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onBackClick = onBackClick,
        onOpenFilterClick = { isFilterSheetVisible = true },
        modifier = modifier
    )

    // true일 때만 시트를 화면에 추가한다.
    if (isFilterSheetVisible) {
        FilterSearchBottomSheet(
            // 이전에 적용한 필터를 이어서 보여준다.
            initialState = state.filter,
            onDismissRequest = { isFilterSheetVisible = false },
            onApplyFilter = { selectedFilter ->
                // 시트는 적용 직후 스스로 onDismissRequest를 호출해 닫힌다.
                viewModel.onApplyFilter(selectedFilter)
            }
        )
    }
}