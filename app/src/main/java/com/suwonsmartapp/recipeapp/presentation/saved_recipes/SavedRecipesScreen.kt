package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.component.recipe.SavedRecipe
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

// ViewModel과 연결하는 Root (상태를 가진 쪽)
@Composable
fun SavedRecipesScreenRoot(
    viewModel: SavedRecipesScreenViewModel
        = viewModel(factory = SavedRecipesScreenViewModel.Factory),
) {
    // 라이프사이클 인식: STARTED 상태에서만 수집
    val state by viewModel.state.collectAsStateWithLifecycle()
    val unbookmarkedIds by viewModel.unbookmarkedIds.collectAsStateWithLifecycle()

    SavedRecipesScreen(
        state = state,
        unbookmarkedIds = unbookmarkedIds,
        onBookmarkClick = { recipe -> viewModel.onBookmarkClick(recipe) },
    )
}

// 화면만 그리는 Screen (상태 없음)
@Composable
fun SavedRecipesScreen(
    state: SavedRecipesState,
    unbookmarkedIds: Set<Long>,
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

        when (state) {
            SavedRecipesState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.primary100)
            }

            is SavedRecipesState.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = state.errorMessage,
                    color = AppColors.gray3,
                    style = AppTextStyles.normalTextRegular,
                )
            }

            is SavedRecipesState.Success -> SavedRecipeList(
                recipes = state.recipes,
                unbookmarkedIds = unbookmarkedIds,
                onBookmarkClick = onBookmarkClick,
            )
        }
    }
}

@Composable
private fun SavedRecipeList(
    recipes: List<Recipe>,
    unbookmarkedIds: Set<Long>,
    onBookmarkClick: (Recipe) -> Unit,
) {
    // 저장된 레시피 목록
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 30.dp,
            vertical = 20.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = recipes,
            key = { it.id } // 동일한 key면 skip
        ) { recipe ->
            SavedRecipe(
                recipe = recipe,
                isBookmarked = recipe.id !in unbookmarkedIds,
                onBookmarkClick = { onBookmarkClick(recipe) },
            )
        }
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun SavedRecipesScreenLoadingPreview() {
    SavedRecipesScreen(
        state = SavedRecipesState.Loading,
        unbookmarkedIds = emptySet(),
        onBookmarkClick = {},
    )
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun SavedRecipesScreenErrorPreview() {
    SavedRecipesScreen(
        state = SavedRecipesState.Error("서버 문제"),
        unbookmarkedIds = emptySet(),
        onBookmarkClick = {},
    )
}

@Preview(showBackground = true, name = "Success")
@Composable
private fun SavedRecipesScreenSuccessPreview() {
    val recipes = List(7) { index ->
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
    }
    var unbookmarkedIds by remember { mutableStateOf(setOf(2L, 5L)) }

    SavedRecipesScreen(
        state = SavedRecipesState.Success(recipes),
        unbookmarkedIds = unbookmarkedIds,
        onBookmarkClick = { recipe ->
            val ids = unbookmarkedIds
            unbookmarkedIds = if (recipe.id in ids) ids - recipe.id else ids + recipe.id
        },
    )
}
