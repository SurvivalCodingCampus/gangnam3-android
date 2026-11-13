package com.suwonsmartapp.recipeapp.presentation.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun TabsLeftPreview() {
    Tabs(
        labels = listOf("Video", "Ingrident"),
        selectedIndex = 0,
    ) {
        println("선택된 탭: $it")
    }
}

@Preview(showBackground = true)
@Composable
fun TabsRightPreview() {
    Tabs(
        labels = listOf("Video", "Ingrident"),
        selectedIndex = 1,
    ) {
        println("선택된 탭: $it")
    }
}

/**
 * Tabs.
 * Layout: each tab 150 x 33, radius 10, padding 8 (vertical), gap 10; 15 between tabs.
 * Selected: primary100 background, white text / Unselected: white background, primary100 text.
 */
@Composable
fun Tabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .width(315.dp)
            .height(33.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        // 왼쪽 탭
        if (selectedIndex == 0) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(33.dp)
                    .background(
                        color = AppColors.primary100,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable {
                        onValueChange(0)
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels[0],
                    color = AppColors.white,
                    style = TextStyles.smallerTextBold,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(33.dp)
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable {
                        onValueChange(0)
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels[0],
                    color = AppColors.primary100,
                    style = TextStyles.smallerTextBold,
                )
            }
        }

        // 오른쪽 탭
        if (selectedIndex == 1) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(33.dp)
                    .background(
                        color = AppColors.primary100,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable {
                        onValueChange(1)
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels[1],
                    color = AppColors.white,
                    style = TextStyles.smallerTextBold,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(33.dp)
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable {
                        onValueChange(1)
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels[1],
                    color = AppColors.primary100,
                    style = TextStyles.smallerTextBold,
                )
            }
        }
    }
}
