package com.suwonsmartapp.recipeapp.presentation.saved_recpies

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
                    id = index.toLong() + 1,
                    category = "Indian",
                    name = "Traditional spare ribs baked ${index + 1}",
                    image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
                    chef = "Chef John",
                    time = "20 min",
                    rating = 4.0,
                    ingredients = emptyList(),
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
