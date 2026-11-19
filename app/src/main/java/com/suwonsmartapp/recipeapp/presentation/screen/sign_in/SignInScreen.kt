package com.suwonsmartapp.recipeapp.presentation.screen.sign_in

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.SocialAuthButtons
import com.suwonsmartapp.recipeapp.presentation.component.TextDivider
import com.suwonsmartapp.recipeapp.presentation.component.buttons.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.inputFields.InputField
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SignInScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 화면 진입 시 초기 포커스 일괄 해제 : 회색 배경 방지
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        focusManager.clearFocus()
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()  // 기기 시스템 영역(상태바,노치,하단바,키보드) 여백 먼저 확보
            .padding(  // 디자인 여백 적용
                vertical = 50.dp,
                horizontal = 30.dp,
            )
            .pointerInput(Unit) {  // 입력 후 빈 배경 터치하면 포커스 해제 및 키보드 내리기
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        SignInHeader()
        
        Spacer(Modifier.size(57.dp))
        
        InputField(
            label = "Email",
            placeholder = "Enter Email",
            value = email,
            onValueChange = onEmailChange,
        )
        
        Spacer(Modifier.size(30.dp))
        
        InputField(
            label = "Password",
            placeholder = "Enter Password",
            value = password,
            onValueChange = onPasswordChange,
        )
        
        Spacer(Modifier.size(20.dp))
        
        Text(
            text = "Forgot Password?",
            color = AppColors.secondary100,
            style = AppTextStyles.smallerTextRegular,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                )
        )
        
        Spacer(Modifier.size(25.dp))
        
        BigButton(
            text = "Sign In",
            enable = email.isNotBlank() && password.isNotBlank()
        ) { }
        
        Spacer(Modifier.size(20.dp))
        
        TextDivider("Or Sign in With")
        
        Spacer(Modifier.size(20.dp))
        
        SocialAuthButtons(
            onGoogleClick = {},
            onFaceBookClick = {},
        )
        
        Spacer(Modifier.size(55.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Don't have an account? ",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextBold.copy(
                    fontWeight = FontWeight.SemiBold,
                )
            )
            
            Text(
                text = "Sign Up",
                color = AppColors.secondary100,
                style = AppTextStyles.smallerTextBold.copy(
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
    }
}

@Composable
private fun SignInHeader(
    title: String = "Hello,",
    subtitle: String = "Welcome Back!",
) {
    Column {
        Text(
            text = title,
            color = AppColors.black1,
            style = AppTextStyles.headerTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Text(
            text = subtitle,
            color = AppColors.black2,
            style = AppTextStyles.largeTextRegular,
        )
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
        onEmailChange = {
            email = it
        },
        onPasswordChange = {
            password = it
        }
    )
}