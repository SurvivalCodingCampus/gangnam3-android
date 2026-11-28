package com.example.gangnam3.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.gangnam3.data.model.Recipe
import com.example.gangnam3.presentation.component.RecipeCategorySelector
import java.util.Locale

// 홈 화면 전용 색상
private val PrimaryGreen = Color(0xFF129575)
private val GrayBorder = Color(0xFFD9D9D9)
private val GrayText = Color(0xFFA9A9A9)
private val CardBgGray = Color(0xFFF1F1F1)
private val RatingBadgeBg = Color(0xFFFFE1B3)
private val StarOrange = Color(0xFFFF9C00)

/**
 * 홈 화면(Stateless). 전달받은 [state]만 그리고 사용자 동작은 콜백으로 알린다.
 * @param onSelectCategory 카테고리를 눌렀을 때의 콜백
 * @param onToggleBookmark 북마크를 눌렀을 때 레시피 id를 전달하는 콜백
 * @param onSearchClick 검색 영역이나 필터 버튼을 눌렀을 때의 콜백
 * @param onRecipeClick 카드를 눌렀을 때의 콜백
 */
@Composable
fun HomeScreen(
    state: HomeState,
    onSelectCategory: (String) -> Unit,
    onToggleBookmark: (Int) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRecipeClick: (Recipe) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            // 내용이 상태 표시줄과 겹치지 않도록 여백을 준다.
            .statusBarsPadding()
            // 가로 화면처럼 높이가 부족해도 카드까지 볼 수 있게 한다.
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // 1. 상단 인사말
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Hello ${state.userName}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF121212)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "What are you cooking today?",
                fontSize = 11.sp,
                color = GrayText
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. 검색바 + 필터 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                // 실제 입력칸이 아니라 검색 화면으로 이동하는 버튼이다.
                onClick = onSearchClick,
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorder),
                modifier = Modifier
                    // 필터 버튼을 뺀 나머지 가로 공간을 모두 차지한다.
                    .weight(1f)
                    .height(44.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = GrayText,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search recipe",
                        color = GrayText,
                        fontSize = 11.sp
                    )
                }
            }

            Surface(
                // 현재는 필터 버튼도 검색 화면으로 이동한다.
                onClick = onSearchClick,
                shape = RoundedCornerShape(10.dp),
                color = PrimaryGreen,
                modifier = Modifier.size(40.dp)
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

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Stateless 카테고리 선택기
        RecipeCategorySelector(
            categories = state.categories,
            // 선택값은 HomeState가 소유하고, 선택기는 표시와 클릭 전달만 맡는다.
            selectedCategory = state.selectedCategory,
            onSelectCategory = onSelectCategory
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 4. 선택된 카테고리에 해당하는 레시피 가로 카드 목록
        // 로딩 → 오류·빈 결과 → 목록 순으로 맞는 상태 하나만 그린다.
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // 카드 목록과 비슷한 높이를 확보해 상태가 바뀔 때 화면이 덜 흔들린다.
                    .height(230.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
        } else if (state.errorMessage != null || state.filteredRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    // 오류 문구가 있으면 우선 표시한다.
                    text = state.errorMessage ?: "해당 카테고리의 레시피가 없습니다.",
                    fontSize = 13.sp,
                    color = if (state.errorMessage != null) Color.Red else GrayText
                )
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = state.filteredRecipes,
                    // 고유 id를 key로 써서 북마크가 바뀌어도 항목을 정확히 구분한다.
                    key = { it.id }
                ) { recipe ->
                    HomeDishCard(
                        recipe = recipe,
                        onBookmarkClick = { onToggleBookmark(recipe.id) },
                        onClick = { onRecipeClick(recipe) }
                    )
                }
            }
        }
    }
}

/**
 * 레시피 카드. 회색 본문 위에 원형 사진과 별점을 겹쳐 그린다.
 * @param onBookmarkClick 북마크 버튼을 눌렀을 때의 콜백
 */
@Composable
fun HomeDishCard(
    recipe: Recipe,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    // Box는 자식을 겹쳐 놓는다. 아래에 적은 요소일수록 앞쪽에 그려진다.
    Box(
        modifier = modifier
            .width(150.dp)
            .height(231.dp)
            .clickable(onClick = onClick)
    ) {
        // 하단 회색 카드 본문
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CardBgGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // 위쪽 64dp는 겹쳐진 원형 사진이 차지하는 자리다.
                    .padding(top = 64.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipe.title,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF484848),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Time",
                            fontSize = 11.sp,
                            color = GrayText
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = recipe.cookingTime,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF484848)
                        )
                    }

                    Surface(
                        onClick = onBookmarkClick,
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                // 저장 여부에 따라 채워진 아이콘과 테두리 아이콘을 바꾼다.
                                imageVector = if (recipe.isBookmarked) {
                                    Icons.Filled.Bookmark
                                } else {
                                    Icons.Outlined.BookmarkBorder
                                },
                                contentDescription = if (recipe.isBookmarked) "Remove bookmark" else "Bookmark",
                                tint = if (recipe.isBookmarked) PrimaryGreen else GrayText,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }

        // 상단 돌출형 원형 음식 사진
        // 사진 주소가 없으면 그림 문자를 대신 보여준다.
        if (recipe.imageUrl.isBlank() && recipe.emoji.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .background(Color(0xFFE5F2EF)),
                contentAlignment = Alignment.Center
            ) {
                // 장식이므로 TalkBack에서는 읽지 않는다.
                Text(recipe.emoji, fontSize = 48.sp, modifier = Modifier.clearAndSetSemantics { })
            }
        } else {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                // 불러오는 중이거나 실패했을 때 대신 보여줄 이미지
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_gallery),
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.TopCenter)
                    // 크기를 정한 뒤 원형으로 잘라낸다. Modifier는 적은 순서대로 적용된다.
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
        }

        // 우측 상단 별점 배지 (★ 4.5)
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = RatingBadgeBg,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 28.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarOrange,
                    modifier = Modifier.size(11.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    // Locale을 고정해 기기 언어와 관계없이 4.5 형식으로 표시한다.
                    text = String.format(Locale.US, "%.1f", recipe.rating),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF121212)
                )
            }
        }
    }
}

/** 가짜 데이터로 화면을 확인하는 미리보기. ViewModel이 필요 없다. */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenFilteredPreview() {
    val sampleRecipes = listOf(
        Recipe(1, "Classic Greek Salad", "Chef John", "15 Mins", 4.5, "", "Italian"),
        Recipe(2, "Crunchy Nut Coleslaw", "Mark Kelvin", "10 Mins", 3.5, "", "Asian")
    )
    HomeScreen(
        state = HomeState(
            allRecipes = sampleRecipes,
            filteredRecipes = sampleRecipes,
            selectedCategory = "All"
        ),
        onSelectCategory = {},
        onToggleBookmark = {},
        onSearchClick = {}
    )
}
