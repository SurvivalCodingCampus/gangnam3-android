package com.suwonsmartapp.recipeapp.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.TextDivider
import com.suwonsmartapp.recipeapp.presentation.component.buttons.BigButton
import com.suwonsmartapp.recipeapp.presentation.component.inputFields.InputField
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
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
        )
        
        Spacer(Modifier.size(25.dp))
        
        BigButton(
            text = "Sign In",
            enable = email.isNotBlank() && password.isNotBlank()
        ) { }
        
        Spacer(Modifier.size(20.dp))
        
        TextDivider("Or Sign in With")
        
        Spacer(Modifier.size(20.dp))
        
//        TODO("Row 커뮤니티 버튼")
        
        Spacer(Modifier.size(55.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
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