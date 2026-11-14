package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.data.model.Ingredient
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {
    val testIngredient = Ingredient(
        name = "Tomatos",
        imageResId = R.drawable.ingredient_tomatos,
        grams = 500,
        description = "토마토 사진입니다",
    )
    IngredientItem(testIngredient) {}
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.gray4)
            .clickable(onClick = onClick)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 재료 이미지 (52dp 흰 배경 + 안쪽 6dp 여백 = 이미지 40dp)
        Image(
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = AppColors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(6.dp),
            painter = painterResource(id = ingredient.imageResId),
            contentDescription = ingredient.description,
        )
        Spacer(modifier = Modifier.width(16.dp))

        // 재료 명
        Text(
            modifier = Modifier
                .padding(
                    top = 29.dp,
                    bottom = 23.dp,
                ),
            text = ingredient.name,
            color = AppColors.black2,
            style = AppTextStyles.normalTextBold,
        )
        Spacer(modifier = Modifier.weight(1f))

        // 그램
        Text(
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 27.dp),
            text = "${ingredient.grams}g",
            color = AppColors.gray3,
            style = AppTextStyles.smallTextRegular,
        )
    }
}