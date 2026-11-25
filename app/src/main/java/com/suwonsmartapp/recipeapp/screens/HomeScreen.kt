package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.data.RecipeCategory
import com.suwonsmartapp.recipeapp.data.formatRating
import com.suwonsmartapp.recipeapp.data.matches
import com.suwonsmartapp.recipeapp.data.featuredRecipeIds
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipeStar

/**
 * 명세 §3.2 메인 홈 — 인사말/프로필, 검색바, 카테고리 칩, Featured(행 카드),
 * New Recipes(2열 그리드 상당), 하단 탭은 AppRoot 담당.
 */
@Composable
fun HomeScreen(
    recipes: List<Recipe>,
    query: String,
    onQueryChange: (String) -> Unit,
    filter: FilterState,
    onFilterChange: (FilterState) -> Unit,
    onOpenFilter: () -> Unit,
    bookmarks: List<String>,
    onToggleBookmark: (String) -> Unit,
    onOpenRecipe: (String) -> Unit,
    onOpenProfile: () -> Unit
) {
    // 검색어나 필터가 하나라도 있으면 추천 목록 대신 검색 결과를 보여준다.
    val filtering = query.isNotBlank() || filter != FilterState()
    // 입력이 바뀔 때만 다시 계산한다.
    val results = remember(recipes, query, filter) {
        recipes.filter { it.matches(query, filter) }
    }
    // 데모 큐레이션 id 우선, 없으면 평점 상위 2개. New는 isNew 플래그만 사용.
    val featured = remember(recipes) {
        recipes.filter { it.id in featuredRecipeIds }
            .ifEmpty { recipes.sortedByDescending { it.rating }.take(2) }
    }
    val newRecipes = remember(recipes) { recipes.filter { it.isNew } }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            val profileLabel = stringResource(R.string.open_profile)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.home_hello, "Alex"),
                            modifier = Modifier.weight(1f, fill = false).semantics { heading() },
                            fontSize = 22.sp,
                            lineHeight = 28.sp, // 기본 bodyLarge lineHeight(24sp)보다 크게 — 2줄 겹침 방지
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        // 인사 이모지는 장식 — TalkBack 낭독 제외.
                        Text("👋", modifier = Modifier.clearAndSetSemantics { }, fontSize = 22.sp)
                    }
                    Text(
                        stringResource(R.string.home_prompt),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp) // 터치 타깃 48dp — 시각 원은 내부 40dp 유지
                        .clickable(onClickLabel = profileLabel, onClick = onOpenProfile)
                        .semantics { contentDescription = profileLabel },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "A",
                            modifier = Modifier.clearAndSetSemantics { },
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        item { HomeSearchBar(query = query, onQueryChange = onQueryChange, onFilter = onOpenFilter) }
        item { HomeCategoryChips(filter = filter, onSelect = { category -> onFilterChange(filter.withCategory(category)) }) }

        if (filtering) {
            item {
                Text(
                    stringResource(R.string.home_results),
                    modifier = Modifier.semantics { heading() },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            // key를 지정해 목록이 바뀌어도 각 행의 상태가 올바른 레시피를 따라가게 한다.
            items(results, key = { it.id }) { recipe ->
                RecipeRow(recipe = recipe, bookmarks = bookmarks, onToggleBookmark = onToggleBookmark, onOpen = { onOpenRecipe(recipe.id) })
            }
            if (results.isEmpty()) {
                item {
                    Column(Modifier.fillMaxWidth().padding(vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.search_empty), style = MaterialTheme.typography.titleMedium)
                        Text(stringResource(R.string.search_empty_hint), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            item {
                Text(
                    stringResource(R.string.home_featured),
                    modifier = Modifier.semantics { heading() },
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(featured, key = { it.id }) { recipe ->
                RecipeRow(recipe = recipe, bookmarks = bookmarks, onToggleBookmark = onToggleBookmark, onOpen = { onOpenRecipe(recipe.id) })
            }
            if (newRecipes.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.home_new),
                        modifier = Modifier.semantics { heading() },
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                // LazyColumn 안에 세로 스크롤 그리드를 중첩할 수 없어 2개씩 한 행으로 묶는다.
                items(newRecipes.chunked(2)) { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (recipe in pair) {
                            RecipeGridCard(
                                recipe = recipe,
                                modifier = Modifier.weight(1f),
                                onOpen = { onOpenRecipe(recipe.id) }
                            )
                        }
                        // 홀수 개일 때 마지막 카드가 가로 전체로 늘어나지 않도록 빈칸을 채운다.
                        if (pair.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/** 홈 칩은 단일 선택이다. All은 카테고리 조건을 비운다. */
private fun FilterState.withCategory(category: RecipeCategory) = copy(
    categories = when (category) {
        RecipeCategory.All -> emptySet()
        else -> setOf(category)
    }
)

/** 검색 입력창과 필터 열기 버튼. */
@Composable
private fun HomeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilter: () -> Unit
) {
    val filterLabel = stringResource(R.string.open_filter)
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.home_search_hint), color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingIcon = { Text("🔍", modifier = Modifier.clearAndSetSemantics { }, fontSize = 18.sp) },
        trailingIcon = {
            IconButton(onClick = onFilter, modifier = Modifier.size(48.dp).semantics { contentDescription = filterLabel }) {
                Text("⚙", modifier = Modifier.clearAndSetSemantics { }, fontSize = 22.sp)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

/** 가로로 스크롤되는 카테고리 선택 칩. */
@Composable
private fun HomeCategoryChips(
    filter: FilterState,
    onSelect: (RecipeCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RecipeCategory.entries.forEach { category ->
            // All 칩은 선택된 카테고리가 하나도 없을 때 켜진 것으로 표시한다.
            val selected = if (category == RecipeCategory.All) {
                filter.categories.isEmpty()
            } else {
                filter.categories.contains(category)
            }
            FilterChip(
                selected = selected,
                onClick = { onSelect(category) },
                label = { Text(category.label, fontSize = 13.sp) }
            )
        }
    }
}

/** 가로 전체 폭 레시피 카드(추천·검색 결과 공용). */
@Composable
private fun RecipeRow(
    recipe: Recipe,
    bookmarks: List<String>,
    onToggleBookmark: (String) -> Unit,
    onOpen: () -> Unit
) {
    val openLabel = stringResource(R.string.open_recipe, recipe.title)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = openLabel, onClick = onOpen)
            .padding(vertical = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            RecipeMedia(
                emoji = recipe.emoji,
                imageUrl = recipe.thumbnailUrl,
                modifier = Modifier.fillMaxWidth().height(110.dp),
                rounded = 14.dp
            )
            HeartToggle(
                saved = recipe.id in bookmarks,
                onClick = { onToggleBookmark(recipe.id) },
                modifier = Modifier.align(Alignment.TopEnd).padding(6.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f).padding(end = 12.dp)
            ) {
                Text(recipe.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(recipe.chef, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("⭐️", modifier = Modifier.clearAndSetSemantics { }, fontSize = 14.sp, color = RecipeStar)
            Text(
                " ${formatRating(recipe.rating)} (${recipe.cookingMinutes} mins)",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** 2열로 배치하는 신규 레시피 카드. */
@Composable
private fun RecipeGridCard(recipe: Recipe, modifier: Modifier = Modifier, onOpen: () -> Unit) {
    val openLabel = stringResource(R.string.open_recipe, recipe.title)
    Column(modifier = modifier.clickable(onClickLabel = openLabel, onClick = onOpen)) {
        RecipeMedia(
            emoji = recipe.emoji,
            imageUrl = recipe.thumbnailUrl,
            modifier = Modifier.fillMaxWidth().height(120.dp),
            rounded = 16.dp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            recipe.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2
        )
    }
}

@Preview(name = "Home", showBackground = true)
@Composable
private fun HomePreview() {
    RecipeAppTheme {
        HomeScreen(
            recipes = sampleRecipes,
            query = "",
            onQueryChange = {},
            filter = FilterState(),
            onFilterChange = {},
            onOpenFilter = {},
            bookmarks = emptyList(),
            onToggleBookmark = {},
            onOpenRecipe = {},
            onOpenProfile = {}
        )
    }
}
