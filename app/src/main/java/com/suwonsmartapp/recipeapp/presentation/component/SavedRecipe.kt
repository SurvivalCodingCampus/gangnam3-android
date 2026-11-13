package com.suwonsmartapp.recipeapp.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
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
    val rating: Float,
)

@Preview(showBackground = true)
@Composable
private fun SavedRecipePreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }

    val previewRecipe = Recipe(
        title = "Traditional spare ribs baked 오이시",
        imageId = RecipeImageId.SPARE_RIBS_BAKED,
        imageDescription = "Traditional spare ribs baked image",
        userName = "junsik",
        requiredTime = 20,
        rating = 4.0f,
    )

    SavedRecipe(
        recipe = previewRecipe,
        isBookmarked = isBookmarked
    ) {
        isBookmarked = !isBookmarked
    }
}

@Composable
fun SavedRecipe(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
) {
    val icon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .width(315.dp)
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
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 1f),
                        ),
                        startY = 100f,
                    ),
                ),
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 10.dp,
                    end = 10.dp,
                )
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
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "평점",
                tint = AppColors.rating,
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "${recipe.rating}",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextRegular
            )
        }
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
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 10.dp,
                    bottom = 10.dp,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "조리 시간",
                tint = AppColors.gray4,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${recipe.requiredTime} min",
                color = AppColors.gray4,
                style = AppTextStyles.smallerTextRegular
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = AppColors.white,
                        shape = CircleShape,
                    )
                    .clickable(
                        onClick = onBookmarkClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = icon,
                    contentDescription = "북마크",
                    tint = AppColors.primary80,
                )
            }
        }
    }
}