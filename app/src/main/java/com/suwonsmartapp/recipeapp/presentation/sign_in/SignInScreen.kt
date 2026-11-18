package com.suwonsmartapp.recipeapp.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.InputField
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        modifier = TODO(),
        onForgotPasswordClick = TODO(),
        onSignInClick = TODO()
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier.padding(horizontal = 30.dp)
    ) {
        // 웰컴
        Column(
            modifier = modifier
                .padding(
                    top = 50.dp,
                    bottom = 57.dp
                )
        ) {
            Text(
                text = "Hello,",
                color = AppColors.black1,
                style = AppTextStyles.headerTextBold
            )

            Text(
                text = "Welcome Back!",
                color = AppColors.black1,
                style = AppTextStyles.largeTextRegular
            )
        }

        // 입력란
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            // Email
            InputField(
                label = "Email",
                placeholder = "Enter Email",
                value = ""
            ) { }

            // Password
            InputField(
                label = "Password",
                placeholder = "Enter Password",
                value = ""
            ) { }
        }

        // 비밀번호 찾기
        Text(
            modifier = modifier
                .padding(
                    top = 20.dp,
                    start = 10.dp,
                    bottom = 25.dp
                )
                .clickable(onClick = onForgotPasswordClick),
            text = "Forgot Password?",
            color = AppColors.secondary100,
            style = AppTextStyles.smallTextRegular
        )

        BigButton(
            text = "Sign In"
        ) { }
        // TODO: 사인버튼 다음 작업
    }
}