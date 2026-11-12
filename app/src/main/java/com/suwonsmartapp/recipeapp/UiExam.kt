package com.suwonsmartapp.recipeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun Hello() {
    Text("안녕")
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
            fontSize = 20.sp,
            color = Color.Blue,
        )
    }
}

// 243 54
@Preview(showBackground = true)
@Composable
fun MediumButton() {

}

//@Preview(showBackground = true)
//@Composable
//fun IconExam() {
//    Icon(
//
//    )
//}