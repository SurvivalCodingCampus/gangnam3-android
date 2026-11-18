package com.suwonsmartapp.recipeapp.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.InputField
import com.suwonsmartapp.recipeapp.presentation.component.SocialSignInSection
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isTermsAccepted by rememberSaveable { mutableStateOf(false) }

    SignUpScreen(
        name = name,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        isTermsAccepted = isTermsAccepted,
        onNameChange = { name = it },
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onTermsClick = { isTermsAccepted = !isTermsAccepted },
        onSignUpClick = {},
        onGoogleClick = {},
        onFacebookClick = {},
        onSignInClick = {},
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    isTermsAccepted: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTermsClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .padding(horizontal = 30.dp)
    ) {
        // 타이틀
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 20.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Create an account",
                color = AppColors.black1,
                style = AppTextStyles.largeTextBold,
            )
            Text(
                modifier = Modifier.width(195.dp),
                text = "Let’s help you set up your account, it won’t take long.",
                color = AppColors.label,
                style = AppTextStyles.smallerTextRegular,
            )
        }

        // 입력란
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InputField(
                label = "Name",
                placeholder = "Enter Name",
                value = name,
                onValueChange = onNameChange,
            )
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
                onValueChange = onPasswordChange,
            )
            InputField(
                label = "Confirm Password",
                placeholder = "Retype Password",
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
            )
        }

        // 약관 동의
        Row(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 10.dp,
                    bottom = 26.dp,
                )
                .clickable(onClick = onTermsClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TermsCheckBox(isChecked = isTermsAccepted)
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "Accept terms & Condition",
                color = AppColors.secondary100,
                style = AppTextStyles.smallerTextRegular,
            )
        }

        // 회원가입 버튼
        BigButton(
            text = "Sign Up",
            onClick = onSignUpClick,
        )

        // Or Sign in With + 소셜 버튼
        SocialSignInSection(
            modifier = Modifier.padding(top = 14.dp),
            onGoogleClick = onGoogleClick,
            onFacebookClick = onFacebookClick,
        )

        // 로그인으로 이동
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Already a member? ",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextBold,
            )
            Text(
                modifier = Modifier.clickable(onClick = onSignInClick),
                text = "Sign In",
                color = AppColors.secondary100,
                style = AppTextStyles.smallerTextBold,
            )
        }
    }
}

@Composable
private fun TermsCheckBox(isChecked: Boolean) {
    val backgroundColor = if (isChecked) AppColors.secondary100 else AppColors.white

    Box(
        modifier = Modifier
            .size(17.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = AppColors.secondary100,
                shape = RoundedCornerShape(5.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isChecked) {
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = AppColors.white,
            )
        }
    }
}
