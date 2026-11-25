package com.example.gangnam3.presentation.search_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.gangnam3.data.model.Recipe

private val PrimaryGreen = Color(0xFF129575)
private val RatingBadgeBg = Color(0xFFFFE1B3)
private val StarOrange = Color(0xFFFF9C00)
private val GrayBorder = Color(0xFFD9D9D9)
private val GrayText = Color(0xFFA9A9A9)

@Composable
fun SearchRecipesRoute(
    viewModel: SearchRecipesViewModel,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchRecipesScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onBackClick = onBackClick,
        onFilterClick = onFilterClick,
        modifier = modifier
    )
}

@Composable
fun SearchRecipesScreen(
    state: SearchRecipesState,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasQuery = state.query.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // 1. 상단 앱바 영역 (뒤로가기 + 중앙 타이틀)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF121212)
                )
            }

            Text(
                text = "Search recipes",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF121212),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. 검색어 입력 필드 + 필터 버튼
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = "Search recipe",
                        color = GrayText,
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = GrayText,
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = GrayBorder,
                    focusedBorderColor = PrimaryGreen
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            )

            Surface(
                onClick = onFilterClick,
                shape = RoundedCornerShape(10.dp),
                color = PrimaryGreen,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = "Filter",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3. 섹션 헤더 ("Recent Search" 또는 "Search Result" + 결과 개수)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (hasQuery) "Search Result" else "Recent Search",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF121212)
            )

            if (hasQuery) {
                Text(
                    text = "${state.filteredRecipes.size} results",
                    fontSize = 12.sp,
                    color = GrayText
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. 레시피 2열 그리드 목록
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.filteredRecipes,
                    key = { it.id }
                ) { recipe ->
                    SearchGridRecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Composable
private fun SearchGridRecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(0.5.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_menu_gallery),
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.78f)
                                )
                            )
                        )
                )

                // 우측 상단 별점 배지
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = RatingBadgeBg,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = StarOrange,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = String.format("%.1f", recipe.rating),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF121212)
                        )
                    }
                }

                // 좌측 하단 레시피 제목 & 작성자
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Text(
                        text = recipe.title,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "By ${recipe.chefName}",
                        fontSize = 9.sp,
                        color = Color(0xFFD9D9D9),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchRecipesScreenPreview() {
    val sampleRecipes = listOf(
        Recipe(1, "Traditional spare ribs baked", "Chef John", "20 min", 4.0, ""),
        Recipe(2, "Lamb chops with fruity couscous and mint", "Spicy Nelly", "20 min", 4.0, ""),
        Recipe(3, "spice roasted chicken with flavored rice", "Mark Kelvin", "20 min", 4.0, ""),
        Recipe(4, "Chinese style Egg fried rice with sliced pork", "laura wilson", "20 min", 4.0, "")
    )
    SearchRecipesScreen(
        state = SearchRecipesState(
            allRecipes = sampleRecipes,
            filteredRecipes = sampleRecipes,
            query = ""
        ),
        onQueryChange = {},
        onBackClick = {},
        onFilterClick = {}
    )
}