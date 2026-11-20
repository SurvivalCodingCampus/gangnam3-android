package com.suwonsmartapp.recipeapp.presentation.component.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors

// res/drawable 의 아이콘은 색이 이미 입혀진 PNG 이다
// isActiveIcon : 선택된 상태의 색으로 그려진 아이콘인지 여부
enum class BottomNavigationItem(
    val label: String,
    @DrawableRes val iconRes: Int,
    val isActiveIcon: Boolean = false,
) {
    HOME("Home", R.drawable.home),
    SAVED_RECIPES("Saved recipes", R.drawable.active, isActiveIcon = true),
    NOTIFICATIONS("Notifications", R.drawable.notification),
    PROFILE("Profile", R.drawable.profile),
}

@Composable
fun BottomNavigationBar(
    selectedItem: BottomNavigationItem,
    onItemClick: (BottomNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(color = AppColors.white)
            .padding(horizontal = 10.dp)
            .selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomNavigationItem.entries.forEach { item ->
            val isSelected = item == selectedItem

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .selectable(
                        selected = isSelected,
                        role = Role.Tab,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onItemClick(item) },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                // 아이콘 자체 색이 현재 상태와 같으면 그대로, 다르면 색을 입힌다
                val tint = when {
                    isSelected == item.isActiveIcon -> Color.Unspecified
                    isSelected -> AppColors.primary100
                    else -> AppColors.gray4
                }

                Icon(
                    painter = painterResource(item.iconRes),
                    contentDescription = item.label,
                    tint = tint,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    var selectedItem by remember { mutableStateOf(BottomNavigationItem.SAVED_RECIPES) }

    BottomNavigationBar(
        selectedItem = selectedItem,
        onItemClick = { selectedItem = it },
    )
}
