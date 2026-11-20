package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.auth.SocialLoginSection
import com.suwonsmartapp.recipeapp.presentation.component.buttons.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.inputs.InputField
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Composable
fun SignInScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    // 전달받은 modifier 는 루트 레이아웃에만 적용한다.
    // 순서: 크기 -> 배경(화면 끝까지) -> 빈 곳 터치 시 포커스 해제
    //      -> 시스템 바/키보드 여백 -> 스크롤 -> 좌우 여백
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.white)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp),
    ) {
        Spacer(Modifier.height(50.dp))

        Text(
            text = "Hello,",
            color = AppColors.black1,
            style = TextStyles.headerTextBold,
        )
        Text(
            text = "Welcome Back!",
            color = AppColors.black2,
            style = TextStyles.largeTextRegular,
        )

        Spacer(Modifier.height(57.dp))

        InputField(
            label = "Email",
            placeholder = "Enter Email",
            value = email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,  // 키보드의 Next 로 다음 입력칸 이동
            ),
            onValueChange = onEmailChange,
        )

        Spacer(Modifier.height(30.dp))

        InputField(
            label = "Enter Password",
            placeholder = "Enter Password",
            value = password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = onPasswordChange,
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Forgot Password?",
            color = AppColors.secondary100,
            style = TextStyles.smallerTextRegular,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable(onClick = onForgotPasswordClick),
        )

        Spacer(Modifier.height(25.dp))

        BigButton(text = "Sign In") {
            focusManager.clearFocus()
            onSignInClick()
        }

        Spacer(Modifier.height(20.dp))

        SocialLoginSection(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onGoogleClick = onGoogleClick,
            onFacebookClick = onFacebookClick,
        )

        Spacer(Modifier.height(55.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Don't have an account? ",
                color = AppColors.black1,
                style = TextStyles.smallerTextSemiBold,
            )
            Text(
                text = "Sign up",
                color = AppColors.secondary100,
                style = TextStyles.smallerTextSemiBold,
                modifier = Modifier.clickable(onClick = onSignUpClick),
            )
        }

        Spacer(Modifier.height(30.dp))
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SignInScreenPreview() {
    // 상태는 상위(Preview)에서 관리하고, SignInScreen 은 값과 이벤트만 주고받는다.
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    SignInScreen(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onSignInClick = { println("sign in: $email / $password") },
    )
}
