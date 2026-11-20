package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.RecipeCard
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SavedRecipesRoot(
    viewModel: SavedRecipesViewModel = viewModel(factory = SavedRecipesViewModel.Factory),
) {
    val recipes by viewModel.recipes.collectAsState()
    SavedRecipesScreen(recipes = recipes)
}

@Composable
fun SavedRecipesScreen(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(vertical = 10.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.saved_recipes_title),
            style = AppTextStyles.mediumTextBold,
            color = AppColors.black2,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(height = 10.dp))
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 30.dp,
                vertical = 10.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp),
        ) {
            items(
                items = recipes,
                key = { it.id },
            ) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    isBookmarked = true,
                    onBookmarkClick = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedRecipesScreenPreview() {
    SavedRecipesScreen(
        recipes = listOf(
            Recipe(
                id = 1,
                category = "Indian",
                name = "Traditional spare ribs baked",
                imageUrl = "",
                chef = "Chef John",
                time = "20 min",
                rating = 4.0,
            ),
            Recipe(
                id = 2,
                category = "Asian",
                name = "Spice roasted chicken with flavored rice",
                imageUrl = "",
                chef = "Mark Kelvin",
                time = "20 min",
                rating = 4.0,
            ),
        ),
    )
}
