package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 정사각형 카드 (너비는 바깥에서 정함, 높이 = 너비), 시간/북마크 없음
@Composable
fun SearchCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        // 배경 이미지 + 그라데이션
        RecipeBackground(
            modifier = Modifier.fillMaxSize(),
            image = recipe.image,
            contentDescription = recipe.name,
        )

        // 우측상단 별점 (아이콘 8, 글씨 8sp)
        RecipeRating(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 10.dp,
                    end = 10.dp,
                ),
            rating = recipe.rating,
            iconSize = 8.dp,
            textStyle = AppTextStyles.smallLabelRegular,
        )

        // 좌측하단 제목, 유저 (제목 11sp, 셰프 8sp)
        RecipeTitle(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    bottom = 10.dp,
                ),
            name = recipe.name,
            chef = recipe.chef,
            titleWidth = 130.dp,
            titleStyle = AppTextStyles.smallerTextBold,
            chefStyle = AppTextStyles.smallLabelRegular,
            chefColor = AppColors.gray3,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchCardPreview() {
    SearchCard(
        modifier = Modifier.size(150.dp),
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
