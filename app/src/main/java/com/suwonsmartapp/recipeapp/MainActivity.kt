package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

// 앱의 메인 초록색 (Figma: #129575)
val Primary = Color(0xFF129575)

// ───────────── 연습문제 1. Buttons ─────────────

@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(315.dp).height(60.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White,
        ),
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.width(11.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
fun MediumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(243.dp).height(54.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White,
        ),
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.width(11.dp)) // 간격 확인되면 수정
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(174.dp).height(37.dp),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp), // 작은 버튼은 기본 여백 때문에 글자가 잘릴 수 있어서 0으로
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White,
        ),
    ) {
        Text(text = text, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

// ───────────── 연습문제 2. InputField ─────────────

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = label, fontSize = 14.sp, color = Color(0xFF121212)) // 추정값
        Spacer(Modifier.height(5.dp))                                  // 추정값
        OutlinedTextField(
            value = value,                 // 값은 바깥에서 받은 것만 보여줌
            onValueChange = onValueChange, // 바뀐 값은 바깥으로 전달
            placeholder = {
                Text(placeholder, fontSize = 11.sp, color = Color(0xFFD9D9D9)) // 추정값
            },
            textStyle = TextStyle(fontSize = 11.sp, color = Color.Black),     // Filled 글자
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD9D9D9), // Default 테두리 (추정)
                focusedBorderColor = Primary,             // Focus 테두리
            ),
            modifier = Modifier.width(315.dp),            // 추정값
        )
    }
}

// ───────────── 미리보기 ─────────────

@Preview(showBackground = true)
@Composable
fun ButtonsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BigButton(text = "Button", onClick = {})
        MediumButton(text = "Button", onClick = {})
        SmallButton(text = "Button", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    // 입력값(상태)은 InputField 안이 아니라 "사용하는 쪽"이 가지고 있음
    var text by remember { mutableStateOf("") }
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.padding(16.dp),
    )
}