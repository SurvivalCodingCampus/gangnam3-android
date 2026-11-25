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
import java.util.Locale

private val PrimaryGreen = Color(0xFF129575)
private val RatingBadgeBg = Color(0xFFFFE1B3)
private val StarOrange = Color(0xFFFF9C00)
private val GrayBorder = Color(0xFFD9D9D9)
private val GrayText = Color(0xFFA9A9A9)

/**
 * 검색 화면과 ViewModel을 연결하는 라우트 컴포저블입니다.
 *
 * 화면 상태는 ViewModel의 StateFlow에서 수명주기를 고려해 수집하고, 사용자의 검색어
 * 입력은 ViewModel로 전달합니다. 뒤로 가기와 필터 열기는 화면 밖에서 처리하므로
 * 이 함수는 내비게이션이나 바텀시트의 표시 상태를 직접 소유하지 않습니다.
 *
 * @param viewModel 검색어와 검색 결과 상태를 관리하는 화면 모델입니다.
 * @param onBackClick 호출 측에서 검색 화면을 닫도록 알리는 콜백입니다.
 * @param onFilterClick 호출 측에서 필터 바텀시트를 열도록 알리는 콜백입니다.
 * @param modifier 검색 화면의 바깥 배치를 조정하는 수정자입니다.
 */
@Composable
fun SearchRecipesRoute(
    viewModel: SearchRecipesViewModel,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 화면이 백그라운드로 가면 불필요한 상태 수집을 멈추고, 다시 활성화되면 최신 상태를 받습니다.
    val state by viewModel.state.collectAsStateWithLifecycle()

    // 실제 UI는 ViewModel에 직접 의존하지 않도록 상태와 이벤트 콜백만 전달합니다.
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
    // 검색어가 비어 있지 않으면 최근 검색 안내 대신 결과 제목과 결과 수를 보여줍니다.
    val hasQuery = state.query.isNotBlank()

    // 이 컴포저블은 전달받은 상태를 표시하는 역할을 맡습니다.
    // 검색 결과 계산과 상태 갱신은 상위 ViewModel에 위임하고, 화면 이동 이벤트는 콜백으로 전달합니다.
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // 뒤로 가기 이벤트를 부모에 전달하는 버튼과 화면 제목을 배치합니다.
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

        // 입력값은 외부 상태인 state.query에서 읽고, 변경분은 onQueryChange로 상위에 전달합니다.
        // 필터 버튼은 바텀시트 자체를 만들지 않고 onFilterClick 이벤트만 발생시킵니다.
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

        // 검색어 입력 여부에 따라 결과 섹션 제목을 바꾸며, 검색 중일 때만 결과 개수를 표시합니다.
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

        // 로딩 중에는 진행 표시를, 로딩이 끝나면 필터링된 레시피를 2열 그리드로 보여줍니다.
        // 각 항목의 안정적인 고유 키로 recipe.id를 사용해 목록 갱신 시 항목 상태를 보존합니다.
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
    // 카드 한 장 안에 이미지, 어두운 그라데이션, 별점, 제목과 작성자 정보를 겹쳐 표시합니다.
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
                // Coil이 이미지 URL을 비동기로 불러오며, 로딩 또는 실패 시 기본 갤러리 아이콘을 표시합니다.
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_menu_gallery),
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    // 사진 위의 글자가 밝은 이미지에서도 읽히도록 아래쪽을 어둡게 덮습니다.
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

                // 우측 상단 별점 배지로 카드 이미지와 구분되는 점수 정보를 제공합니다.
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
                            text = String.format(Locale.US, "%.1f", recipe.rating),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF121212)
                        )
                    }
                }

                // 제목은 최대 두 줄, 작성자는 한 줄로 제한해 카드 높이를 일정하게 유지합니다.
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
    // 미리보기 전용 샘플 데이터입니다. 실제 화면에서는 ViewModel이 저장소에서 레시피를 제공합니다.
    val sampleRecipes = listOf(
        Recipe(1, "Traditional spare ribs baked", "Chef John", "20 min", 4.0, ""),
        Recipe(2, "Lamb chops with fruity couscous and mint", "Spicy Nelly", "20 min", 4.0, ""),
        Recipe(3, "spice roasted chicken with flavored rice", "Mark Kelvin", "20 min", 4.0, ""),
        Recipe(4, "Chinese style Egg fried rice with sliced pork", "laura wilson", "20 min", 4.0, "")
    )
    // 미리보기는 상태와 콜백을 직접 전달하므로 ViewModel이나 실제 내비게이션이 필요하지 않습니다.
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
