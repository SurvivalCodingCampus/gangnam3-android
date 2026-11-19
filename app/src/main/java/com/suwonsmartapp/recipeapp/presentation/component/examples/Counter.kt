package com.suwonsmartapp.recipeapp.presentation.component.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview(showBackground = true)
@Composable
private fun CounterScreenPreview() {
    var counter by rememberSaveable { mutableIntStateOf(0) }
//    CounterScreen(
//        counter = counter,
//        onIncrement = {
//            counter++
//        }
//    )
}

@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel(factory = CounterViewModel.Factory),
    onIncrement: () -> Unit= {},
) {
    val counter by viewModel.count.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Count: $counter"
        )

        Button(
            onClick = {
                viewModel.increment()
            }
        ) {
            Text("Increment")
        }
    }
}