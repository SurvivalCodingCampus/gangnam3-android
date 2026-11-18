package com.example.gangnam3.modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// 3. 순서 중요성 비교 1 (background -> padding)
@Composable
fun OrderExample1() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello world")
    }
}

// 4. 순서 중요성 비교 2 (padding -> background)
@Composable
fun OrderExample2() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello world")
    }
}

// 5. 이중 원형 클리핑 및 배경 적용
@Composable
fun DoubleCircleShapeExample() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Red)
            .padding(16.dp)
            .clip(CircleShape)
            .background(Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello world")
    }
}

// 6. 모양 관련 수정자 (둥근 이미지 및 모서리 굴리기)
@Composable
fun CircleImageExample(resId: Int) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
}

@Composable
fun RoundedCornerImageExample(resId: Int) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(topStartPercent = 50))
    )
}

// 7. offset vs padding 비교
@Composable
fun OffsetVsPaddingExample() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "Hello world",
            modifier = Modifier
                .offset(x = 50.dp, y = 20.dp)
                .background(Color.Green)
        )
        Text(
            text = "Hello world",
            modifier = Modifier
                .padding(start = 50.dp, top = 20.dp)
                .background(Color.Green)
        )
    }
}
