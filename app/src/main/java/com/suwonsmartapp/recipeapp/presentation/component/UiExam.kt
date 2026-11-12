package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.R

@Preview(showBackground = true)
@Composable
fun Hello() {
//    Text("Hello World")
    Text(text = "Hello World",
        modifier = Modifier.fillMaxWidth()
        )
}

@Preview(showBackground = true)
@Composable
fun Greeting() {
    Text(
        modifier = Modifier
            .background(color = Color.Cyan)
            .padding(16.dp),
        text = "Hello",
    )
}

@Preview(showBackground = true)
@Composable
fun ColumnExam() {
    Column {
        Text(
            text = "Hello",
            lineHeight = 30.sp,
        )
        Text("Hello")
        
        repeat(5) { it ->
            Text("World $it")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowExam() {
    Row {
        Text("Hello")
        Text("Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun BoxExam() {
    Box {
        Text("Hello")
        Text(
            text = "Hello",
            style = TextStyle(
                color = Color.Red,
                fontSize = 20.sp,
            )
        )
    }
}

//@Preview(showBackground = true)  composable 함수에 입력 인자가 있으면 preview 안 됨
@Composable
fun TextTest(text: String) {
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
fun ComposableTest() {
    Column {
        BoxExam()
        RowExam()
        ColumnExam()
        Greeting()
        TextTest("Hello")
        
        Box(
            modifier = Modifier.clickable{
                println("Click!!!")
            }
        ) {
            TextTest("World")
        }
    }
}

data class Recipe(val name: String) {}

@Composable
fun RecipeCard(recipe: Recipe) {
    // composable 함수는 불변인 데이터만 받아야 함
    // 안그러면 화면을 계속 다시 그림 = 재갱신함
    // data class는 불변 데이터
}

@Preview(showBackground = true)
@Composable
fun IconExam() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "뒤로가기",
    )
    Icon(
        painter = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "ic_launcher_foreground",
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonTest() {
    Row(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),  // 남은 가로 공간을 모두 차지해서 버튼을 오른쪽 끝으로 밀어냄
        ) {
            Text("Hello, ")
            Text("World")
        }
        
        ElevatedButton(
            onClick = {}
        ) {
            Text("더 보기")
        }
    }
}