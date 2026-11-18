package com.example.gangnam3.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 1. 기본 사용 방법
@Composable
fun BasicModifierExample() {
    Text(
        text = "Hello World",
        modifier = Modifier
            .padding(16.dp)              // 패딩 추가
            .background(Color.Gray)      // 배경색 설정
            .clickable { }               // 클릭 이벤트 추가
    )
}

// 2. Modifier 파라미터 전달 규칙
data class Ingredient(val name: String)

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    modifier: Modifier = Modifier, // 첫 번째 기본값 파라미터 (데이터 다음 위치)
) {
    Row(
        modifier = modifier // 최상위(루트) 레이아웃에만 적용
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(text = ingredient.name)
    }
}
