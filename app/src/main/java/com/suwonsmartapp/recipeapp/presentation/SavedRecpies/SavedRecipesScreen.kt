package com.suwonsmartapp.recipeapp.presentation.SavedRecpies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.component.SavedRecipe
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun SavedRecipesScreenPreview() {
    val recipes = remember {
        mutableStateListOf(
            *List(7) { index ->
                Recipe(
                    title = "Traditional spare ribs baked ${index + 1}",
                    imageResId = R.drawable.recipe_spare_ribs_baked,
                    imageDescription = "오븐에 구운 전통 돼지갈비 이미지",
                    userName = "Chef John",
                    requiredTime = 20,
                    rating = 4.0f,
                )
            }.toTypedArray()
        )
    }

    SavedRecipesScreen(
        recipes = recipes,
        onBookmarkClick = { recipes.remove(it) },
    )
}

@Composable
fun SavedRecipesScreen(
    recipes: List<Recipe>,
    onBookmarkClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 타이틀
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = "Saved recipes",
            color = AppColors.label,
            style = AppTextStyles.mediumTextBold,
            textAlign = TextAlign.Center,
        )

        // 저장된 레시피 목록
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 30.dp,
                vertical = 20.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(recipes) { recipe ->
                SavedRecipe(
                    recipe = recipe,
                    isBookmarked = true,
                    onBookmarkClick = { onBookmarkClick(recipe) },
                )
            }
        }
    }
}
