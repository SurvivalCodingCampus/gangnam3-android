package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun MediumButtonPreview(modifier: Modifier = Modifier) {
    MediumButton("Button") { }
}

@Composable
fun MediumButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(243.dp)
            .height(54.dp)
            .background(
                color = AppColors.primary100,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(  // Text에 Modifier 적용하기 위해 Box로 감싸줌
                modifier = Modifier
                    .width(114.dp)
                    .height(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = text,
                    color = AppColors.white,
                    style = AppTextStyles.normalTextBold,
                    textAlign = TextAlign.Center,
                )
            }
            
            Spacer(modifier = Modifier.width(9.dp))
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "arrow forward",
                tint = AppColors.white,
                modifier = Modifier.size(20.dp),  // 하나만 적어주면 가로세로 20.dp 적용됨
            )
        }
    }
}