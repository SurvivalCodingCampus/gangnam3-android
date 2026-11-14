package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.data.formatRating
import com.suwonsmartapp.recipeapp.data.formatReviews
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
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
    val colorScheme = MaterialTheme.colorScheme

    Column(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BackArrow()
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.detail_back))
                }
            }
            Spacer(Modifier.weight(1f))
            HeartToggle(
                saved = saved,
                onClick = { onToggleBookmark(recipe.id) },
                modifier = Modifier.padding(end = 4.dp)
            )
            TextButton(onClick = onShare) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 공유 이모지는 장식 — 라벨만 낭독.
                    Text("📤", modifier = Modifier.clearAndSetSemantics { }, fontSize = 14.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.detail_share))
                }
            }
        }
        HorizontalDivider()
        // 본문이 길거나 글자가 커져도 CTA가 잘리지 않도록 스크롤 영역에 넣는다(헤더는 고정).
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            RecipeMedia(
                emoji = recipe.emoji,
                imageUrl = recipe.thumbnailUrl,
                modifier = Modifier.fillMaxWidth().height(190.dp),
                emojiSize = 72.sp,
                tint = colorScheme.primary.copy(alpha = 0.12f),
                rounded = 20.dp
            )
            Spacer(Modifier.height(12.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    recipe.title,
                    modifier = Modifier.semantics { heading() },
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "⭐️",
                        modifier = Modifier.clearAndSetSemantics { },
                        fontSize = 14.sp,
                        color = RecipeStar
                    )
                    Text(
                        " ${formatRating(recipe.rating)} (${formatReviews(recipe.reviews)} Reviews)",
                        fontSize = 14.sp,
                        color = colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 큰 글자에서도 Follow 버튼이 밀려나지 않도록 이름이 먼저 줄어든다.
                    Text(
                        recipe.chef,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp,
                        color = colorScheme.onBackground
                    )
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
                            Text(stringResource(R.string.detail_no_ingredients), color = colorScheme.onSurfaceVariant)
                        }
                        recipe.ingredients.forEach { ingredient ->
                            val checked = ingredient.name in checkedIngredients
                            // 체크박스+재료명+계량을 한 행/한 포커스로 묶는다.
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 48.dp)
                                    .toggleable(
                                        value = checked,
                                        role = Role.Checkbox,
                                        onValueChange = { isChecked ->
                                            if (isChecked) checkedIngredients.add(ingredient.name)
                                            else checkedIngredients.remove(ingredient.name)
                                        }
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = checked, onCheckedChange = null)
                                Text(
                                    ingredient.name,
                                    fontSize = 15.sp,
                                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                                )
                                Text(
                                    ingredient.amount,
                                    fontSize = 14.sp,
                                    color = colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    DetailTab.Instructions -> {
                        if (recipe.steps.isEmpty()) {
                            Text(stringResource(R.string.detail_no_steps), color = colorScheme.onSurfaceVariant)
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
                                    color = colorScheme.primary
                                )
                                Text(
                                    step,
                                    fontSize = 15.sp,
                                    color = colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onStartCooking,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Text(stringResource(R.string.detail_cta), fontSize = 16.sp)
                }
                Spacer(Modifier.height(16.dp))
            }
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
            .padding(4.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        labels.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    // 44dp는 최소값 — 글자 확대 시 2줄이 되어도 잘리지 않게 성장 허용.
                    .heightIn(min = 44.dp)
                    .background(
                        color = if (selected) colorScheme.primaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .selectable(
                        selected = selected,
                        role = Role.Tab,
                        onClick = { onValueChange(index) }
                    )
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center,
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
