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
<<<<<<< HEAD
=======
import androidx.compose.foundation.layout.width
>>>>>>> recipe/student/01_박강원
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
<<<<<<< HEAD
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
=======
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.FilterChip
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
=======
import androidx.compose.ui.res.stringResource
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
import com.suwonsmartapp.recipeapp.data.featuredRecipeIds
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipeStar
=======
import com.suwonsmartapp.recipeapp.data.sampleFeatured
import com.suwonsmartapp.recipeapp.data.sampleNew
import com.suwonsmartapp.recipeapp.data.SampleRecipeRepository
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipeBorder
import com.suwonsmartapp.recipeapp.ui.theme.RecipeStar
import com.suwonsmartapp.recipeapp.ui.theme.RecipeSurfaceVariant
>>>>>>> recipe/student/01_박강원

/**
 * 명세 §3.2 메인 홈 — 인사말/프로필, 검색바, 카테고리 칩, Featured(행 카드),
 * New Recipes(2열 그리드 상당), 하단 탭은 AppRoot 담당.
 */
@Composable
fun HomeScreen(
<<<<<<< HEAD
    recipes: List<Recipe>,
=======
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
    val filtering = query.isNotBlank() || filter != FilterState()
    val results = remember(recipes, query, filter) {
        recipes.filter { it.matches(query, filter) }
    }
    // 데모 큐레이션 id 우선, 없으면 평점 상위 2개. New는 isNew 플래그만 사용.
    val featured = remember(recipes) {
        recipes.filter { it.id in featuredRecipeIds }
            .ifEmpty { recipes.sortedByDescending { it.rating }.take(2) }
    }
    val newRecipes = remember(recipes) { recipes.filter { it.isNew } }
=======
    val recipes = SampleRecipeRepository.recipes()
    val filtering = query.isNotBlank() || filter != FilterState()
    val results = remember(query, filter) {
        recipes.filter { it.matches(query, filter) }
    }
>>>>>>> recipe/student/01_박강원

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
<<<<<<< HEAD
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
=======
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(stringResource(R.string.home_hello, "Alex"), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
>>>>>>> recipe/student/01_박강원
                    Text(
                        stringResource(R.string.home_prompt),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
<<<<<<< HEAD
                Box(
                    modifier = Modifier
                        .size(48.dp) // 터치 타깃 48dp — 시각 원은 내부 40dp 유지
                        .clickable(onClickLabel = profileLabel, onClick = onOpenProfile)
                        .semantics { contentDescription = profileLabel },
=======
                Spacer(Modifier.width(12.dp)) // 기존 weight(1f): 현행 Compose에서 internal — 고정 갭으로 대체(기존 WIP 수정, 계획 문서 기록)
                Box(
                    modifier = Modifier
                        .size(48.dp) // 터치 타깃 48dp — 시각 원은 내부 40dp 유지
                        .clickable(onClickLabel = "프로필 열기", onClick = onOpenProfile),
>>>>>>> recipe/student/01_박강원
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
<<<<<<< HEAD
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "A",
                            modifier = Modifier.clearAndSetSemantics { },
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
=======
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    ) {
                        Text("A", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
                    modifier = Modifier.semantics { heading() },
=======
>>>>>>> recipe/student/01_박강원
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(results, key = { it.id }) { recipe ->
                RecipeRow(recipe = recipe, bookmarks = bookmarks, onToggleBookmark = onToggleBookmark, onOpen = { onOpenRecipe(recipe.id) })
            }
<<<<<<< HEAD
            if (results.isEmpty()) {
                item {
                    Column(Modifier.fillMaxWidth().padding(vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.search_empty), style = MaterialTheme.typography.titleMedium)
                        Text(stringResource(R.string.search_empty_hint), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
=======
>>>>>>> recipe/student/01_박강원
        } else {
            item {
                Text(
                    stringResource(R.string.home_featured),
<<<<<<< HEAD
                    modifier = Modifier.semantics { heading() },
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
=======
                    fontSize = 16.sp,
>>>>>>> recipe/student/01_박강원
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
<<<<<<< HEAD
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
                        if (pair.size == 1) Spacer(Modifier.weight(1f))
=======
            items(sampleFeatured, key = { it.id }) { recipe ->
                RecipeRow(recipe = recipe, bookmarks = bookmarks, onToggleBookmark = onToggleBookmark, onOpen = { onOpenRecipe(recipe.id) })
            }
            item {
                Text(
                    stringResource(R.string.home_new),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (recipe in sampleNew) {
                        RecipeGridCard(
                            recipe = recipe,
                            modifier = Modifier.width(150.dp) // 기존 weight(1f) 대체(고정 폭 — 기존 WIP 수정, 계획 문서 기록)
                        )
>>>>>>> recipe/student/01_박강원
                    }
                }
            }
        }
    }
}

private fun FilterState.withCategory(category: RecipeCategory) = copy(
    categories = when (category) {
        RecipeCategory.All -> emptySet()
        else -> setOf(category)
    }
)

@Composable
private fun HomeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilter: () -> Unit
) {
<<<<<<< HEAD
    val filterLabel = stringResource(R.string.open_filter)
=======
>>>>>>> recipe/student/01_박강원
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.home_search_hint), color = MaterialTheme.colorScheme.onSurfaceVariant) },
<<<<<<< HEAD
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
=======
        leadingIcon = { Text("🔍", fontSize = 18.sp) },
        trailingIcon = {
            Text(
                "⚙️",
                fontSize = 18.sp,
                modifier = Modifier.clickable(onClickLabel = "필터 열기", onClick = onFilter).padding(horizontal = 4.dp)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(500.dp),
>>>>>>> recipe/student/01_박강원
        singleLine = true
    )
}

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

@Composable
private fun RecipeRow(
    recipe: Recipe,
    bookmarks: List<String>,
    onToggleBookmark: (String) -> Unit,
    onOpen: () -> Unit
) {
<<<<<<< HEAD
    val openLabel = stringResource(R.string.open_recipe, recipe.title)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = openLabel, onClick = onOpen)
=======
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "레시피 상세 열기: ${recipe.title}", onClick = onOpen)
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
                Modifier.weight(1f).padding(end = 12.dp)
            ) {
                Text(recipe.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(recipe.chef, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("⭐️", modifier = Modifier.clearAndSetSemantics { }, fontSize = 14.sp, color = RecipeStar)
=======
                Modifier.padding(end = 12.dp) // 기존 weight(1f) 대체(가변 갭 → 고정 갭 — 기존 WIP 수정, 계획 문서 기록)
            ) {
                Text(recipe.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(recipe.chef, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("⭐️", fontSize = 14.sp, color = RecipeStar)
>>>>>>> recipe/student/01_박강원
            Text(
                " ${formatRating(recipe.rating)} (${recipe.cookingMinutes} mins)",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
<<<<<<< HEAD
private fun RecipeGridCard(recipe: Recipe, modifier: Modifier = Modifier, onOpen: () -> Unit) {
    val openLabel = stringResource(R.string.open_recipe, recipe.title)
    Column(modifier = modifier.clickable(onClickLabel = openLabel, onClick = onOpen)) {
=======
private fun RecipeGridCard(recipe: Recipe, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
>>>>>>> recipe/student/01_박강원
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
<<<<<<< HEAD
            recipes = sampleRecipes,
=======
>>>>>>> recipe/student/01_박강원
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
