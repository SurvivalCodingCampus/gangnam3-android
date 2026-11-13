package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.TextStyles

@Preview(showBackground = true)
@Composable
private fun TabsPreview() {
    Tabs(
        labels = listOf("Label", "Label"),
        selectedIndex = 0,
        onValueChange = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun TabsPreview2() {
    var index by remember { mutableIntStateOf(0) }

    Tabs(
        labels = listOf("Label", "Label"),
        selectedIndex = index,
        onValueChange = {
            index = it
        },
    )
}

@Composable
fun Tabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = AppColors.white)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 12.dp,
                bottom = 13.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 15.dp),
    ) {
        labels.forEachIndexed { index, label ->
            val isTapped = selectedIndex == index
            val backgroundColor = if (isTapped) AppColors.primary100 else AppColors.white
            val textColor = if (isTapped) AppColors.white else AppColors.primary80

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = backgroundColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onValueChange(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = TextStyles.smallerTextSemiBold,
                    color = textColor,
                )
            }
        }
    }
}