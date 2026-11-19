package com.suwonsmartapp.recipeapp.presentation.component.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
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
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
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
        Spacer(Modifier.height(10.dp))

        Text(
            text = "Create an account",
            color = AppColors.black1,
            style = TextStyles.largeTextBold,
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "Let's help you set up your account,\nit won't take long.",
            color = AppColors.black2,
            style = TextStyles.smallerTextRegular,
        )

        Spacer(Modifier.height(20.dp))

        // 입력칸 사이 간격은 spacedBy 로 한 번에 지정
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            InputField(
                label = "Name",
                placeholder = "Enter Name",
                value = name,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = onNameChange,
            )

            InputField(
                label = "Email",
                placeholder = "Enter Email",
                value = email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                onValueChange = onEmailChange,
            )

            InputField(
                label = "Password",
                placeholder = "Enter Password",
                value = password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = onPasswordChange,
            )

            InputField(
                label = "Confirm Password",
                placeholder = "Retype Password",
                value = confirmPassword,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() },
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = onConfirmPasswordChange,
            )
        }

        Spacer(Modifier.height(20.dp))

        TermsCheckbox(
            checked = isTermsAccepted,
            modifier = Modifier.padding(start = 10.dp),
            onCheckedChange = onTermsAcceptedChange,
        )

        Spacer(Modifier.height(26.dp))

        BigButton(text = "Sign Up") {
            focusManager.clearFocus()
            onSignUpClick()
        }

        Spacer(Modifier.height(14.dp))

        SocialLoginSection(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onGoogleClick = onGoogleClick,
            onFacebookClick = onFacebookClick,
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Already a member? ",
                color = AppColors.black1,
                style = TextStyles.smallerTextSemiBold,
            )
            Text(
                text = "Sign In",
                color = AppColors.secondary100,
                style = TextStyles.smallerTextSemiBold,
                modifier = Modifier.clickable(onClick = onSignInClick),
            )
        }

        Spacer(Modifier.height(20.dp))
    }
}

/** "Accept terms & Condition" 체크박스. 글자까지 포함해 줄 전체가 눌린다. */
@Composable
private fun TermsCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    val boxShape = RoundedCornerShape(5.dp)

    Row(
        modifier = modifier.toggleable(
            value = checked,
            role = Role.Checkbox,
            onValueChange = onCheckedChange,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(17.dp)
                .clip(boxShape)
                .background(if (checked) AppColors.secondary100 else AppColors.white)
                .border(width = 1.dp, color = AppColors.secondary100, shape = boxShape),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = AppColors.white,
                    modifier = Modifier.size(13.dp),
                )
            }
        }

        Spacer(Modifier.width(5.dp))

        Text(
            text = "Accept terms & Condition",
            color = AppColors.secondary100,
            style = TextStyles.smallerTextRegular,
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SignUpScreenPreview() {
    // 상태는 상위(Preview)에서 관리하고, SignUpScreen 은 값과 이벤트만 주고받는다.
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
        onTermsAcceptedChange = { isTermsAccepted = it },
        onSignUpClick = { println("sign up: $name / $email / $password") },
    )
}
