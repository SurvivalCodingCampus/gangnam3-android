package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 제목 + 셰프
@Composable
fun RecipeTitle(
    name: String,
    chef: String,
    modifier: Modifier = Modifier, // 너비는 바깥에서 정함
    titleStyle: TextStyle = AppTextStyles.smallTextBold,
    chefStyle: TextStyle = AppTextStyles.smallerTextRegular,
    chefColor: Color = AppColors.gray4,
) {
    Column(
        modifier = modifier,
    ) {
        // 제목
        Text(
            text = name,
            color = AppColors.white,
            style = titleStyle,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        // 유저
        Text(
            text = "By $chef",
            color = chefColor,
            style = chefStyle
        )
    }
}

@Preview
@Composable
private fun RecipeTitlePreview() {
    RecipeTitle(
        modifier = Modifier
            .width(200.dp)
            .background(AppColors.black1),
        name = "Traditional spare ribs baked",
        chef = "Chef John",
    )
}
