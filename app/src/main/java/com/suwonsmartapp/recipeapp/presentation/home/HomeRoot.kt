package com.suwonsmartapp.recipeapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        onSelectCategory = { category -> viewModel.onSelectCategory(category) },
    )
}
