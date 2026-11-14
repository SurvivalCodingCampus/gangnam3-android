package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun FilterButtonsPreview() {
    var selectedRating by rememberSaveable { mutableIntStateOf(4) }

    FilterButtons(
        selectedRating = selectedRating,
        onRatingClick = { rating ->
            selectedRating = rating
        },
    )
}

@Composable
fun FilterButtons(
    selectedRating: Int,
    onRatingClick: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        val category = listOf("a", "b", "c")

        category.forEachIndexed { index, category ->
            FilterButton(
                text = category,
                isSelected = index == selectedRating,
                onClick = { onRatingClick(index) },
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white
    val contentColor = if (isSelected) AppColors.white else AppColors.primary80
    val borderRadius = 10.dp

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(borderRadius))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = AppColors.primary80,
                shape = RoundedCornerShape(borderRadius),
            )
            .clickable(
                onClick = onClick
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = AppTextStyles.smallerTextSemiBold,
        )
    }
}
