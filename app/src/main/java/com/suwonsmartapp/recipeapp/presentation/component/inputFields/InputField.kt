package com.suwonsmartapp.recipeapp.presentation.component.inputFields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    val (text, setValue) = remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = text,
            onValueChange = setValue,
        )
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = text,
            onValueChange = setValue,
        )
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = "text",
            onValueChange = setValue,
        )
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = label,
            color = AppColors.black2,
            style = AppTextStyles.smallTextRegular,
        )
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColors.gray4,
                    style = AppTextStyles.smallTextRegular,
                )
            },
            textStyle = AppTextStyles.smallTextRegular.copy(  // Filled text
                fontSize = 11.sp,
                color = AppColors.black1,
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.primary80,
                unfocusedBorderColor = AppColors.gray4,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}