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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun SavedRecipePreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }

    val previewRecipe = Recipe(
        title = "Traditional spare ribs baked 오이시" +
                " dasdasdasdadasdasalkfjlaskdfjsadkfjsadflasklfflaksdjfsklkjsasjkfdl",
        imageResId = R.drawable.recipe_spare_ribs_baked,
        imageDescription = "오븐에 구운 전통 돼지갈비 이미지",
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
        // 배경 이미지
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = recipe.imageResId),
            contentDescription = recipe.imageDescription,
            contentScale = ContentScale.Crop,
        )

        // 그라데이션
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

        // 우측상단 벌점
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
            // 별점
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "평점",
                tint = AppColors.rating,
            )
            Spacer(modifier = Modifier.width(3.dp))

            // 점수
            Text(
                text = "${recipe.rating}",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextRegular
            )
        }

        // 좌측하단 제목, 유저
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    bottom = 10.dp,
                ),
        ) {
            // 제목
            Text(
                modifier = Modifier
                    .width(200.dp),
                text = recipe.title,
                color = AppColors.white,
                style = AppTextStyles.smallTextBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            // 유저
            Text(
                text = "By ${recipe.userName}",
                color = AppColors.gray4,
                style = AppTextStyles.smallerTextRegular
            )
        }

        // 우측하단 시간, 북마크 여부
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 10.dp,
                    bottom = 10.dp,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 타이머 아이콘
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "조리 시간",
                tint = AppColors.gray4,
            )
            Spacer(modifier = Modifier.width(5.dp))

            // 시간
            Text(
                text = "${recipe.requiredTime} min",
                color = AppColors.gray4,
                style = AppTextStyles.smallerTextRegular
            )
            Spacer(modifier = Modifier.width(10.dp))

            // 북마크 아이콘
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = AppColors.white,
                        shape = CircleShape,
                    )
                    .clickable(
                        onClick = onBookmarkClick
                    )
                    .padding(4.dp),
                imageVector = icon,
                contentDescription = "북마크",
                tint = AppColors.primary80,
            )
        }
    }
}