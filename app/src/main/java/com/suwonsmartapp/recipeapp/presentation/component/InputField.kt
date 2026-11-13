package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.ui.AppColors


@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    Column {
        Spacer(modifier = Modifier.size(30.dp))
        InputFieldDefaultPreview()
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = ""
        ) {}
        InputFieldFilledPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldDefaultPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = ""
    ) {}
}

@Preview(showBackground = true)
@Composable
fun InputFieldFocusPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = ""
    ) {}
}

@Preview(showBackground = true)
@Composable
fun InputFieldFilledPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = "Placeholder"
    ) {}
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, fontSize = 14.sp, color = AppColors.black1) // 추정값
        Spacer(Modifier.height(5.dp))                                  // 추정값
        OutlinedTextField(
            value = value,                 // 값은 바깥에서 받은 것만 보여줌
            onValueChange = onValueChange, // 바뀐 값은 바깥으로 전달
            placeholder = {
                Text(placeholder, fontSize = 11.sp, color = AppColors.gray4) // 추정값
            },
            textStyle = TextStyle(fontSize = 11.sp, color = AppColors.black1),     // Filled 글자
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.gray4, // Default 테두리 (추정)
                focusedBorderColor = AppColors.primary80,             // Focus 테두리
            ),
            modifier = Modifier.fillMaxWidth(),            // 추정값
        )
    }
}