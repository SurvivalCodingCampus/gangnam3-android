package com.suwonsmartapp.recipeapp.presentation.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors

// 하단 네비바 (Figma: Nav Bar)
// 왼쪽 탭 2개 + 가운데 + 버튼 + 오른쪽 탭 2개
@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    onTabClick: (NavTab) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        // 흰 배경 (+ 버튼이 위로 튀어나오도록 14dp 내려서 그림)
        Row(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = NavBarShape)
                .background(color = AppColors.white, shape = NavBarShape)
                .navigationBarsPadding() // 시스템 하단바만큼 띄움
                .padding(horizontal = 40.dp, vertical = 24.dp),
        ) {
            NavItem(
                tab = NavTab.HOME,
                isSelected = selectedTab == NavTab.HOME,
                onClick = { onTabClick(NavTab.HOME) },
            )
            Spacer(modifier = Modifier.width(40.dp))
            NavItem(
                tab = NavTab.SAVED,
                isSelected = selectedTab == NavTab.SAVED,
                onClick = { onTabClick(NavTab.SAVED) },
            )

            // 가운데 + 버튼 자리
            Spacer(modifier = Modifier.weight(1f))

            NavItem(
                tab = NavTab.NOTIFICATION,
                isSelected = selectedTab == NavTab.NOTIFICATION,
                onClick = { onTabClick(NavTab.NOTIFICATION) },
            )
            Spacer(modifier = Modifier.width(40.dp))
            NavItem(
                tab = NavTab.PROFILE,
                isSelected = selectedTab == NavTab.PROFILE,
                onClick = { onTabClick(NavTab.PROFILE) },
            )
        }

        // 가운데 + 버튼 (Figma: Nav Bar Menu)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(48.dp)
                .clip(CircleShape)
                .background(AppColors.primary100)
                .clickable(onClick = onAddClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "추가",
                tint = AppColors.white,
            )
        }
    }
}

@Composable
private fun NavItem(
    tab: NavTab,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Icon(
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick),
        painter = painterResource(tab.iconRes),
        contentDescription = tab.label,
        tint = if (isSelected) AppColors.primary100 else AppColors.gray4,
    )
}

// 가운데가 움푹 파인 배경 모양 (Figma: Nav Bar/Bg)
// 숫자는 Figma 좌표(dp)를 가운데(centerX) 기준으로 옮긴 값
private object NavBarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val d = density.density // dp → px
        val centerX = size.width / 2

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(centerX - 54.875f * d, 0f)
            // 왼쪽 곡선 (위 → 아래)
            cubicTo(
                centerX - 45.128f * d, 0f,
                centerX - 36.871f * d, 7.144f * d,
                centerX - 32.855f * d, 16.025f * d,
            )
            cubicTo(
                centerX - 27.656f * d, 27.525f * d,
                centerX - 17.772f * d, 41f * d,
                centerX, 41f * d,
            )
            // 오른쪽 곡선 (아래 → 위)
            cubicTo(
                centerX + 17.772f * d, 41f * d,
                centerX + 27.656f * d, 27.525f * d,
                centerX + 32.855f * d, 16.025f * d,
            )
            cubicTo(
                centerX + 36.871f * d, 7.144f * d,
                centerX + 45.128f * d, 0f,
                centerX + 54.875f * d, 0f,
            )
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    var selectedTab by remember { mutableStateOf(NavTab.HOME) }

    BottomNavBar(
        selectedTab = selectedTab,
        onTabClick = { selectedTab = it },
        onAddClick = {},
    )
}
