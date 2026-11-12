package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = "qqq"
    ) {
        println("값변경")
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(315.dp)
            .height(54.dp)
    )
}