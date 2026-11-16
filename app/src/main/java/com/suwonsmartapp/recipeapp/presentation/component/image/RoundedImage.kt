package com.suwonsmartapp.recipeapp.presentation.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R

@Composable
private fun RoundedImage(
    imageId: Int,
    contentDescription: String,
) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop,  // 비율 유지하며 꽉 채우기
    )
}

@Preview(showBackground = true)
@Composable
private fun RoundedImagePreview() {
    RoundedImage(
        imageId = R.drawable.jeju,
        contentDescription = "jeju"
    )
}