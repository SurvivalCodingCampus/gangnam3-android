package com.suwonsmartapp.recipeapp.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

// TODO: enum말고 다른 거 교체 예정
enum class IngredientImageId(@param:DrawableRes val resId: Int) {
    TOMATOS(R.drawable.ingredient_tomatos),
}

data class Ingredient(
    val name: String,
    val imageId: IngredientImageId,
    val requiredGrams: Int,
    val description: String,
)

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {

    val testIngredient = Ingredient(
        name = "Tomatos",
        imageId = IngredientImageId.TOMATOS,
        requiredGrams = 500,
        description = "토마토입니다",
    )

    IngredientItem(testIngredient) {}
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppColors.gray4,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(
                onClick = onClick
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(12.dp),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = ingredient.imageId.resId),
                    contentDescription = ingredient.description,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
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
            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(bottom = 27.dp),
                text = "${ingredient.requiredGrams}g",
                color = AppColors.gray3,
                style = AppTextStyles.smallTextRegular,
            )
        }

    }
}