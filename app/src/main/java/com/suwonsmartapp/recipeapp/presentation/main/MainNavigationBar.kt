package com.suwonsmartapp.recipeapp.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.core.routing.Route
import com.suwonsmartapp.recipeapp.ui.AppColors

private enum class MainTab(
    val route: Route,
    @param:DrawableRes val activeIcon: Int,
    @param:DrawableRes val inactiveIcon: Int,
    @param:StringRes val label: Int,
) {
    Home(
        Route.Home,
        R.drawable.ic_home_active,
        R.drawable.ic_home_inactive,
        R.string.main_tab_home,
    ),
    SavedRecipes(
        Route.SavedRecipes,
        R.drawable.ic_bookmark_active,
        R.drawable.ic_bookmark_inactive,
        R.string.main_tab_saved_recipes,
    ),
    Notification(
        Route.Notification,
        R.drawable.ic_notification_active,
        R.drawable.ic_notification_inactive,
        R.string.main_tab_notification,
    ),
    Profile(
        Route.Profile,
        R.drawable.ic_profile_active,
        R.drawable.ic_profile_inactive,
        R.string.main_tab_profile,
    ),
}

@Composable
fun MainNavigationBar(
    currentRoute: NavKey,
    onTabClick: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = AppColors.white,
    ) {
        MainTab.entries.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onTabClick(tab.route) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (selected) tab.activeIcon else tab.inactiveIcon,
                        ),
                        contentDescription = stringResource(id = tab.label),
                        tint = Color.Unspecified,
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = AppColors.transparent),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainNavigationBarPreview() {
    MainNavigationBar(
        currentRoute = Route.Home,
        onTabClick = {},
    )
}
