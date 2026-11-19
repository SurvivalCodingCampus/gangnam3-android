package com.suwonsmartapp.recipeapp.presentation.component.recipe

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
import com.suwonsmartapp.recipeapp.core.data.model.RecipeIngredient
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {
    val testIngredient = RecipeIngredient(
        ingredient = Ingredient(
            id = 1,
            name = "Tomato",
            image = "https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg",
        ),
        amount = 500,
    )
    IngredientItem(testIngredient) {}
}

@Composable
fun IngredientItem(
    recipeIngredient: RecipeIngredient,
    onClick: () -> Unit,
) {
    val ingredient = recipeIngredient.ingredient

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
            // TODO: ingredient.image(URL) 로딩 (이미지 로더 도입 전까지 placeholder)
            painter = painterResource(id = R.drawable.ingredient_tomatos),
            contentDescription = ingredient.name,
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
            text = "${recipeIngredient.amount}g",
            color = AppColors.gray3,
            style = AppTextStyles.smallTextRegular,
        )
    }
}