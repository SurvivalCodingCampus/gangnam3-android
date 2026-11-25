package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchRecipesRoot(
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isFilterSheetVisible by rememberSaveable { mutableStateOf(false) }

    SearchRecipesScreen(
        state = state,
        onQueryChange = {
            viewModel.onQueryChange(it)
        },
        onBackClick = {
            // 화면 이동 코드
        },
        onFilterClick = {
            isFilterSheetVisible = true
        },
    )

    if (isFilterSheetVisible) {
        FilterSearchBottomSheet(
            onFilterClick = viewModel::onFilterApply,
            onDismissRequest = { isFilterSheetVisible = false },
            initialState = state.filterSearchState,
        )
    }
}