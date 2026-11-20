package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.RecipeCategory
import com.suwonsmartapp.recipeapp.data.model.UserSummary
import com.suwonsmartapp.recipeapp.presentation.component.cards.RecipeCard
import com.suwonsmartapp.recipeapp.presentation.component.navigation.BottomNavigationBar
import com.suwonsmartapp.recipeapp.presentation.component.navigation.BottomNavigationItem
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

// ViewModel 을 주입받아 상태를 관찰하는 화면
@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
    onNavigationItemClick: (BottomNavigationItem) -> Unit = {},
    viewModel: SavedRecipesViewModel = viewModel(factory = SavedRecipesViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SavedRecipesScreen(
        uiState = uiState,
        onBookmarkClick = viewModel::removeRecipe,
        onNavigationItemClick = onNavigationItemClick,
        modifier = modifier,
    )
}

// 상태만 받아서 그리는 화면 (Preview 가능)
@Composable
fun SavedRecipesScreen(
    uiState: SavedRecipesUiState,
    onBookmarkClick: (recipeId: String) -> Unit,
    modifier: Modifier = Modifier,
    onNavigationItemClick: (BottomNavigationItem) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.white)
            // 상단 상태바 영역만 제외하고, 나머지 공간 전체를 사용한다
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        // 디자인 : 제목은 화면 맨 위에서 54 아래, 가로 중앙 (129 x 27)
        // 상태바 영역은 이미 제외했으므로, 54 에서 상태바 높이를 뺀 만큼만 띄운다
        val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        val titleTopPadding = (54.dp - statusBarHeight).coerceAtLeast(10.dp)

        Text(
            text = "Saved recipes",
            color = AppColors.black1,
            style = TextStyles.mediumTextBold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = titleTopPadding, bottom = 10.dp)
                .height(27.dp),
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.primary100)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(
                    items = uiState.recipes,
                    key = { it.id },
                ) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        isBookmarked = true,
                        onBookmarkClick = { onBookmarkClick(recipe.id) },
                    )
                }
            }
        }

        BottomNavigationBar(
            selectedItem = BottomNavigationItem.SAVED_RECIPES,
            onItemClick = onNavigationItemClick,
            // 아이콘이 하단 시스템 내비게이션(제스처 바)과 겹치지 않도록 한다
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
        )
    }
}

// 디자인 시안과 같은 화면 크기 (375 x 812) 로 미리보기
@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SavedRecipesScreenPreview() {
    val recipes = List(5) { index ->
        Recipe(
            id = "$index",
            title = "Traditional spare ribs baked",
            imageUrl = "",
            videoUrl = "",
            chef = UserSummary(
                id = "chef",
                name = "Chef John",
                profileImageUrl = "",
                location = "",
            ),
            category = RecipeCategory.INDIAN,
            reviewCount = 0,
            cookingTimeMinutes = 20,
            createAt = 0L,
            rating = 4.0,
        )
    }

    SavedRecipesScreen(
        uiState = SavedRecipesUiState(recipes = recipes),
        onBookmarkClick = {},
    )
}
