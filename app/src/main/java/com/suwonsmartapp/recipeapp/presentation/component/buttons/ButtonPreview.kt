package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Column {
        BigButton("Button") { }
        Spacer(modifier = Modifier.size(10.dp))
        MediumButton("Button") { }
        Spacer(modifier = Modifier.size(10.dp))
        SmallButton("Button") { }
    }
}