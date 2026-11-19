package com.example.gangnam3.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryGreen = Color(0xFF129575)
val SecondaryOrange = Color(0xFFFF9C00)
val GrayBorder = Color(0xFFD9D9D9)
val GrayText = Color(0xFFA9A9A9)

// 공통 입력 필드 (Stateless)
@Composable
fun AuthInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF121212)
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = GrayText, fontSize = 12.sp)
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = GrayBorder,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}

// 공통 메인 버튼 (우측 화살표 아이콘 포함)
@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
            contentColor = Color.White,
            disabledContainerColor = GrayBorder,
            disabledContentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// 공통 소셜 로그인 영역 ("Or Sign in With" + Google / Facebook 버튼)
@Composable
fun SocialSignInSection(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = GrayBorder)
            Text(
                text = "Or Sign in With",
                fontSize = 11.sp,
                color = GrayText,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = GrayBorder)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Google 버튼
            Surface(
                onClick = onGoogleClick,
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 2.dp,
                color = Color.White,
                border = BorderStroke(0.5.dp, Color(0xFFEEEEEE)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "G",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDB4437)
                    )
                }
            }

            // Facebook 버튼
            Surface(
                onClick = onFacebookClick,
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 2.dp,
                color = Color.White,
                border = BorderStroke(0.5.dp, Color(0xFFEEEEEE)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "f",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1877F2)
                    )
                }
            }
        }
    }
}