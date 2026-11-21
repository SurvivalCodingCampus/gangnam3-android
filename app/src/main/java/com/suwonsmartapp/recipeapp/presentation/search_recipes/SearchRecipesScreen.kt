package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.suwonsmartapp.recipeapp.presentation.component.input.SearchField
import com.suwonsmartapp.recipeapp.presentation.component.recipe.SearchCard
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// ViewModel과 연결하는 Root (상태를 가진 쪽)
@Composable
fun SearchRecipesScreenRoot(
    viewModel: SearchRecipesScreenViewModel = viewModel(factory = SearchRecipesScreenViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchRecipesScreen(
        state = state,
        onQueryChange = { query -> viewModel.onQueryChange(query) },
        onBackClick = {},
        onFilterClick = {},
    )
}

// 화면만 그리는 Screen (상태 없음)
@Composable
fun SearchRecipesScreen(
    state: SearchRecipesState,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // 검색어가 있으면 Search Result, 없으면 Recent Search
    val isSearching = !state.query.isNullOrBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .safeDrawingPadding()
            .padding(horizontal = 30.dp),
    ) {
        // 헤더 (뒤로가기 + 타이틀)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp)
                    .clickable(onClick = onBackClick),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                tint = AppColors.black1,
            )
            Text(
                text = "Search recipes",
                color = AppColors.label,
                style = AppTextStyles.mediumTextBold,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(17.dp))

        // 검색창 + 필터 버튼
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchField(
                modifier = Modifier.weight(1f),
                value = state.query ?: "",
                onValueChange = onQueryChange,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = AppColors.primary100,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable(onClick = onFilterClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Tune,
                    contentDescription = "필터",
                    tint = AppColors.white,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // 섹션 타이틀 (+ 검색 중이면 결과 개수)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = if (isSearching) "Search Result" else "Recent Search",
                color = AppColors.black1,
                style = AppTextStyles.normalTextBold,
            )
            if (isSearching) {
                Text(
                    text = "${state.recipes.size} results",
                    color = AppColors.gray3,
                    style = AppTextStyles.smallerTextRegular,
                )
            }
        }

        // 본문
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.primary100)
            }

            state.errorMessage != null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = state.errorMessage,
                    color = AppColors.gray3,
                    style = AppTextStyles.normalTextRegular,
                )
            }

            else -> RecipeGrid(recipes = state.recipes)
        }
    }
}

@Composable
private fun RecipeGrid(
    recipes: List<Recipe>,
) {
    // 2열 그리드 (칸 너비는 균등 분배, 간격 15)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(
            items = recipes,
            key = { it.id }, // 동일한 key면 skip
        ) { recipe ->
            SearchCard(recipe = recipe)
        }
    }
}

private val previewRecipes = List(6) { index ->
    Recipe(
        id = index.toLong() + 1,
        category = "Indian",
        name = if (index % 2 == 0) "Traditional spare ribs baked" else "Lamb chops with fruity couscous and mint",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = if (index % 2 == 0) "Chef John" else "Spicy Nelly",
        time = "20 min",
        rating = 4.0,
        ingredients = emptyList(),
    )
}

@Preview(showBackground = true, name = "Search")
@Composable
private fun SearchRecipesScreenPreview() {
    SearchRecipesScreen(
        state = SearchRecipesState(isLoading = false, recipes = previewRecipes),
        onQueryChange = {},
        onBackClick = {},
        onFilterClick = {},
    )
}

@Preview(showBackground = true, name = "Search Result")
@Composable
private fun SearchRecipesScreenResultPreview() {
    var query by remember { mutableStateOf("ribs") }

    SearchRecipesScreen(
        state = SearchRecipesState(
            isLoading = false,
            query = query,
            recipes = previewRecipes.filter { it.name.contains(query, ignoreCase = true) },
        ),
        onQueryChange = { query = it },
        onBackClick = {},
        onFilterClick = {},
    )
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun SearchRecipesScreenErrorPreview() {
    SearchRecipesScreen(
        state = SearchRecipesState(
            isLoading = false,
            errorMessage = "서버 문제",
            recipes = listOf()),
        onQueryChange = {},
        onBackClick = {},
        onFilterClick = {},
    )
}
