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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    Column {
        Spacer(modifier = Modifier.size(30.dp))
        var text by remember { mutableStateOf("") }
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = "Placeholder",
        ) {
            text = it
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
            style = TextStyles.smallTextRegular,
            color = AppColors.black1
        )
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    style = TextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
            },
            textStyle = TextStyles.smallerTextRegular.copy(
                color = AppColors.black1,
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
