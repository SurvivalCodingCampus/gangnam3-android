package com.example.gangnam3.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 선택된 칩의 배경과 선택되지 않은 칩의 글자에 쓰는 강조색(같은 패키지의 PrimaryGreen과 이름이 겹치지 않게 구분)
private val CategoryGreen = Color(0xFF129575)

/**
 * 카테고리를 가로로 나열하는 선택기(Stateless).
 *
 * 선택값을 직접 보관하지 않고, 눌린 카테고리만 콜백으로 알린다.
 * @param categories 표시할 카테고리 이름 목록. key로도 쓰므로 중복되면 안 된다.
 * @param selectedCategory 현재 선택된 카테고리
 * @param onSelectCategory 칩을 눌렀을 때 호출되는 콜백
 */
@Composable
fun RecipeCategorySelector(
    categories: List<String>,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 화면에 보이는 칩만 그리는 가로 스크롤 목록
    LazyRow(
        modifier = modifier,
        // 목록 양 끝에 여백을 주되, 스크롤할 때는 칩이 화면 끝까지 지나간다.
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            items = categories,
            // 항목을 이름으로 구분해 목록이 바뀌어도 올바른 칩을 재사용한다.
            key = { it }
        ) { category ->
            // 별도 상태 없이 전달받은 값과 비교해 선택 여부를 계산한다.
            val isSelected = category == selectedCategory
            Surface(
                // 색상뿐 아니라 접근성 트리에도 선택 상태를 알려 TalkBack이 현재 카테고리를 읽게 한다.
                selected = isSelected,
                onClick = { onSelectCategory(category) },
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) CategoryGreen else Color.Transparent,
                contentColor = if (isSelected) Color.White else CategoryGreen,
                modifier = Modifier.height(31.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/** ViewModel 없이 모양만 확인하는 미리보기. */
@Preview(showBackground = true)
@Composable
fun RecipeCategorySelectorPreview() {
    RecipeCategorySelector(
        categories = listOf("All", "Indian", "Italian", "Asian", "Chinese"),
        selectedCategory = "All",
        onSelectCategory = {}
    )
}
