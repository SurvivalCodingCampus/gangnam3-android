package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppIcons
import com.suwonsmartapp.recipeapp.ui.TextStyles

data class Recipe(
    val title: String,
    val chef: String,
    val time: String,
    val rating: Double,
    val image: Int,
)

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    val recipe = Recipe(
        title = "Traditional spare ribs baked",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0,
        image = R.drawable.recipe_ribs,
    )
    var isBookmarked by remember { mutableStateOf(false) }

    RecipeCard(
        recipe = recipe,
        isBookmarked = isBookmarked,
        onBookmarkClick = {
            isBookmarked = !isBookmarked
        },
    )
}

/**
 * Recipe card.
 * Layout: 150 high, radius 10, photo with a transparent-to-black vertical gradient.
 */
@Composable
fun RecipeCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(10.dp)),
    ) {
        // Photo
        Image(
            painter = painterResource(id = recipe.image),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // linear-gradient(180deg, transparent 0%, #000000 100%)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                    ),
                ),
        )

        // Rating chip (top right)
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(horizontal = 7.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = AppIcons.Bold.star,
                contentDescription = null,
                tint = AppColors.rating,
                modifier = Modifier.size(8.dp),
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = recipe.rating.toString(),
                color = AppColors.black1,
                style = TextStyles.smallerTextRegular.copy(fontSize = 8.sp),
            )
        }

        // Title, chef, time, bookmark (bottom)
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = recipe.title,
                    color = AppColors.white,
                    style = TextStyles.smallTextBold,
                    maxLines = 2,
                )
                Text(
                    text = "By ${recipe.chef}",
                    color = AppColors.white,
                    style = TextStyles.smallerTextRegular.copy(fontSize = 8.sp),
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = AppIcons.Outline.timer,
                    contentDescription = null,
                    tint = AppColors.gray4,
                    modifier = Modifier.size(17.dp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = recipe.time,
                    color = AppColors.gray4,
                    style = TextStyles.smallerTextRegular,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AppColors.white)
                        .clickable(onClick = onBookmarkClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (isBookmarked) {
                            AppIcons.Bold.bookmark
                        } else {
                            AppIcons.Outline.bookmark
                        },
                        contentDescription = "Bookmark",
                        tint = AppColors.primary80,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}