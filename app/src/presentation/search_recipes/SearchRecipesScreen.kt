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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import com.example.gangnam3.data.model.Recipe

// 검색 화면 전용 색상
private val PrimaryGreen = Color(0xFF129575)
private val RatingBadgeBg = Color(0xFFFFE1B3)
private val StarOrange = Color(0xFFFF9C00)
private val GrayBorder = Color(0xFFD9D9D9)
private val GrayText = Color(0xFFA9A9A9)

/**
 * 검색 화면(Stateless). 상태를 그리고 사용자 동작을 콜백으로 전달한다.
 * @param onQueryChange 검색어가 바뀔 때마다 호출된다.
 * @param onBackClick 뒤로 가기 버튼 콜백
 * @param onOpenFilterClick 필터 시트를 열어 달라는 요청. 시트는 Root가 띄운다.
 */
@Composable
fun SearchRecipesScreen(
    state: SearchRecipesState,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onOpenFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 공백만 입력한 경우는 검색어가 없는 것으로 본다.
    val hasQuery = state.query.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            // 상태 표시줄과 내비게이션 바에 내용이 가리지 않게 한다.
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 입력값을 직접 보관하지 않는다. state.query를 표시하고 변경은 콜백으로 알린다.
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
                    // 주의: 46dp 고정 높이에서는 입력 글자가 잘릴 수 있다.
                    .height(46.dp)
            )

            Surface(
                onClick = onOpenFilterClick,
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                // 검색어 유무에 따라 제목을 바꾼다.
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

        // 주의: 결과가 없을 때의 안내가 없어 빈 화면으로 보인다.
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
        } else {
            LazyVerticalGrid(
                // 두 열로 고정된 격자. 보이는 항목만 그린다.
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.filteredRecipes,
                    // 고유 id로 항목을 구분한다.
                    key = { it.id }
                ) { recipe ->
                    SearchGridRecipeCard(recipe = recipe)
                }
            }
        }
    }
}

/** 사진 위에 별점, 제목, 요리사 이름을 겹쳐 보여주는 정사각형 카드. */
@Composable
private fun SearchGridRecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            // 가로와 세로 비율을 1:1로 맞춘다.
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
                            // 아래로 갈수록 어두워져 흰 글자가 잘 보인다.
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.78f)
                                )
                            )
                        )
                )

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
                            // 주의: Locale이 없어 기기 언어에 따라 4,0처럼 보일 수 있다.
                            text = String.format("%.1f", recipe.rating),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF121212)
                        )
                    }
                }

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

/** 가짜 상태로 검색 결과 화면을 확인하는 미리보기. */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchRecipesScreenPreview() {
    val sampleRecipes = listOf(
        Recipe(1, "Traditional spare ribs baked", "Chef John", "20 min", 4.0, ""),
        Recipe(2, "Lamb chops with fruity couscous and mint", "Spicy Nelly", "20 min", 4.0, "")
    )
    SearchRecipesScreen(
        state = SearchRecipesState(
            allRecipes = sampleRecipes,
            filteredRecipes = sampleRecipes,
            query = "Jollof rice"
        ),
        onQueryChange = {},
        onBackClick = {},
        onOpenFilterClick = {}
    )
}