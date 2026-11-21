package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.core.data.model.Recipe

// 썸네일 (배경, 별점, 제목/셰프) + 우측하단 슬롯
@Composable
fun RecipeThumbnail(
    recipe: Recipe,
    bottomEnd: (@Composable () -> Unit)? = null, // 우측하단에 넣을 내용
) {
    Box(
        modifier = Modifier
//            .fillMaxWidth()
            .width(315.dp)
            .height(150.dp)
            .clip(
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        // 배경 이미지 + 그라데이션
        RecipeBackground(
            modifier = Modifier.matchParentSize(),
            image = recipe.image,
            contentDescription = recipe.name,
        )

        // 우측상단 별점
        RecipeRating(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 10.dp,
                    end = 10.dp,
                ),
            rating = recipe.rating,
        )

        // 좌측하단 제목, 유저
        RecipeTitle(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    bottom = 10.dp,
                ),
            name = recipe.name,
            chef = recipe.chef,
        )

        // 우측하단 슬롯 (넘겨준 게 있을 때만 그림)
        if (bottomEnd != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 10.dp,
                        bottom = 10.dp,
                    ),
            ) {
                bottomEnd()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeThumbnailBookmarkedPreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(true) }

    val previewRecipe = Recipe(
        id = 1,
        category = "Indian",
        name = "Traditional spare ribs baked 오이시" +
                " dasdasdasdadasdasalkfjlaskdfjsadkfjsadflasklfflaksdjfsklkjsasjkfdl",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "junsik",
        time = "20 min",
        rating = 4.0,
        ingredients = emptyList(),
    )

    RecipeThumbnail(recipe = previewRecipe) {
        RecipeTimeBookmark(
            time = previewRecipe.time,
            isBookmarked = isBookmarked,
            onBookmarkClick = { isBookmarked = !isBookmarked },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeThumbnailPreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }

    val previewRecipe = Recipe(
        id = 1,
        category = "Indian",
        name = "Traditional spare ribs baked 오이시" +
                " dasdasdasdadasdasalkfjlaskdfjsadkfjsadflasklfflaksdjfsklkjsasjkfdl",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "junsik",
        time = "20 min",
        rating = 4.0,
        ingredients = emptyList(),
    )

    RecipeThumbnail(recipe = previewRecipe) {
        RecipeTimeBookmark(
            time = previewRecipe.time,
            isBookmarked = isBookmarked,
            onBookmarkClick = { isBookmarked = !isBookmarked },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeThumbnailNoBookmarkPreview() {
    val previewRecipe = Recipe(
        id = 1,
        category = "Indian",
        name = "Traditional spare ribs baked",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "junsik",
        time = "20 min",
        rating = 4.0,
        ingredients = emptyList(),
    )

    RecipeThumbnail(recipe = previewRecipe)
}
