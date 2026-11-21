package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 별점 뱃지 (별 아이콘 + 점수)
@Composable
fun RecipeRating(
    rating: Double,
    modifier: Modifier = Modifier,
    iconSize: Dp = 12.dp,
    textStyle: TextStyle = AppTextStyles.smallerTextRegular,
) {
    Row(
        modifier = modifier
            .background(
                color = AppColors.secondary20,
                shape = RoundedCornerShape(20.dp),
            )
            .padding(
                horizontal = 7.dp,
                vertical = 2.dp,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 별점
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = Icons.Filled.Star,
            contentDescription = "평점",
            tint = AppColors.rating,
        )
        Spacer(modifier = Modifier.width(3.dp))

        // 점수
        Text(
            text = "$rating",
            color = AppColors.black1,
            style = textStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeRatingPreview() {
    RecipeRating(rating = 4.0)
}
