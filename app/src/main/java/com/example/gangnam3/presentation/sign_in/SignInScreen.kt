package com.example.gangnam3.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gangnam3.presentation.component.*

/**
 * 로그인 화면(Stateless). 입력값과 동작을 모두 호출자에게서 전달받는다.
 * 실제 인증은 하지 않으며 버튼을 누르면 콜백만 호출한다.
 */
@Composable
fun SignInScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 키보드의 다음/완료 버튼으로 입력칸 포커스를 이동하거나 해제한다.
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // 1. 헤더 영역
        Text(
            text = "Hello,",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Welcome Back!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF121212)
        )

        Spacer(modifier = Modifier.height(56.dp))

        // 2. 이메일 & 비밀번호 입력 영역
        AuthInputField(
            label = "Email",
            placeholder = "Enter Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        AuthInputField(
            label = "Enter Password",
            placeholder = "Enter Password",
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSignInClick()
                }
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 3. 비밀번호 찾기 링크
        Text(
            text = "Forgot Password?",
            fontSize = 11.sp,
            color = SecondaryOrange,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable(onClick = onForgotPasswordClick)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // 4. Sign In 버튼
        PrimaryActionButton(
            text = "Sign In",
            onClick = onSignInClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 5. 소셜 로그인
        SocialSignInSection(
            onGoogleClick = onGoogleSignInClick,
            onFacebookClick = onFacebookSignInClick
        )

        Spacer(modifier = Modifier.height(55.dp))

        // 6. 하단 회원가입 이동 링크
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account? ",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "Sign up",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SecondaryOrange,
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )
        }
    }
}

// 상태 호이스팅 검증용 Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    // 미리보기에서만 쓰는 임시 상태
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    SignInScreen(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onForgotPasswordClick = {},
        onSignInClick = {},
        onGoogleSignInClick = {},
        onFacebookSignInClick = {},
        onSignUpClick = {}
    )
}