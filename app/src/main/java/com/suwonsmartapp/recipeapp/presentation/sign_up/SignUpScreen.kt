package com.suwonsmartapp.recipeapp.presentation.sign_up

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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
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
fun SignUpScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    checked: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
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
            .padding(
                // 디자인 여백 적용
                vertical = 10.dp,
                horizontal = 30.dp,
            )
            .pointerInput(Unit) {  // 입력 후 빈 배경 터치 시 포커스 해제 및 키보드 내리기
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        SignUpHeader()
        
        Spacer(Modifier.size(20.dp))
        
        InputField(
            label = "Name",
            placeholder = "Enter Name",
            value = name,
            onValueChange = onNameChange,
        )
        
        Spacer(Modifier.size(20.dp))
        
        InputField(
            label = "Email",
            placeholder = "Enter Email",
            value = email,
            onValueChange = onEmailChange,
        )
        
        Spacer(Modifier.size(20.dp))
        
        InputField(
            label = "Password",
            placeholder = "Enter Password",
            value = password,
            onValueChange = onPasswordChange,
        )
        
        Spacer(Modifier.size(20.dp))
        
        InputField(
            label = "Confirm Password",
            placeholder = "Retype Password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
        )
        
        Spacer(Modifier.size(20.dp))
        
        TermsCheckboxRow(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
        
        Spacer(Modifier.size(26.dp))
        
        BigButton(
            text = "Sign Up",
            enable = name.isNotBlank()
                    && email.isNotBlank()
                    && password.isNotBlank()
                    && confirmPassword.isNotBlank()
                    && (password == confirmPassword)
        ) { }
        
        Spacer(Modifier.size(14.dp))
        
        TextDivider("Or Sign in With")
        
        Spacer(Modifier.size(20.dp))
        
        SocialAuthButtons(
            onGoogleClick = {},
            onFaceBookClick = {},
        )
        
        Spacer(Modifier.size(20.dp))
        
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Already a member? ",
                color = AppColors.black1,
                style = AppTextStyles.smallerTextBold.copy(
                    fontWeight = FontWeight.SemiBold,
                )
            )
            
            Text(
                text = "Sign In",
                color = AppColors.secondary100,
                style = AppTextStyles.smallerTextBold.copy(
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
    }
}

@Composable
private fun SignUpHeader(
    modifier: Modifier = Modifier,
    title: String = "Create an account",
    subtitle: String = "Let’s help you set up your account,\nit won’t take long."
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            color = AppColors.black1,
            style = AppTextStyles.largeTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        
        Text(
            text = subtitle,
            color = AppColors.black2,
            style = AppTextStyles.smallerTextRegular,
        )
        
    }
}

@Composable
fun TermsCheckboxRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
            )
            .toggleable(  // Row 전체 클릭 시 체크 상태
                value = checked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,  // 상위 Row의 toggleable에서 클릭 한 번에 처리
            colors = CheckboxDefaults.colors(
                checkedColor = AppColors.secondary100,
                uncheckedColor = AppColors.secondary100,
                checkmarkColor = AppColors.white,
            )
        )
        
        Spacer(Modifier.size(5.dp))
        
        Text(
            text = "Accept terms & Condition",
            color = AppColors.secondary100,
            style = AppTextStyles.smallerTextRegular,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
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
    )
}