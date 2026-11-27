package com.suwonsmartapp.recipeapp.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.suwonsmartapp.recipeapp.presentation.sign_in.SignInScreen
import com.suwonsmartapp.recipeapp.presentation.sign_up.SignUpScreen
import com.suwonsmartapp.recipeapp.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Splash)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashScreen(
                    onStartCookingClick = {
                        // 스택에 쌓지 않고 replace
                        backStack[backStack.lastIndex] = Route.SignIn
                    }
                )
            }
            entry<Route.SignIn> {
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                SignInScreen(
                    email = email,
                    password = password,
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it },
                    onSignUpClick = {
                        // 스택에 쌓지 않고 replace
                        backStack[backStack.lastIndex] = Route.SignUp
                    }
                )
            }
            entry<Route.SignUp> {
                var name by rememberSaveable { mutableStateOf("") }
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var confirmPassword by rememberSaveable { mutableStateOf("") }
                var checked by rememberSaveable { mutableStateOf(false) }

                SignUpScreen(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    checked = checked,
                    onNameChange = { name = it },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it },
                    onConfirmPasswordChange = { confirmPassword = it },
                    onCheckedChange = { checked = it },
                    onSignInClick = {
                        // 스택에 쌓지 않고 replace
                        backStack[backStack.lastIndex] = Route.SignIn
                    }
                )
            }
        }
    )
}
