package com.suwonsmartapp.recipeapp.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.suwonsmartapp.recipeapp.core.routing.Route
import com.suwonsmartapp.recipeapp.core.routing.replace
import com.suwonsmartapp.recipeapp.presentation.home.HomeRoot
import com.suwonsmartapp.recipeapp.presentation.notification.NotificationScreen
import com.suwonsmartapp.recipeapp.presentation.profile.ProfileScreen
import com.suwonsmartapp.recipeapp.presentation.saved_recipes.SavedRecipesRoot
import com.suwonsmartapp.recipeapp.ui.AppColors

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val tabBackStack = rememberNavBackStack(Route.Home)

    Scaffold(
        modifier = modifier,
        containerColor = AppColors.white,
        bottomBar = {
            MainNavigationBar(
                currentRoute = tabBackStack.last(),
                onTabClick = { route -> tabBackStack.replace(route) },
            )
        },
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            backStack = tabBackStack,
            entryProvider = entryProvider {
                entry<Route.Home> { HomeRoot() }
                entry<Route.SavedRecipes> { SavedRecipesRoot() }
                entry<Route.Notification> { NotificationScreen() }
                entry<Route.Profile> { ProfileScreen() }
            },
            transitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
            popTransitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
