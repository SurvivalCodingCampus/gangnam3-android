package com.suwonsmartapp.recipeapp.presentation._practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel(
        factory = CounterViewModel.Factory
    ),
    onIncrement: () -> Unit = {},
) {
    val countValue by viewModel.count.collectAsStateWithLifecycle()
    
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Count $countValue",
        )
        
        Button(
            onClick = {
                viewModel.increment()
            },
        ) {
            Text(
                text = "Increment",
            )
        }
    }
}