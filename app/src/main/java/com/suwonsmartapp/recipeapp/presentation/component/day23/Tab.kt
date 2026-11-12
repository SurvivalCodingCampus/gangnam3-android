package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.TextStyles

@Preview(showBackground = true)
@Composable
fun TabPreview() {
    Tab(
        label = "Ingredient",
        isSelected = true,
    ) {
        println("클릭!!")
    }
}

@Composable
fun Tab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(33.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = if (isSelected) AppColors.primary100 else AppColors.white,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                interactionSource = null,
                indication = null, // 눌렀을 때 회색 효과 끄기
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) AppColors.white else AppColors.primary80,
            style = TextStyles.smallerTextBold,
        )
    }
}
