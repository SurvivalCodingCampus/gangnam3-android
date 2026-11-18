package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

    // Padding keeps the button clear of the status bar when the preview runs on a device.
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .padding(20.dp),
    ) {
        FilterButton(
            text = "Text",
            isSelected = isSelected,
            onClick = {
                isSelected = !isSelected
            },
        )
    }
}

// Both states side by side, visible without Interactive Mode.
@Preview(showBackground = true)
@Composable
fun FilterButtonStatesPreview() {
    Row(
        modifier = Modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        FilterButton(
            text = "Text",
            isSelected = false,
            onClick = {},
        )
        FilterButton(
            text = "Text",
            isSelected = true,
            onClick = {},
        )
    }
}

/**
 * Filter button.
 * Layout: 43 x 27, radius 10, border 1, padding 5 (vertical) / 10 (horizontal), gap 5.
 */
@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white
    val contentColor = if (isSelected) AppColors.white else AppColors.primary80
    val borderColor = if (isSelected) AppColors.white else AppColors.primary80

    Row(
        modifier = Modifier
            .height(27.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(
                start = 10.dp,
                top = 5.dp,
                end = 10.dp,
                bottom = 5.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = TextStyles.smallerTextRegular,
        )
    }
}
