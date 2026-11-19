package com.example.gangnam3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gangnam3.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Greeting(name = "Android", onClick = {
            // 클릭 이벤트 처리
        })

        Spacer(modifier = Modifier.height(16.dp))

        // 반복문 예시
        repeat(3) { index ->
            Text(
                text = "Hello World $index",
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun Greeting(
    name: String,
    onClick: () -> Unit
) {
    Text(
        text = "Hello $name",
        modifier = Modifier
            .background(color = Color.Red)
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        style = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    )
}