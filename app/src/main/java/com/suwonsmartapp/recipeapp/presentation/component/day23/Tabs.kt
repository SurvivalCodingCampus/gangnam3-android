package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors

@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    Tabs(
        labels = listOf("Ingredient", "Procedure"),
        selectedIndex = 1,
    ) { index ->
        println("$index 번 탭 클릭!!")
    }
}

@Composable
fun Tabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .width(375.dp)
            .height(58.dp)
            .background(color = AppColors.white),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(30.dp))

        labels.forEachIndexed { index, label ->
            // 첫 번째 탭 앞에는 간격을 넣지 않음
            if (index > 0) {
                Spacer(modifier = Modifier.width(15.dp))
            }

            Tab(
                label = label,
                isSelected = index == selectedIndex,
                onClick = { onValueChange(index) },
            )

        }

        Spacer(modifier = Modifier.width(30.dp))
    }
}
