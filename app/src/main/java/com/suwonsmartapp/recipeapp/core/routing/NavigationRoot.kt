package com.suwonsmartapp.recipeapp.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.suwonsmartapp.recipeapp.core.extension.replace
import com.suwonsmartapp.recipeapp.presentation.sign_in.SignInScreenRoot
import com.suwonsmartapp.recipeapp.presentation.sign_up.SignUpScreenRoot
import com.suwonsmartapp.recipeapp.presentation.splash.SplashScreenRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val topLevelBackStack = rememberNavBackStack(Route.Splash)

    NavDisplay(
//        modifier = modifier,
//        entryDecorators = listOf(
//            rememberSaveableStateHolderNavEntryDecorator(),
//            rememberViewModelStoreNavEntryDecorator(),
//        ),
        backStack = topLevelBackStack,
        onBack = { topLevelBackStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashScreenRoot(
                    onStartClick = { topLevelBackStack.replace(Route.SignIn) },
                )
            }
            entry<Route.SignIn> {
                SignInScreenRoot(
                    onSignInClick = { topLevelBackStack.replace(Route.Main) },
                    onSignUpClick = { topLevelBackStack.replace(Route.SignUp) },
                )
            }
            entry<Route.SignUp> {
                SignUpScreenRoot(
                    onSignUpClick = { topLevelBackStack.replace(Route.Main) },
                    onSignInClick = { topLevelBackStack.replace(Route.SignIn) },
                )
            }
        },
    )
}