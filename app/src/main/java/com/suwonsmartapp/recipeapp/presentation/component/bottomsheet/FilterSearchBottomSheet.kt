package com.suwonsmartapp.recipeapp.presentation.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.button.FilterButton
import com.suwonsmartapp.recipeapp.presentation.component.button.RatingButton
import com.suwonsmartapp.recipeapp.presentation.component.button.SmallButton
import com.suwonsmartapp.recipeapp.presentation.search_recipes.FilterSearchState
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

private val times = listOf("All", "Newest", "Oldest", "Popularity")
private val categories = listOf(
    "All", "Cereal", "Vegetables", "Dinner", "Chinese",
    "Local Dish", "Fruit", "BreakFast", "Spanish", "Lunch",
)

@OptIn(ExperimentalMaterial3Api::class) // ModalBottomSheet가 실험적 API라 필요
@Composable
fun FilterSearchBottomSheet(
    initialState: FilterSearchState,
    onFilterClick: (FilterSearchState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    // 바깥 영역 클릭이나 아래로 내리면 onDismissRequest 호출
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        containerColor = AppColors.white,
        dragHandle = null,
    ) {
        FilterSearchContent(
            initialState = initialState,
            onFilterClick = onFilterClick,
        )
    }
}

@Composable
private fun FilterSearchContent(
    initialState: FilterSearchState,
    onFilterClick: (FilterSearchState) -> Unit,
) {
    // 시트 안에서 고르는 중인 값 (Filter 버튼을 눌러야 밖으로 전달)
    var filterState by remember { mutableStateOf(initialState) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.white)
            .padding(horizontal = 30.dp, vertical = 30.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Filter Search",
            color = AppColors.black1,
            style = AppTextStyles.smallTextBold,
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Time
        SectionTitle(text = "Time")
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            times.forEach { time ->
                FilterButton(
                    text = time,
                    isSelected = time == filterState.time,
                    onClick = { filterState = filterState.copy(time = time) },
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Rate
        SectionTitle(text = "Rate")
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (rate in 5 downTo 1) {
                RatingButton(
                    text = "$rate",
                    isSelected = rate == filterState.rate,
                    onClick = { filterState = filterState.copy(rate = rate) },
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Category (넘치면 다음 줄로)
        SectionTitle(text = "Category")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            categories.forEach { category ->
                FilterButton(
                    text = category,
                    isSelected = category == filterState.category,
                    onClick = { filterState = filterState.copy(category = category) },
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // Filter 버튼
        Box(
            modifier = Modifier
                .width(174.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            SmallButton(
                text = "Filter",
                onClick = { onFilterClick(filterState) },
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = AppColors.black1,
        style = AppTextStyles.smallTextBold,
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
private fun FilterSearchContentPreview() {
    FilterSearchContent(
        initialState = FilterSearchState(
            time = "Newest",
            rate = 4,
            category = "Local Dish",
        ),
        onFilterClick = {},
    )
}
