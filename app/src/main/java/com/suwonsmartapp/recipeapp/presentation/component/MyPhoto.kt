package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R


@Preview(showBackground = true)
@Composable
fun MyPhoto() {
    Image(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp),
        painter = painterResource(id = R.drawable.iu),
        contentDescription = "아이유",
        contentScale = ContentScale.Crop,
    )
}