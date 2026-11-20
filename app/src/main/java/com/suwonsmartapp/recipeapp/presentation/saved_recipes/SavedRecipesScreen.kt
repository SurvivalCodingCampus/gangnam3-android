package com.suwonsmartapp.recipeapp.presentation.saved_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suwonsmartapp.recipeapp.presentation.component.cards.RecipeCard
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedRecipesViewModel = viewModel(
        factory = SavedRecipesViewModel.Factory,
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()  // 기기 시스템 영역 여백 먼저 확보
            .padding(
                // 디자인 여백 적용
                vertical = 10.dp,
                horizontal = 30.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.CenterVertically,
        )
    ) {
        Text(
            text = "Saved recipes",
            color = AppColors.black2,
            style = AppTextStyles.mediumTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )

        if (state.isLoading) {
            // 뺑글
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 20.dp), // 내부 콘텐츠 가장 아래에 스크롤 가능한 여백 주기
            ) {
                items(
                    items = state.recipes,
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
}

@Preview(showBackground = true)
@Composable
private fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}