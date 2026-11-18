package com.suwonsmartapp.recipeapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun FilterButtonPreview() {
    var isSelected by remember { mutableStateOf(false) }

    FilterButton(
        text = "Text",
        isSelected = isSelected,
        onClick = { isSelected = !isSelected },
    )
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = if (isSelected) AppColors.primary100 else AppColors.white)
            .border(
                width = 1.dp,
                color = if (isSelected) AppColors.primary100 else AppColors.primary80,
                shape = RoundedCornerShape(size = 10.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        text = text,
        style = TextStyles.smallerTextRegular,
        color = if (isSelected) AppColors.white else AppColors.primary80,
    )
}
