package com.suwonsmartapp.recipeapp.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.suwonsmartapp.recipeapp.core.routing.Route
import com.suwonsmartapp.recipeapp.core.routing.replace
import com.suwonsmartapp.recipeapp.presentation.home.HomeRoot
import com.suwonsmartapp.recipeapp.presentation.notification.NotificationScreen
import com.suwonsmartapp.recipeapp.presentation.profile.ProfileScreen
import com.suwonsmartapp.recipeapp.presentation.saved_recipes.SavedRecipesRoot

@Composable
fun MainRoot(modifier: Modifier = Modifier) {
    val tabBackStack = rememberNavBackStack(Route.Home)

    MainScreen(
        modifier = modifier,
        currentRoute = tabBackStack.last(),
        onTabClick = { route -> tabBackStack.replace(route) },
    ) { contentModifier ->
        NavDisplay(
            modifier = contentModifier,
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
