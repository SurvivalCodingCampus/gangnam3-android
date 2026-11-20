package com.suwonsmartapp.recipeapp._practice

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.suwonsmartapp.recipeapp.R

@Preview
@Composable
fun CoilMain() {
    val imgUrl = "https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg"
    
    AsyncImage(
        model = imgUrl,
        contentDescription = "tomato",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.jeju),
        error = painterResource(R.drawable.jeju),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}