package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
private fun RatingButtonPreview() {
    var selectedRating by rememberSaveable { mutableIntStateOf(4) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            RatingButtons(
                selectedRating = selectedRating,
                onRatingClick = { rating ->
                    selectedRating = rating
                },
            )
        }
    }
}

@Composable
fun RatingButtons(
    selectedRating: Int,
    onRatingClick: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        for (rating in 5 downTo 1) {
            RatingButton(
                text = "$rating",
                isSelected = rating == selectedRating,
                onClick = { onRatingClick(rating) },
            )
        }
    }
}

@Composable
fun RatingButton(
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
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Filled.Star,
            contentDescription = "별점 ${text}점",
            tint = contentColor,
        )
    }
}
