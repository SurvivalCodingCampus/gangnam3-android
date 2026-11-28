package com.suwonsmartapp.recipeapp.presentation.component.navigation

import androidx.annotation.DrawableRes
import com.suwonsmartapp.recipeapp.R

// 하단 네비바 탭 (가운데 + 버튼은 탭이 아님)
enum class NavTab(
    val label: String,
    @param:DrawableRes val iconRes: Int,
) {
    HOME("Home", R.drawable.ic_nav_home),
    SAVED("Saved", R.drawable.ic_nav_bookmark),
    NOTIFICATION("Notification", R.drawable.ic_nav_notification),
    PROFILE("Profile", R.drawable.ic_nav_profile),
}
