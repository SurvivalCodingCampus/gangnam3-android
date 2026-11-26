package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.suwonsmartapp.recipeapp.presentation.component.button.AppButton
import com.suwonsmartapp.recipeapp.presentation.component.button.ButtonSize
import com.suwonsmartapp.recipeapp.presentation.component.tab.Tabs
import com.suwonsmartapp.recipeapp.presentation.model.FilterCategory
import com.suwonsmartapp.recipeapp.presentation.model.FilterRate
import com.suwonsmartapp.recipeapp.presentation.model.FilterTime
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

private val times = FilterTime.entries
private val rates = FilterRate.entries
private val categories = FilterCategory.entries

// 내가 좋아하는 카테고리 (별 표시)
private val favoriteCategories = setOf(FilterCategory.DINNER)

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
        Tabs(
            items = times,
            selectedItem = filterState.time,
            onValueChange = { filterState = filterState.copy(time = it) },
            itemLabel = { it.label },
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Rate (글자 + 별 아이콘을 slot으로 넣음)
        SectionTitle(text = "Rate")
        Tabs(
            items = rates,
            selectedItem = filterState.rate, // null이면 선택 없음
            onValueChange = { filterState = filterState.copy(rate = it) },
            itemLabel = { it.value.toString() },
            horizontalArrangement = Arrangement.spacedBy(space = 15.dp),
            tabContent = { rate, contentColor ->
                Text(
                    text = rate.value.toString(),
                    color = contentColor,
                    style = AppTextStyles.smallerTextRegular,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = "별점 ${rate.value}점",
                    tint = contentColor,
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Category (넘치면 다음 줄로)
        SectionTitle(text = "Category")
        Tabs(
            items = categories,
            selectedItem = filterState.category,
            onValueChange = { filterState = filterState.copy(category = it) },
            itemLabel = { it.label },
            tabContent = { category, contentColor ->
                Text(
                    text = category.label,
                    color = contentColor,
                    style = AppTextStyles.smallerTextRegular,
                )
                // 좋아하는 카테고리면 별 추가
                if (category in favoriteCategories) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "좋아하는 카테고리",
                        tint = contentColor,
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Filter 버튼
        Box(
            modifier = Modifier
                .width(174.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            AppButton(
                text = "Filter",
                size = ButtonSize.Small,
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
            time = FilterTime.NEWEST,
            rate = FilterRate.FOUR,
            category = FilterCategory.LOCAL_DISH,
        ),
        onFilterClick = {},
    )
}
