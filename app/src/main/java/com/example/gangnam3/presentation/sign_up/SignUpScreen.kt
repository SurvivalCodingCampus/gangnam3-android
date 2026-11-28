package com.example.gangnam3.presentation.sign_up

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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

/** 회원가입 화면(Stateless). 입력값 검증과 실제 가입 처리는 호출자가 담당한다. */
@Composable
fun SignUpScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    isTermsAccepted: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 키보드의 다음 버튼으로 다음 입력칸에 포커스를 옮긴다.
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
        Spacer(modifier = Modifier.height(24.dp))

        // 1. 헤더 영역
        Text(
            text = "Create an account",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Let’s help you set up your account,\nit won’t take long.",
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = Color(0xFF121212)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2. 입력 필드 4개 (Name -> Email -> Password -> Confirm Password)
        AuthInputField(
            label = "Name",
            placeholder = "Enter Name",
            value = name,
            onValueChange = onNameChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        AuthInputField(
            label = "Password",
            placeholder = "Enter Password",
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthInputField(
            label = "Confirm Password",
            placeholder = "Retype Password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. 약관 동의 커스텀 체크박스
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp)
                // 상자와 문구 어디를 눌러도 동의 상태가 반대로 바뀐다.
                .clickable { onTermsAcceptedChange(!isTermsAccepted) }
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, SecondaryOrange),
                color = if (isTermsAccepted) SecondaryOrange else Color.White,
                modifier = Modifier.size(17.dp)
            ) {
                if (isTermsAccepted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checked",
                        tint = Color.White,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Accept terms & Condition",
                fontSize = 11.sp,
                color = SecondaryOrange
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Sign Up 버튼
        PrimaryActionButton(
            text = "Sign Up",
            onClick = onSignUpClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 5. 소셜 로그인
        SocialSignInSection(
            onGoogleClick = onGoogleSignInClick,
            onFacebookClick = onFacebookSignInClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 6. 하단 로그인 이동 링크
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already a member? ",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "Sign In",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SecondaryOrange,
                modifier = Modifier.clickable(onClick = onSignInClick)
            )
        }
    }
}

// 상태 호이스팅 검증용 Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    // 미리보기에서만 쓰는 임시 상태
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }

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
        onTermsAcceptedChange = { isTermsAccepted = it },
        onSignUpClick = {},
        onGoogleSignInClick = {},
        onFacebookSignInClick = {},
        onSignInClick = {}
    )
}