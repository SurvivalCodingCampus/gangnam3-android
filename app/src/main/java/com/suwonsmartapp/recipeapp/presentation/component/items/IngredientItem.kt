package com.suwonsmartapp.recipeapp.presentation.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.util.capitalizeWords
import com.suwonsmartapp.recipeapp.domain.model.recipe.Ingredient
import com.suwonsmartapp.recipeapp.presentation.component.images.RoundedImage
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = AppColors.gray4)
            .clickable(
                onClick = onClick,
            )
            .padding(
                horizontal = 15.dp,
                vertical = 12.dp
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RoundedImage(
                ingredient.imageUrl,
                contentDescription = ingredient.name,
            )
            
            Text(
                text = ingredient.name.capitalizeWords(),
                color = AppColors.black2,
                style = AppTextStyles.normalTextBold.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,  // ...으로 생략하기
                modifier = Modifier
                    .weight(1f)
                    .offset(y = 3.dp)
            )
            
            Text(
                text = ingredient.amount,
                color = AppColors.gray3,
                style = AppTextStyles.smallTextRegular,
                modifier = Modifier
                    .offset(y = 0.5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {
    val tomato = Ingredient(
        name = "tomatos",
        imageUrl = R.drawable.tomato,
        amount = "500g"
    )
    
    Column {
        IngredientItem(
            ingredient = tomato,
        )
        Spacer(Modifier.size(5.dp))
        IngredientItem(
            ingredient = tomato.copy(
                name = "멋쟁이 토마토 나는야 주스될거야 나는야 케찹될거야 나는야 춤을 출거야 뽐내는 토마토 토마토"
            ),
        )
    }
}