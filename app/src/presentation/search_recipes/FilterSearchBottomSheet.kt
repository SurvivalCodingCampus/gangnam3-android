package com.example.gangnam3.presentation.search_recipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryGreen = Color(0xFF129575)
private val TextDark = Color(0xFF121212)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterSearchBottomSheet(
    onDismissRequest: () -> Unit,
    onApplyFilter: (FilterSearchState) -> Unit,
    modifier: Modifier = Modifier,
    initialState: FilterSearchState = FilterSearchState()
) {
    // 바텀시트 내부에서 관리하는 FilterSearchState
    var filterState by remember(initialState) { mutableStateOf(initialState) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = modifier
    ) {
        FilterSearchSheetContent(
            filterState = filterState,
            onTimeSelect = { time ->
                filterState = filterState.copy(selectedTime = time)
            },
            onRateSelect = { rate ->
                // 이미 선택된 별점을 다시 누르면 해제하거나 유지할 수 있도록 처리
                val nextRate = if (filterState.selectedRate == rate) null else rate
                filterState = filterState.copy(selectedRate = nextRate)
            },
            onCategorySelect = { category ->
                filterState = filterState.copy(selectedCategory = category)
            },
            onFilterClick = {
                onApplyFilter(filterState)
                onDismissRequest()
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterSearchSheetContent(
    filterState: FilterSearchState,
    onTimeSelect: (String) -> Unit,
    onRateSelect: (Int) -> Unit,
    onCategorySelect: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        // 상단 타이틀 ("Filter Search")
        Text(
            text = "Filter Search",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 1. Time 필터 섹션
        FilterSectionTitle(title = "Time")
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterSearchState.timeOptions.forEach { option ->
                FilterChipButton(
                    text = option,
                    selected = filterState.selectedTime == option,
                    showStar = false,
                    onClick = { onTimeSelect(option) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Rate 필터 섹션
        FilterSectionTitle(title = "Rate")
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterSearchState.rateOptions.forEach { rate ->
                FilterChipButton(
                    text = "$rate",
                    selected = filterState.selectedRate == rate,
                    showStar = true,
                    onClick = { onRateSelect(rate) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Category 필터 섹션
        FilterSectionTitle(title = "Category")
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterSearchState.categoryOptions.forEach { category ->
                val isDinner = category == "Dinner"
                FilterChipButton(
                    text = category,
                    selected = filterState.selectedCategory == category,
                    showStar = isDinner,
                    onClick = { onCategorySelect(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 하단 적용 버튼 ("Filter")
        Button(
            onClick = onFilterClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(174.dp)
                .height(37.dp)
        ) {
            Text(
                text = "Filter",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun FilterSectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextDark,
        modifier = modifier
    )
}

@Composable
private fun FilterChipButton(
    text: String,
    selected: Boolean,
    showStar: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (selected) PrimaryGreen else Color.White
    val contentColor = if (selected) Color.White else PrimaryGreen
    val borderStroke = if (selected) null else BorderStroke(1.dp, PrimaryGreen)

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = containerColor,
        contentColor = contentColor,
        border = borderStroke,
        modifier = modifier.height(31.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = contentColor
            )
            if (showStar) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSearchSheetContentPreview() {
    var previewState by remember { mutableStateOf(FilterSearchState()) }

    FilterSearchSheetContent(
        filterState = previewState,
        onTimeSelect = { previewState = previewState.copy(selectedTime = it) },
        onRateSelect = { previewState = previewState.copy(selectedRate = it) },
        onCategorySelect = { previewState = previewState.copy(selectedCategory = it) },
        onFilterClick = {}
    )