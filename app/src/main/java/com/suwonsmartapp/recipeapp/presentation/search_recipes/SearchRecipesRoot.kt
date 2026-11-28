package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchRecipesRoot(
    onBackClick: () -> Unit,
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory),
) {
    val state by viewModel.state.collectAsState()
    var isFilterSheetVisible by rememberSaveable { mutableStateOf(false) }

    SearchRecipesScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onFilterClick = { isFilterSheetVisible = true },
        onBackClick = onBackClick,
    )

    if (isFilterSheetVisible) {
        FilterSearchBottomSheet(
            onFilterClick = viewModel::onFilterClick,
            onDismissRequest = { isFilterSheetVisible = false },
            initialState = state.filter,
        )
    }
}
