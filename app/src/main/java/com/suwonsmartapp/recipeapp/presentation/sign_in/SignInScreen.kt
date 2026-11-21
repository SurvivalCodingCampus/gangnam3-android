package com.suwonsmartapp.recipeapp.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.auth.SocialSignInSection
import com.suwonsmartapp.recipeapp.presentation.component.button.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.input.InputField
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Composable
fun SignInScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .safeDrawingPadding()
            .padding(
                horizontal = 30.dp,
            )
    ) {
        // 웰컴
        Column(
            modifier = Modifier.padding(
                top = 50.dp,
                bottom = 57.dp,
            )
        ) {
            Text(
                text = "Hello,",
                color = AppColors.black1,
                style = AppTextStyles.headerTextBold,
            )
            Text(
                text = "Welcome Back!",
                color = AppColors.label,
                style = AppTextStyles.largeTextRegular,
            )
        }

        // 입력란
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            InputField(
                label = "Email",
                placeholder = "Enter Email",
                value = email,
                onValueChange = onEmailChange,
            )
            InputField(
                label = "Password",
                placeholder = "Enter Password",
                value = password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = onPasswordChange,
            )
        }

        // 비밀번호 찾기
        Text(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 10.dp,
                    bottom = 25.dp,
                )
                .clickable(onClick = onForgotPasswordClick),
            text = "Forgot Password?",
            color = AppColors.secondary100,
            style = AppTextStyles.smallerTextRegular,
        )

        // 로그인 버튼
        BigButton(
            text = "Sign In",
            onClick = onSignInClick,
        )

        // Or Sign in With + 소셜 버튼
        SocialSignInSection(
            modifier = Modifier.padding(top = 20.dp),
            onGoogleClick = onGoogleClick,
            onFacebookClick = onFacebookClick,
        )

        // 회원가입
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 55.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Don’t have an account? ",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextBold,
            )
            Text(
                modifier = Modifier.clickable(onClick = onSignUpClick),
                text = "Sign up",
                color = AppColors.secondary100,
                style = AppTextStyles.smallerTextBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    SignInScreen(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onForgotPasswordClick = {},
        onSignInClick = {},
        onGoogleClick = {},
        onFacebookClick = {},
        onSignUpClick = {},
    )
}

