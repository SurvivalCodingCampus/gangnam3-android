package com.suwonsmartapp.recipeapp.presentation.component

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

data class Ingredient(
    val name: String,
    val amount: String,
    val image: Int,
)

@Preview(showBackground = true)
@Composable
fun IngredientItemPreview() {
    IngredientItem(
        ingredient = Ingredient(
            name = "Tomato",
            amount = "500g",
            image = R.drawable.tomato,
        ),
        onClick = {
            println("클릭")
        },
    )
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.gray4)
            .clickable(onClick = onClick)
            .padding(horizontal = 15.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AppColors.white),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = ingredient.image),
                contentDescription = ingredient.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = ingredient.name,
            color = AppColors.black2,
            style = TextStyles.normalTextBold,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = ingredient.amount,
            color = AppColors.gray3,
            style = TextStyles.smallTextRegular,
        )
    }
}
