package com.suwonsmartapp.recipeapp.presentation

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
import coil3.compose.rememberAsyncImagePainter
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    val recipe = remember {
        Recipe(
            id = 1,
            category = "Indian",
            name = "Traditional spare ribs baked",
            imageUrl = "",
            chef = "Chef John",
            time = "20 min",
            rating = 4.0,
        )
    }
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
                painter = rememberAsyncImagePainter(
                    model = recipe.imageUrl,
                    placeholder = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                    error = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                ),
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
                style = AppTextStyles.smallerTextRegular,
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
                    text = recipe.name,
                    style = AppTextStyles.smallTextBold,
                    color = AppColors.white,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(R.string.recipe_chef, recipe.chef),
                    style = AppTextStyles.smallerTextRegular,
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
                    text = recipe.time,
                    style = AppTextStyles.smallerTextRegular,
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