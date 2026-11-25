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

/**
 * 시간, 별점, 카테고리 조건을 고른 뒤 한 번에 적용하는 모달 바텀시트입니다.
 *
 * 선택 중인 값은 이 화면의 로컬 상태로 보관하고, 적용 버튼을 눌렀을 때만
 * [onApplyFilter]로 바깥 화면에 전달합니다. 따라서 사용자가 시트를 닫기만 하면
 * 아직 적용하지 않은 변경은 검색 결과에 반영되지 않습니다.
 *
 * @param onDismissRequest 바깥 영역 탭이나 적용 완료 등으로 시트를 닫을 때 호출합니다.
 * @param onApplyFilter 사용자가 적용 버튼을 눌렀을 때 확정된 필터 상태를 전달합니다.
 * @param modifier 바텀시트의 배치나 크기를 호출 측에서 조정할 때 사용합니다.
 * @param initialState 시트를 열 때 표시할 초기 선택값입니다. 값이 바뀌면 내부 선택도 다시 맞춥니다.
 * @param categories 카테고리 선택지입니다. 지정하지 않으면 시안의 고정 목록을 씁니다.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterSearchBottomSheet(
    onDismissRequest: () -> Unit,
    onApplyFilter: (FilterSearchState) -> Unit,
    modifier: Modifier = Modifier,
    initialState: FilterSearchState = FilterSearchState(),
    categories: List<String> = FilterSearchState.categoryOptions
) {
    // 시트에서 사용자가 조작 중인 임시 필터입니다. remember의 키로 초기 상태를 사용해
    // 호출 측에서 다른 필터를 넘기면 내부 선택값도 새 값으로 초기화합니다.
    var filterState by remember(initialState) { mutableStateOf(initialState) }

    // 일부만 펼쳐진 상태를 건너뛰어 시트를 열 때 필터 항목을 모두 볼 수 있게 합니다.
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
            categories = categories,
            onTimeSelect = { time ->
                filterState = filterState.copy(selectedTime = time)
            },
            onRateSelect = { rate ->
                // 별점은 단일 선택입니다. 이미 선택된 별점을 다시 누르면 선택을 해제하고,
                // 다른 별점을 누르면 해당 값으로 교체합니다.
                val nextRate = if (filterState.selectedRate == rate) null else rate
                filterState = filterState.copy(selectedRate = nextRate)
            },
            onCategorySelect = { category ->
                filterState = filterState.copy(selectedCategory = category)
            },
            onFilterClick = {
                // 현재 시트의 선택값을 검색 화면에 확정 전달한 다음 시트를 닫습니다.
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
    modifier: Modifier = Modifier,
    categories: List<String> = FilterSearchState.categoryOptions
) {
    // 세로로 항목을 쌓고, 시스템 내비게이션 영역과 겹치지 않도록 아래쪽 여백을 확보합니다.
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        // 시트의 목적을 나타내는 제목입니다. 가운데 정렬해 필터 구역과 시각적으로 구분합니다.
        Text(
            text = "Filter Search",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 정렬·시간 관련 옵션입니다. FlowRow를 사용해 가로 공간이 부족하면 다음 줄로 배치합니다.
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

        // 별점 조건입니다. 선택된 값은 한 개 또는 없음(null)이며, 별점 칩에 별 아이콘을 표시합니다.
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

        // 음식 분류 조건입니다. 항목 수나 화면 너비에 따라 칩이 여러 줄로 자연스럽게 감깁니다.
        FilterSectionTitle(title = "Category")
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            categories.forEach { category ->
                // 명세의 시각 표현에 맞춰 Dinner 항목에만 별 아이콘을 표시합니다.
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

        // 선택한 임시 조건을 호출 측에 전달하는 유일한 확정 동작입니다.
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
    // 각 필터 묶음의 제목을 동일한 서체와 색상으로 표시하는 재사용 컴포넌트입니다.
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
    // 선택 여부에 따라 배경과 글자색을 함께 바꿔 현재 선택 상태를 명확히 보여줍니다.
    val containerColor = if (selected) PrimaryGreen else Color.White
    val contentColor = if (selected) Color.White else PrimaryGreen
    val borderStroke = if (selected) null else BorderStroke(1.dp, PrimaryGreen)

    Surface(
        // Surface의 클릭 동작을 사용해 칩 전체 영역에서 선택 이벤트를 받을 수 있습니다.
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
                // 별은 보조 장식이므로 접근성 읽기 대상에서는 제외하고 텍스트 라벨에 의미를 둡니다.
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
    // 미리보기에서만 사용할 독립 상태입니다. 각 콜백이 실제 시트처럼 선택값을 갱신하게 합니다.
    var previewState by remember { mutableStateOf(FilterSearchState()) }

    FilterSearchSheetContent(
        filterState = previewState,
        onTimeSelect = { previewState = previewState.copy(selectedTime = it) },
        onRateSelect = { previewState = previewState.copy(selectedRate = it) },
        onCategorySelect = { previewState = previewState.copy(selectedCategory = it) },
        onFilterClick = {}
    )
}
