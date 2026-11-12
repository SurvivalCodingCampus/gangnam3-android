package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.TextStyles

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = "",
        onValueChange = {},
    )
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = AppColors.dark,
            style = TextStyles.smallTextRegular,
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 5.dp),
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.primary80,
                unfocusedBorderColor = AppColors.gray4,
            ),
            textStyle = TextStyles.smallerTextRegular,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyles.smallerTextRegular,
                )
            },
        )
    }
}