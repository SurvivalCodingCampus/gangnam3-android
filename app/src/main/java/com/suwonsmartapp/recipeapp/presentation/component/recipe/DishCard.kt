package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 홈 요리 카드 (Figma: Home > Dishes > Card, 150 x 231)
// 위로 튀어나온 원형 사진 + 회색 카드 + 별점 / 제목 / 시간 / 북마크
@Composable
fun DishCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bookmarkIcon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

    Box(
        modifier = modifier.size(width = 150.dp, height = 231.dp),
    ) {
        // 회색 카드 배경 (위에서 55 내려옴)
        Box(
            modifier = Modifier
                .padding(top = 55.dp)
                .fillMaxWidth()
                .height(176.dp)
                .background(
                    color = AppColors.gray4.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                ),
        )

        // 원형 음식 사진
        AsyncImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(110.dp)
                .clip(CircleShape),
            model = recipe.image,
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
            // 로딩 중이거나 불러오기 실패하면 회색 원
            placeholder = ColorPainter(AppColors.gray4),
            error = ColorPainter(AppColors.gray4),
        )

        // 우측 별점
        RecipeRating(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp),
            rating = recipe.rating,
            iconSize = 10.dp,
            textStyle = AppTextStyles.smallerTextRegular,
        )

        // 제목 (가운데 정렬, 최대 2줄)
        Text(
            modifier = Modifier
                .padding(
                    top = 121.dp,
                    start = 10.dp,
                    end = 10.dp,
                )
                .fillMaxWidth(),
            text = recipe.name,
            color = AppColors.gray1,
            style = AppTextStyles.smallTextBold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        // 좌측하단 조리 시간
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    bottom = 10.dp,
                ),
        ) {
            Text(
                text = "Time",
                color = AppColors.gray3,
                style = AppTextStyles.smallerTextRegular,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = recipe.time,
                color = AppColors.gray1,
                style = AppTextStyles.smallerTextBold,
            )
        }

        // 우측하단 북마크
        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 10.dp,
                    bottom = 10.dp,
                )
                .size(24.dp)
                .background(
                    color = AppColors.white,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .clickable(onClick = onBookmarkClick)
                .padding(4.dp),
            imageVector = bookmarkIcon,
            contentDescription = "북마크",
            tint = AppColors.primary80,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DishCardPreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }

    DishCard(
        recipe = Recipe(
            id = 1,
            category = "Indian",
            name = "Classic Greek Salad",
            image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            chef = "Chef John",
            time = "15 Mins",
            rating = 4.5,
            ingredients = emptyList(),
        ),
        isBookmarked = isBookmarked,
        onBookmarkClick = { isBookmarked = !isBookmarked },
    )
}

@Preview(showBackground = true)
@Composable
private fun DishCardLongTitlePreview() {
    DishCard(
        recipe = Recipe(
            id = 2,
            category = "Indian",
            name = "Shrimp Chicken Andouille Sausage Jambalaya",
            image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            chef = "Chef John",
            time = "10 Mins",
            rating = 3.0,
            ingredients = emptyList(),
        ),
        isBookmarked = true,
        onBookmarkClick = {},
    )
}
