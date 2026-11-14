package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.RatingFilter
import com.suwonsmartapp.recipeapp.data.RecipeCategory
import com.suwonsmartapp.recipeapp.data.TimeFilter

/** 명세 §3.5 상세 검색/필터 시트 — 시간(라디오)/평점(배지)/카테고리(멀티 칩)/Apply. */
@Composable
fun SearchFilterSheet(
    state: FilterState,
    onApply: (FilterState) -> Unit,
    onDismiss: () -> Unit
) {
    var time by remember(state) { mutableStateOf(state.time) }
    var rating by remember(state) { mutableStateOf(state.rating) }
    var categories by remember(state) { mutableStateOf(state.categories) }

    Surface(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedTopCorners,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        Column(Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.filter_title), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text(stringResource(R.string.filter_close)) }
                }
                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.filter_time), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TimeFilter.entries.forEach { option ->
                        Row(
                            modifier = Modifier.clickable { time = option }.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = option == time, onClick = { time = option })
                            Text(option.label, fontSize = 14.sp)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.filter_rate), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RatingFilter.entries.filter { it != RatingFilter.Any }.forEach { option ->
                        FilterChip(
                            selected = option == rating,
                            onClick = { rating = option },
                            label = { Text(option.label, fontSize = 13.sp) }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.filter_category), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RecipeCategory.entries.filter { it != RecipeCategory.All }.forEach { category ->
                        FilterChip(
                            selected = categories.contains(category),
                            onClick = {
                                categories = if (categories.contains(category)) {
                                    categories - category
                                } else {
                                    categories + category
                                }
                            },
                            label = { Text(category.label, fontSize = 13.sp) }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { onApply(FilterState(time = time, rating = rating, categories = categories)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.filter_apply), fontSize = 15.sp)
                }
            }
        }
    }
private val RoundedTopCorners: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
