package com.suwonsmartapp.recipeapp.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.suwonsmartapp.recipeapp.presentation.main.MainScreen
import com.suwonsmartapp.recipeapp.presentation.sign_in.SignInScreen
import com.suwonsmartapp.recipeapp.presentation.sign_up.SignUpScreen
import com.suwonsmartapp.recipeapp.presentation.splash.SplashScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val topLevelBackStack = rememberNavBackStack(Route.Splash)

    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        backStack = topLevelBackStack,
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashScreen(
                    onStartCookingClick = { topLevelBackStack.replace(Route.SignIn) },
                )
            }
            entry<Route.SignIn> {
                SignInScreen(
                    onSignInClick = {
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.Main)
                    },
                    onSignUpClick = { topLevelBackStack.replace(Route.SignUp) },
                )
            }
            entry<Route.SignUp> {
                SignUpScreen(
                    onSignInClick = { topLevelBackStack.replace(Route.SignIn) },
                )
            }
            entry<Route.Main> { MainScreen() }
        },
    )
}

fun NavBackStack<NavKey>.replace(route: NavKey) {
    this[lastIndex] = route
}
