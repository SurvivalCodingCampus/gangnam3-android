package com.suwonsmartapp.recipeapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun HomeRecipeCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(width = 150.dp)
            .height(height = 231.dp),
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxWidth()
                .height(height = 176.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = AppColors.gray4.copy(alpha = 0.5f))
                .padding(all = 10.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = 56.dp),
                text = recipe.name,
                style = AppTextStyles.smallTextBold,
                color = AppColors.gray1,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Column(modifier = Modifier.align(alignment = Alignment.BottomStart)) {
                Text(
                    text = stringResource(id = R.string.home_recipe_time),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray3,
                )
                Text(
                    text = recipe.time,
                    style = AppTextStyles.smallerTextBold,
                    color = AppColors.gray1,
                )
            }
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .size(size = 24.dp)
                    .clip(shape = CircleShape)
                    .background(color = AppColors.white)
                    .clickable(onClick = onBookmarkClick)
                    .padding(all = 4.dp),
                painter = painterResource(id = R.drawable.ic_bookmark_inactive),
                contentDescription = stringResource(id = R.string.recipe_bookmark),
                tint = if (isBookmarked) AppColors.primary80 else AppColors.gray3,
            )
        }
        Image(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .size(size = 110.dp)
                .clip(shape = CircleShape),
            painter = rememberAsyncImagePainter(
                model = recipe.imageUrl,
                placeholder = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                error = painterResource(id = R.drawable.traditional_spare_ribs_baked),
            ),
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .offset(y = 30.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(size = 20.dp),
                )
                .padding(horizontal = 7.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(size = 10.dp),
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
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecipeCardPreview() {
    var isBookmarked by remember { mutableStateOf(false) }
    HomeRecipeCard(
        isBookmarked = isBookmarked,
        onBookmarkClick = { isBookmarked = !isBookmarked },
        recipe = Recipe(
            id = 1,
            category = "Indian",
            name = "Classic Greek Salad",
            imageUrl = "",
            chef = "Chef John",
            time = "15 Mins",
            rating = 4.5,
        ),
    )
}
