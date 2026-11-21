package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 검색 화면용 정사각형 카드 (150 x 150)
@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(RoundedCornerShape(10.dp)),
    ) {
        // 배경 이미지
        AsyncImage(
            modifier = Modifier.matchParentSize(),
            model = recipe.image,
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
        )

        // 그라데이션 (투명 → 검정)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, AppColors.black1),
                    ),
                ),
        )

        // 우측상단 별점
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 10.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(horizontal = 7.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(8.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "평점",
                tint = AppColors.rating,
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "${recipe.rating}",
                color = AppColors.black1,
                style = AppTextStyles.smallLabelRegular,
            )
        }

        // 좌측하단 제목, 셰프
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        ) {
            Text(
                modifier = Modifier.width(130.dp),
                text = recipe.name,
                color = AppColors.white,
                style = AppTextStyles.smallerTextBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = "By ${recipe.chef}",
                color = AppColors.gray3,
                style = AppTextStyles.smallLabelRegular,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    RecipeCard(
        recipe = Recipe(
            id = 1,
            category = "Indian",
            name = "Lamb chops with fruity couscous and mint",
            image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            chef = "Spicy Nelly",
            time = "20 min",
            rating = 4.0,
            ingredients = emptyList(),
        ),
    )
}
