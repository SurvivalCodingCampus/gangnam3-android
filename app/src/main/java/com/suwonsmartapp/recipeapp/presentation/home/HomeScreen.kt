package com.suwonsmartapp.recipeapp.presentation.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.component.input.SearchField
import com.suwonsmartapp.recipeapp.presentation.component.recipe.DishCard
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 카드 높이 (Figma: Dishes 231) — 로딩/에러 때도 같은 자리를 차지하게
private val DishRowHeight = 231.dp

// ViewModel과 연결하는 Root (상태를 가진 쪽)
@Composable
fun HomeScreenRoot(
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        userName = "Jega", // TODO: 로그인 유저 정보가 생기면 교체
        onSearchClick = {},
        onFilterClick = {},
        onCategoryClick = { category -> viewModel.onCategoryClick(category) },
        onBookmarkClick = { recipe -> viewModel.onBookmarkClick(recipe.id) },
    )
}

// 화면만 그리는 Screen (상태 없음)
@Composable
fun HomeScreen(
    state: HomeUiState,
    userName: String,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    onCategoryClick: (HomeCategory) -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .safeDrawingPadding(),
    ) {
        // 헤더 (인사말 + 프로필)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 30.dp,
                    end = 30.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hello $userName",
                    color = AppColors.black1,
                    style = AppTextStyles.largeTextBold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "What are you cooking today?",
                    color = AppColors.gray3,
                    style = AppTextStyles.smallerTextRegular,
                )
            }
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppColors.secondary40),
                painter = painterResource(id = R.drawable.profile_avatar),
                contentDescription = "프로필",
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        // 검색창 + 필터 버튼
        Row(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 홈에서는 입력하지 않고 검색 화면으로 이동만 함
            Box(modifier = Modifier.weight(1f)) {
                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = {},
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = onSearchClick),
                )
            }
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
        Spacer(modifier = Modifier.height(15.dp))

        // 카테고리 탭 (가로 스크롤)
        CategoryTabs(
            selectedCategory = state.selectedCategory,
            onCategoryClick = onCategoryClick,
        )
        Spacer(modifier = Modifier.height(25.dp))

        // 요리 카드 목록
        when (val result = state.result) {
            HomeResult.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DishRowHeight),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.primary100)
            }

            is HomeResult.Error -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DishRowHeight),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = result.message,
                    color = AppColors.gray3,
                    style = AppTextStyles.normalTextRegular,
                )
            }

            is HomeResult.Success -> DishRow(
                recipes = result.recipes,
                bookmarkedIds = state.bookmarkedIds,
                onBookmarkClick = onBookmarkClick,
            )
        }
    }
}

@Composable
private fun CategoryTabs(
    selectedCategory: HomeCategory,
    onCategoryClick: (HomeCategory) -> Unit,
) {
    // 탭 사이 간격 없음, 양 끝만 30 여백
    // TODO: Tabs로 교체
    LazyRow(
        contentPadding = PaddingValues(horizontal = 30.dp),
    ) {
        items(HomeCategory.entries) { category ->
            val isSelected = category == selectedCategory
            val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white
            val contentColor = if (isSelected) AppColors.white else AppColors.primary80

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .clickable { onCategoryClick(category) }
                    .padding(
                        horizontal = 20.dp,
                        vertical = 7.dp,
                    ),
                text = category.label,
                color = contentColor,
                style = AppTextStyles.smallerTextBold,
            )
        }
    }
}

@Composable
private fun DishRow(
    recipes: List<Recipe>,
    bookmarkedIds: Set<Long>,
    onBookmarkClick: (Recipe) -> Unit,
) {
    if (recipes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(DishRowHeight),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "No recipes",
                color = AppColors.gray3,
                style = AppTextStyles.normalTextRegular,
            )
        }
        return
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(
            items = recipes,
            key = { it.id },
        ) { recipe ->
            DishCard(
                recipe = recipe,
                isBookmarked = recipe.id in bookmarkedIds,
                onBookmarkClick = { onBookmarkClick(recipe) },
            )
        }
    }
}

private val previewRecipes = listOf(
    "Classic Greek Salad" to 4.5,
    "Crunchy Nut Coleslaw" to 3.5,
    "Shrimp Chicken Andouille Sausage Jambalaya" to 3.0,
    "Barbecue Chicken Jollof Rice" to 4.5,
    "Portuguese Piri Piri Chicken" to 4.5,
).mapIndexed { index, (name, rating) ->
    Recipe(
        id = index.toLong() + 1,
        category = "Indian",
        name = name,
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "Chef John",
        time = if (index == 0) "15 Mins" else "10 Mins",
        rating = rating,
        ingredients = emptyList(),
    )
}

@Preview(showBackground = true, name = "Home")
@Composable
private fun HomeScreenPreview() {
    var state by remember {
        mutableStateOf(HomeUiState(result = HomeResult.Success(previewRecipes)))
    }

    HomeScreen(
        state = state,
        userName = "Jega",
        onSearchClick = {},
        onFilterClick = {},
        onCategoryClick = { state = state.copy(selectedCategory = it) },
        onBookmarkClick = { recipe ->
            val ids = state.bookmarkedIds
            state = state.copy(
                bookmarkedIds = if (recipe.id in ids) ids - recipe.id else ids + recipe.id,
            )
        },
    )
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun HomeScreenLoadingPreview() {
    HomeScreen(
        state = HomeUiState(result = HomeResult.Loading),
        userName = "Jega",
        onSearchClick = {},
        onFilterClick = {},
        onCategoryClick = {},
        onBookmarkClick = {},
    )
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun HomeScreenErrorPreview() {
    HomeScreen(
        state = HomeUiState(result = HomeResult.Error("서버 문제")),
        userName = "Jega",
        onSearchClick = {},
        onFilterClick = {},
        onCategoryClick = {},
        onBookmarkClick = {},
    )
}
