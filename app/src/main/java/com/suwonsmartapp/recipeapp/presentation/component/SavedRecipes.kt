package com.suwonsmartapp.recipeapp.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppFonts
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

// TODO: enum말고 다른 거 교체 예정
enum class RecipeImageId(@param:DrawableRes val resId: Int) {
    SPARE_RIBS_BAKED(R.drawable.recipe_spare_ribs_baked),
}

data class Recipe(
    val title: String,
    val imageId: RecipeImageId,
    val imageDescription: String,
    val userName: String,
    val requiredTime: Int,
)

@Preview(showBackground = true)
@Composable
private fun SavedRecipePreview() {

    val previewRecipe = Recipe(
        title = "Traditional spare ribs baked 오이시",
        imageId = RecipeImageId.SPARE_RIBS_BAKED,
        imageDescription = "Traditional spare ribs baked image",
        userName = "junsik",
        requiredTime = 20
    )

    SavedRecipe(
        recipe = previewRecipe,
        isBookmarked = false
    ) {

    }
}

@Composable
fun SavedRecipe(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(315.dp)
            .height(150.dp)
            .clip(
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = recipe.imageId.resId),
            contentDescription = recipe.imageDescription,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    bottom = 10.dp,
                ),
        ) {
            Text(
                modifier = Modifier
                    .width(200.dp),
                text = recipe.title,
                color = AppColors.white,
                style = AppTextStyles.smallTextBold
            )
            Text(
                text = "By ${recipe.userName}",
                color = AppColors.gray4,
                style = AppTextStyles.smallerTextRegular
            )

            // TODO: 오른쪽 해
        }
    }
}