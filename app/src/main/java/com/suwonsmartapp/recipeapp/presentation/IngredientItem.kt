package com.suwonsmartapp.recipeapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.model.Ingredient
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun IngredientPreview() {
    IngredientItem(
        Ingredient(
            id = 1,
            name = "Tomatos",
            imageUrl = "",
            amountInGrams = 500,
        ),
        onClick = {},
    )
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColors.gray4,
                shape = RoundedCornerShape(size = 12.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onClick() }
            .padding(
                horizontal = 15.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(size = 40.dp)
                .background(
                    color = AppColors.white,
                    shape = RoundedCornerShape(size = 10.dp),
                )
                .padding(all = 6.dp),
            painter = painterResource(id = R.drawable.tomatoes),
            contentDescription = ingredient.name,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(width = 16.dp))
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = ingredient.name,
            style = AppTextStyles.normalTextSemiBold,
            color = AppColors.black2,
        )
        Text(
            text = stringResource(R.string.ingredient_gram, ingredient.amountInGrams),
            style = AppTextStyles.smallerTextRegular,
            color = AppColors.gray3,
        )
    }
}