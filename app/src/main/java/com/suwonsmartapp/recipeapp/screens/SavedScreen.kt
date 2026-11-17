package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
<<<<<<< HEAD
import androidx.compose.foundation.clickable
=======
>>>>>>> recipe/student/01_박강원
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
<<<<<<< HEAD
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
=======
import androidx.compose.ui.res.stringResource
>>>>>>> recipe/student/01_박강원
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.data.formatRating
<<<<<<< HEAD
import com.suwonsmartapp.recipeapp.data.sampleRecipes
=======
import com.suwonsmartapp.recipeapp.data.SampleRecipeRepository
>>>>>>> recipe/student/01_박강원
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipeStar

/** 명세 §3.4 북마크/저장 목록 — 썸네일/제목/평점/시간/셰프 + 하트 토글. */
@Composable
fun SavedScreen(
<<<<<<< HEAD
    recipes: List<Recipe>,
    bookmarks: List<String>,
    onToggleBookmark: (String) -> Unit,
    onClearAll: () -> Unit,
    onOpenRecipe: (String) -> Unit = {}
) {
    val saved = recipes.filter { it.id in bookmarks }
=======
    bookmarks: List<String>,
    onToggleBookmark: (String) -> Unit,
    onClearAll: () -> Unit
) {
    val saved = SampleRecipeRepository.recipes().filter { it.id in bookmarks }
>>>>>>> recipe/student/01_박강원
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.saved_title),
<<<<<<< HEAD
                // 버튼이 먼저 측정되도록 제목에 weight — 큰 글자에서 제목이 2줄로 늘어나도 버튼이 잘리지 않는다.
                modifier = Modifier.weight(1f).semantics { heading() },
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onClearAll, enabled = saved.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🗑️", modifier = Modifier.clearAndSetSemantics { }, fontSize = 14.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.saved_clear))
                }
            }
=======
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onClearAll) { Text(stringResource(R.string.saved_clear)) }
>>>>>>> recipe/student/01_박강원
        }
        if (saved.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
<<<<<<< HEAD
                    Text("🔖", fontSize = 32.sp, modifier = Modifier.clearAndSetSemantics { })
=======
                    Text("", fontSize = 32.sp)
>>>>>>> recipe/student/01_박강원
                    Text(
                        stringResource(R.string.saved_empty),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        stringResource(R.string.saved_empty_hint),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(saved, key = { it.id }) { recipe ->
                    SavedRow(
                        recipe = recipe,
                        saved = recipe.id in bookmarks,
<<<<<<< HEAD
                        onToggle = { onToggleBookmark(recipe.id) },
                        onOpen = { onOpenRecipe(recipe.id) }
=======
                        onToggle = { onToggleBookmark(recipe.id) }
>>>>>>> recipe/student/01_박강원
                    )
                }
            }
        }
    }
}

@Composable
<<<<<<< HEAD
private fun SavedRow(recipe: Recipe, saved: Boolean, onToggle: () -> Unit, onOpen: () -> Unit) {
    val openLabel = stringResource(R.string.open_recipe, recipe.title)
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClickLabel = openLabel, onClick = onOpen),
=======
private fun SavedRow(recipe: Recipe, saved: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
>>>>>>> recipe/student/01_박강원
        verticalAlignment = Alignment.CenterVertically
    ) {
        RecipeMedia(
            emoji = recipe.emoji,
            imageUrl = recipe.thumbnailUrl,
<<<<<<< HEAD
            modifier = Modifier.width(80.dp).height(80.dp),
=======
            modifier = Modifier.width(72.dp).height(72.dp),
>>>>>>> recipe/student/01_박강원
            emojiSize = 32.sp,
            rounded = 12.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(recipe.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Row(verticalAlignment = Alignment.CenterVertically) {
<<<<<<< HEAD
                Text("⭐️", modifier = Modifier.clearAndSetSemantics { }, fontSize = 13.sp, color = RecipeStar)
=======
                Text("⭐️", fontSize = 13.sp, color = RecipeStar)
>>>>>>> recipe/student/01_박강원
                Text(
                    " ${formatRating(recipe.rating)} · ${recipe.cookingMinutes} mins",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(recipe.chef, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        HeartToggle(saved = saved, onClick = onToggle, modifier = Modifier.padding(end = 8.dp))
    }
}

@Preview(name = "Saved (empty)", showBackground = true)
@Composable
private fun SavedPreview() {
    RecipeAppTheme {
        SavedScreen(
<<<<<<< HEAD
            recipes = sampleRecipes,
=======
>>>>>>> recipe/student/01_박강원
            bookmarks = emptyList(),
            onToggleBookmark = {},
            onClearAll = {}
        )
    }
}
