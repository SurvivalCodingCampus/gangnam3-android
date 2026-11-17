package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.ui.AppColors
import androidx.compose.material3.OutlinedTextField

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    Column {
        Spacer(modifier = Modifier.size(30.dp))
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = "",
        ) {
            println("입력: $it")
        }
    }
}

/**
 * Input field (default state).
 * Layout: 55, radius 10, border 1.5 (gray4), padding 20 (horizontal).
 */
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
        Text(
            text = label,
            fontSize = 14.sp,
            color = AppColors.black1
        )
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    fontSize = 11.sp,
                    color = AppColors.gray4,
                )
            },
            textStyle = TextStyle(
                fontSize = 11.sp,
                color = AppColors.black1
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.gray4,
                focusedBorderColor = AppColors.primary80,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
