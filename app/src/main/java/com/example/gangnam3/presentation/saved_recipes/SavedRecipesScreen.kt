package com.example.gangnam3.presentation.saved_recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessAlarm
import androidx.compose.material.icons.outlined.BookmarkBorder
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
import java.util.Locale

private val PrimaryGreen = Color(0xFF129575)
private val RatingBadgeBg = Color(0xFFFFE1B3)
private val StarOrange = Color(0xFFFF9C00)

/** ViewModel과 화면을 연결한다. 화면 자체는 ViewModel을 몰라 미리보기와 테스트가 쉽다. */
@Composable
fun SavedRecipesRoute(
    viewModel: SavedRecipesViewModel,
    modifier: Modifier = Modifier
) {
    // 화면이 보이지 않을 때는 수집을 멈춰 자원을 아낀다.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SavedRecipesScreen(
        uiState = uiState,
        onBookmarkClick = viewModel::onToggleBookmark,
        modifier = modifier
    )
}

/** 저장 목록 화면(Stateless). 상태에 따라 로딩·오류·빈 목록·목록 중 하나를 그린다. */
@Composable
fun SavedRecipesScreen(
    uiState: SavedRecipesUiState,
    onBookmarkClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // 상단 타이틀 ("Saved recipes")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Saved recipes",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF121212)
            )
        }

        when {
            // 위에서부터 먼저 맞는 상태 하나만 표시한다.
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryGreen)
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }

            // 불러오기는 성공했지만 저장한 레시피가 없는 경우
            uiState.recipes.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "저장한 레시피가 없습니다.", fontSize = 14.sp, color = Color.Gray)
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(
                        items = uiState.recipes,
                        key = { it.id }
                    ) { recipe ->
                        SavedRecipeCard(
                            recipe = recipe,
                            onBookmarkClick = { onBookmarkClick(recipe.id) }
                        )
                    }
                }
            }
        }
    }
}

/** 사진 위에 제목, 셰프, 조리 시간, 북마크 버튼을 겹쳐 보여주는 카드. */
@Composable
fun SavedRecipeCard(
    recipe: Recipe,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 음식 이미지 (Coil AsyncImage)
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_gallery),
                modifier = Modifier.fillMaxSize()
            )

            // 하단 텍스트 가독성을 위한 그라데이션 오버레이
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.72f)
                            )
                        )
                    )
            )

            // 우측 상단 별점 배지 (★ 4.0)
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                color = RatingBadgeBg
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = StarOrange,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = String.format(Locale.US, "%.1f", recipe.rating),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF121212)
                    )
                }
            }

            // 좌측 하단 제목 & 셰프명
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 12.dp, end = 120.dp)
            ) {
                Text(
                    text = recipe.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "By ${recipe.chefName}",
                    fontSize = 10.sp,
                    color = Color(0xFFD9D9D9),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // 우측 하단 조리 시간 & 북마크 버튼
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 12.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessAlarm,
                    contentDescription = null,
                    tint = Color(0xFFD9D9D9),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = recipe.cookingTime,
                    fontSize = 11.sp,
                    color = Color(0xFFD9D9D9)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Surface(
                    onClick = onBookmarkClick,
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (recipe.isBookmarked) {
                                Icons.Filled.Bookmark
                            } else {
                                Icons.Outlined.BookmarkBorder
                            },
                            contentDescription = "Bookmark",
                            tint = PrimaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedRecipesScreenPreview() {
    val sampleState = SavedRecipesUiState(
        recipes = listOf(
            Recipe(
                id = 1,
                title = "Traditional spare ribs baked",
                chefName = "Chef John",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "",
                isBookmarked = true
            ),
            Recipe(
                id = 2,
                title = "spice roasted chicken with flavored rice",
                chefName = "Mark Kelvin",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "",
                isBookmarked = true
            )
        )
    )

    SavedRecipesScreen(
        uiState = sampleState,
        onBookmarkClick = {}
    )
}