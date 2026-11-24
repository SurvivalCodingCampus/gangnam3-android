package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.presentation.FilterButton
import com.suwonsmartapp.recipeapp.presentation.component.SmallButton
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSearchBottomSheet(
    onFilterClick: (FilterSearchState) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    initialState: FilterSearchState = FilterSearchState(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var filterState by remember { mutableStateOf(initialState) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppColors.white,
        dragHandle = null,
    ) {
        FilterSearchContent(
            state = filterState,
            onStateChange = { filterState = it },
            onFilterClick = {
                onFilterClick(filterState)
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
            },
        )
    }
}

@Composable
private fun FilterSearchContent(
    state: FilterSearchState,
    onStateChange: (FilterSearchState) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 31.dp,
                end = 36.dp,
                bottom = 22.dp,
                start = 30.dp,
            ),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.filter_search_title),
            style = AppTextStyles.smallTextBold,
            color = AppColors.black1,
            textAlign = TextAlign.Center,
        )

        FilterSection(title = stringResource(id = R.string.filter_search_time)) {
            FilterTime.entries.forEach { time ->
                FilterButton(
                    text = stringResource(id = time.label),
                    isSelected = state.time == time,
                    onClick = { onStateChange(state.copy(time = time)) },
                )
            }
        }

        FilterSection(title = stringResource(id = R.string.filter_search_rate)) {
            RATES.forEach { rate ->
                FilterButton(
                    text = rate.toString(),
                    isSelected = state.rate == rate,
                    onClick = {
                        onStateChange(state.copy(rate = if (state.rate == rate) null else rate))
                    },
                    icon = painterResource(id = R.drawable.star),
                )
            }
        }

        FilterSection(title = stringResource(id = R.string.filter_search_category)) {
            FilterCategory.entries.forEach { category ->
                FilterButton(
                    text = stringResource(id = category.label),
                    isSelected = state.category == category,
                    onClick = { onStateChange(state.copy(category = category)) },
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 30.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SmallButton(
                text = stringResource(id = R.string.filter_search_button),
                onClick = onFilterClick,
            )
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Spacer(modifier = Modifier.height(height = 20.dp))
    Text(
        text = title,
        style = AppTextStyles.smallTextBold,
        color = AppColors.black1,
    )
    Spacer(modifier = Modifier.height(height = 10.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp),
    ) {
        content()
    }
}

private val RATES = 5 downTo 1

@Preview(showBackground = true)
@Composable
private fun FilterSearchContentPreview() {
    var state by remember {
        mutableStateOf(
            FilterSearchState(
                time = FilterTime.NEWEST,
                rate = 4,
                category = FilterCategory.LOCAL_DISH,
            ),
        )
    }
    FilterSearchContent(
        state = state,
        onStateChange = { state = it },
        onFilterClick = {},
    )
}
