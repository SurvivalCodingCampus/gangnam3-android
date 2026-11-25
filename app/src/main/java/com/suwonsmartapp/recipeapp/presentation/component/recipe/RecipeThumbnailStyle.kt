package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// RecipeThumbnail 안쪽 글씨/아이콘 크기 묶음
data class RecipeThumbnailStyle(
    val ratingIconSize: Dp,
    val ratingTextStyle: TextStyle,
    val titleWidthFraction: Float, // 제목 영역이 카드 너비에서 차지하는 비율
    val titleStyle: TextStyle,
    val chefStyle: TextStyle,
    val chefColor: Color,
) {
    companion object {
        // Saved recipes (315 x 150)
        val Large = RecipeThumbnailStyle(
            ratingIconSize = 8.dp,
            ratingTextStyle = AppTextStyles.smallerTextRegular,
            titleWidthFraction = 0.67f, // (패딩 10 + 제목 200) / 315
            titleStyle = AppTextStyles.smallTextBold,
            chefStyle = AppTextStyles.smallLabelRegular,
            chefColor = AppColors.gray4,
        )

        // Search (정사각형 150 x 150)
        val Small = RecipeThumbnailStyle(
            ratingIconSize = 8.dp,
            ratingTextStyle = AppTextStyles.smallLabelRegular,
            titleWidthFraction = 0.93f, // (패딩 10 + 제목 130) / 150
            titleStyle = AppTextStyles.smallTextBold,
            chefStyle = AppTextStyles.smallLabelRegular,
            chefColor = AppColors.gray3,
        )
    }
}
