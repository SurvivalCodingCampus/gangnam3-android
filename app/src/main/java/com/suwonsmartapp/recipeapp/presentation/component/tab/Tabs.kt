package com.suwonsmartapp.recipeapp.presentation.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

@Composable
fun Tabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(space = 10.dp),
    // slot: 탭 안에 들어갈 내용 (기본은 글자만)
    tabContent: @Composable (label: String, contentColor: Color) -> Unit = { label, contentColor ->
        Text(
            text = label,
            style = AppTextStyles.smallerTextRegular,
            color = contentColor,
        )
    },
) {
    // 글자 크기만큼만 차지하고, 넘치면 다음 줄로
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        labels.forEachIndexed { index, label ->
            val isTapped = selectedIndex == index

            val (backgroundColor, contentColor, borderColor) = if (isTapped) {
                Triple(AppColors.primary100, AppColors.white, AppColors.primary100)
            } else {
                Triple(AppColors.white, AppColors.primary80, AppColors.primary80)
            }

            val borderRadius = 10.dp

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(borderRadius))
                    .background(color = backgroundColor)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(borderRadius),
                    )
                    .clickable { onValueChange(index) }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabContent(label, contentColor)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TabsPreview() {
    var index by remember { mutableIntStateOf(0) }

    Tabs(
        labels = listOf("All", "Newest", "Oldest", "Popularity"),
        selectedIndex = index,
        onValueChange = {
            index = it
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun RatingTabsPreview() {
    var index by remember { mutableIntStateOf(1) }

    Tabs(
        labels = listOf("5", "4", "3", "2", "1"),
        selectedIndex = index,
        onValueChange = { index = it },
        tabContent = { label, contentColor ->
            Text(
                text = label,
                style = AppTextStyles.smallerTextRegular,
                color = contentColor,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "별점 ${label}점",
                tint = contentColor,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun StarTabPreview() {
    Tabs(
        labels = listOf("Dinner"),
        selectedIndex = -1,
        onValueChange = {},
        tabContent = { label, contentColor ->
            Text(
                text = label,
                style = AppTextStyles.smallerTextRegular,
                color = contentColor,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = contentColor,
            )
        },
    )
}
