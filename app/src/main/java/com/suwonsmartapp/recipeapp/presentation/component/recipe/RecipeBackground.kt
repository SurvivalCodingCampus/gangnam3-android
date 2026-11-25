package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

// 배경 이미지 + 아래쪽 검정 그라데이션
@Composable
fun RecipeBackground(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        // 배경 이미지
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = image,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
        )

        // 그라데이션
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 1f),
                        ),
                        startY = 100f,
                    ),
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeBackgroundPreview() {
    RecipeBackground(
        modifier = Modifier
            .width(315.dp)
            .height(150.dp),
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        contentDescription = "Traditional spare ribs baked",
    )
}
