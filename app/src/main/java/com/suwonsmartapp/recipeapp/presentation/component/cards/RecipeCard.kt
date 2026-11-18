package com.suwonsmartapp.recipeapp.presentation.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.domain.model.RecipeCategory
import com.suwonsmartapp.recipeapp.domain.model.UserSummary
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

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
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(R.drawable.iu),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        // 이미지 위 선형 그라데이션 오버레이
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.black1.copy(alpha = 0f),
                            AppColors.black1
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // 별점
            RatingBadge(
                rating = recipe.rating,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.size(60.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    // 제목
                    Text(
                        text = recipe.title,
                        color = AppColors.white,
                        style = AppTextStyles.smallTextBold.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,  // ... 으로 생략
                        modifier = Modifier
                            .height(42.dp)
                    )

                    // Chef 정보
                    Text(
                        text = "By ${recipe.chef.name}",
                        color = AppColors.gray4,
                        style = AppTextStyles.smallLabelRegular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(12.dp)
                    )
                }

                // 조리 시간
                CookingTimeLabel(recipe.cookingTimeMinutes)

                // 북마크
                CircleBookmarkButton(
                    isBookmarked = isBookmarked,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}

@Composable
private fun CircleBookmarkButton(
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = AppColors.white)
            .toggleable(  // 버튼 누를 때 clickable과 달리 음성 안내가 true/false를 안내해줌
                value = isBookmarked,
                role = Role.Checkbox,
                onValueChange = { onBookmarkClick() }
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {
        BookmarkIcon(isBookmarked)
    }
}

@Composable
private fun CookingTimeLabel(
    cookingTimeMinutes: Int,
) {
    Row(
        modifier = Modifier
            .widthIn(min = 60.dp)
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        CompositionLocalProvider(
            // 스타일 일괄 적용
            LocalContentColor provides AppColors.gray4,
            LocalTextStyle provides AppTextStyles.smallerTextRegular,
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "Timer",
                modifier = Modifier
                    .size(17.dp)
            )

            Text(
                text = "$cookingTimeMinutes min",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f, fill = false)
            )
        }
    }
}

@Composable
private fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .widthIn(min = 37.dp)
            .height(16.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = AppColors.secondary20)
            .padding(
                vertical = 2.dp,
                horizontal = 7.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.StarRate,
            contentDescription = "Star rate",
            tint = AppColors.rating,
            modifier = Modifier
                .size(8.dp)
        )

        Text(
            text = "$rating",
            color = AppColors.black1,
            style = AppTextStyles.smallLabelRegular,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    val chef = UserSummary(
        id = "A001",
        name = "John",
        profileImageUrl = "",
        location = ""
    )

    val recipe = Recipe(
        id = "001",
        title = "Traditional spare ribs baked Traditional spare ribs baked Traditional spare ribs baked Traditional spare ribs baked ",
        imageUrl = "https://iu.png",
        videoUrl = "",
        chef = chef,
        category = RecipeCategory.UNKNOWN,
        reviewCount = 10,
        cookingTimeMinutes = 20,
        createAt = 261113L,
        rating = 4.0
    )

    var isBookmarked by remember { mutableStateOf(false) }
    var isBookmarked2 by remember { mutableStateOf(true) }

    Column {
        RecipeCard(
            recipe = recipe,
            isBookmarked = isBookmarked,
        ) {
            isBookmarked = !isBookmarked
        }
        Spacer(Modifier.size(5.dp))
        RecipeCard(
            recipe = recipe,
            isBookmarked = isBookmarked2,
        ) {
            isBookmarked2 = !isBookmarked2
        }
    }
}

@Composable
private fun BookmarkIcon(
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
) {
    val bookmarkImage = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder
    val bookmarkColor = if (isBookmarked) AppColors.primary80 else AppColors.gray4

    Icon(
        imageVector = bookmarkImage,
        contentDescription = "Bookmark",
        tint = bookmarkColor,
        modifier = modifier
            .size(24.dp)
    )
}
