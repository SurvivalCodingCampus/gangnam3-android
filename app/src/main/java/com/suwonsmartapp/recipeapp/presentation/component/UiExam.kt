package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun Hello() {
    Text(
        text = "안녕",
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun ColumnExam() {
    Column {
        Text(
            text = "안녕",
            lineHeight = 30.sp,
        )
        Text("안녕")
    }
}

@Preview(showBackground = true)
@Composable
fun RowExam() {
    Row {
        Text("안녕")
        Text("안녕")
    }
}

@Preview(showBackground = true)
@Composable
fun BoxExam() {
    Box {
        Text("안녕")
        Text(
            text = "안녕",
            style = TextStyle(
                color = Color.Red,
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun MyText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = Color.Blue,
            fontSize = 10.sp,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MyComposable() {
    Column {
        BoxExam()
        RowExam()
        ColumnExam()
        MyText("안녕")

        Box(
            modifier = Modifier.clickable {
                println("클릭!!!!")
            }
        ) {
            MyText("kotlin")
        }
    }
}

data class Recipe(val name: String)

@Composable
fun RecipeCard(recipe: Recipe) {

}


@Preview(showBackground = true)
@Composable
fun IconExam() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "뒤로 가기"
    )
}