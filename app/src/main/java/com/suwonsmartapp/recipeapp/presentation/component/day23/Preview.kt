package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview1() {
    InputField(
        label = "label",
        placeholder = "Placeholder",
        value = ""
    ) {
        println(it)
    }
}

@Preview(showBackground = true)
@Composable
private fun BigButtonPreview1() {
    BigButton("Sign In") {
        println("클릭!!")
    }
}

@Preview(showBackground = true)
@Composable
private fun MediumButtonPreview1() {
    MediumButton("Sign In") {
        println("클릭!!")
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallButtonPreview1() {
    SmallButton("Sign In") {
        println("클릭!!")
    }
}
