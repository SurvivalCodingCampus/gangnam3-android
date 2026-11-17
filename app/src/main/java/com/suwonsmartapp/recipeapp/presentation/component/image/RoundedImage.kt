package com.suwonsmartapp.recipeapp.presentation.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors

@Composable
private fun RoundedImage(
    imageId: Int,
    contentDescription: String,
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = AppColors.white)
            .padding(6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,  // 비율 유지하며 꽉 채우기
        )
    }
}

//@Preview(showBackground = true)
@Preview()
@Composable
private fun RoundedImagePreview() {
    Column {
        RoundedImage(
            imageId = R.drawable.jeju,
            contentDescription = "jeju"
        )
        
        Spacer(modifier = Modifier.size(10.dp))
        
        RoundedImage(
            imageId = R.drawable.tomato,
            contentDescription = "tomato"
        )
    }
}