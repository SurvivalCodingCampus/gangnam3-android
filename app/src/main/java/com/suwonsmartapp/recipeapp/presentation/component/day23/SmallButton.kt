package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.TextStyles

@Preview(showBackground = true)
@Composable
fun SmallButtonPreview() {
    SmallButton("Sign In") {
        println("클릭!!")
    }
}

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(174.dp)
            .height(37.dp)
            .background(
                color = AppColors.primary100,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(114.dp)
                    .height(17.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = AppColors.white,
                    style = TextStyles.smallerTextBold,
                )
            }
        }
    }
}