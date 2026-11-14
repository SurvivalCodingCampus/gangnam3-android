package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.data.formatRating
import com.suwonsmartapp.recipeapp.data.formatReviews
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipePrimary
import com.suwonsmartapp.recipeapp.ui.theme.RecipeRed
import com.suwonsmartapp.recipeapp.ui.theme.RecipeStar

/** 명세 §3.3 레시피 상세 — 히어로/평점/셰프(+팔로우 토글 데모)/재료·단계 탭/하단 CTA. */
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    saved: Boolean,
    onToggleBookmark: (String) -> Unit,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onStartCooking: () -> Unit = {}
) {
    var tab by remember { mutableStateOf(DetailTab.Ingredients) }
    // 재료 체크 상태(데모용 로컬 상태 — 실제 앱은 저장 연동)
    val checkedIngredients = remember { mutableStateListOf<String>() }
    var following by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) { Text(stringResource(R.string.detail_back)) }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onShare) { Text(stringResource(R.string.detail_share)) }
            HeartToggle(
                saved = saved,
                onClick = { onToggleBookmark(recipe.id) },
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        HorizontalDivider()
        RecipeMedia(
            emoji = recipe.emoji,
            imageUrl = recipe.thumbnailUrl,
            modifier = Modifier.fillMaxWidth().height(190.dp),
            emojiSize = 72.sp,
            tint = RecipePrimary.copy(alpha = 0.12f),
            rounded = 20.dp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            recipe.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("⭐️", fontSize = 14.sp, color = RecipeStar)
            Text(
                " ${formatRating(recipe.rating)} (${formatReviews(recipe.reviews)} Reviews)",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(recipe.chef, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.weight(1f))
            OutlinedButton(onClick = { following = !following }) {
                Text(if (following) stringResource(R.string.detail_following) else stringResource(R.string.detail_follow))
            }
        }
        Spacer(Modifier.height(16.dp))
        SegmentedTabs(
            labels = listOf(
                stringResource(R.string.detail_tab_ingredients, recipe.ingredients.size),
                stringResource(R.string.detail_tab_instructions)
            ),
            selectedIndex = tab.ordinal,
            onValueChange = { tab = DetailTab.entries[it] }
        )
        Spacer(Modifier.height(12.dp))
        when (tab) {
            DetailTab.Ingredients -> Column {
                if (recipe.ingredients.isEmpty()) {
                    Text(stringResource(R.string.detail_no_ingredients), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                recipe.ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = ingredient.name in checkedIngredients,
                            onCheckedChange = { checked ->
                                if (checked) checkedIngredients.add(ingredient.name)
                                else checkedIngredients.remove(ingredient.name)
                            }
                        )
                        Text(ingredient.name, fontSize = 15.sp, modifier = Modifier.weight(1f))
                        Text(
                            ingredient.amount,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            DetailTab.Instructions -> {
                if (recipe.steps.isEmpty()) {
                    Text(stringResource(R.string.detail_no_steps), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                recipe.steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "${index + 1}.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = RecipePrimary
                        )
                        Text(
                            step,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onStartCooking,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RecipePrimary,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.detail_cta), fontSize = 16.sp)
        }
    }
}

private enum class DetailTab { Ingredients, Instructions }

@Composable
private fun SegmentedTabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        labels.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .background(
                        color = if (selected) colorScheme.primaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onValueChange(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) colorScheme.onPrimaryContainer else colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(name = "Recipe Detail", showBackground = true)
@Composable
private fun RecipeDetailPreview() {
    RecipeAppTheme {
        RecipeDetailScreen(
            recipe = sampleRecipes.first { it.id == "r1" },
            saved = false,
            onToggleBookmark = {},
            onBack = {},
            onShare = {}
        )
    }
}
