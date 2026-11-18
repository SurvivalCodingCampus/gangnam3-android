package com.suwonsmartapp.recipeapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.User
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
private fun createPreviewRecipe() = Recipe(
    id = 1,
    title = "Traditional spare ribs  baked",
    imageUrl = "",
    videoUrl = null,
    chef = User(
        id = 1,
        name = "Chef John",
        profileImageUrl = "",
        job = "Chef",
        introduction = "",
        location = "",
        followerCount = 0,
        followingCount = 0,
    ),
    category = "Dinner",
    rating = 4.0,
    reviewCount = 0,
    cookingTimeMinutes = 20,
    serving = 1,
    ingredients = emptyList(),
    procedures = emptyList(),
    createdAt = LocalDateTime.of(2026, 11, 17, 0, 0),
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    val recipe = remember { createPreviewRecipe() }
    var isBookmarked by remember { mutableStateOf(false) }

    RecipeCard(
        recipe = recipe,
        isBookmarked = isBookmarked,
        onBookmarkClick = { isBookmarked = !isBookmarked },
    )
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 150.dp)
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .paint(
                painter = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                contentScale = ContentScale.Crop,
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.black1.copy(alpha = 0f),
                        AppColors.black1,
                    ),
                ),
            )
            .padding(all = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(size = 20.dp),
                )
                .padding(horizontal = 7.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 3.dp)
                    .size(size = 8.dp),
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                tint = AppColors.rating,
            )
            Text(
                text = recipe.rating.toString(),
                style = TextStyles.smallerTextRegular,
                color = AppColors.black1,
            )
        }
        Row(
            modifier = Modifier.align(alignment = Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom,
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    modifier = Modifier.widthIn(max = 200.dp),
                    text = recipe.title,
                    style = TextStyles.smallTextBold,
                    color = AppColors.white,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(R.string.recipe_chef, recipe.chef.name),
                    style = TextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(size = 17.dp),
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = null,
                    tint = AppColors.gray4,
                )
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(R.string.recipe_cooking_time, recipe.cookingTimeMinutes),
                    style = TextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
                Icon(
                    modifier = Modifier
                        .size(size = 24.dp)
                        .clip(shape = CircleShape)
                        .background(color = AppColors.white)
                        .clickable(onClick = onBookmarkClick)
                        .padding(all = 4.dp),
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = stringResource(R.string.recipe_bookmark),
                    tint = if (isBookmarked) AppColors.primary80 else AppColors.gray4,
                )
            }
        }
    }
}