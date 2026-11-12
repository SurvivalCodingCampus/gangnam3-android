package com.suwonsmartapp.recipeapp.presentation.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun InputFieldFocusPreview() {
    InputFieldFocus(
        label = "Label",
        placeholder = "Placeholder",
        value = "",
    ) {
        println("입력: $it")
    }
}

/**
 * Input field (focus state).
 * Layout: 315 x 55, radius 10, border 1.5 (primary80), padding 20 (horizontal).
 */
@Composable
fun InputFieldFocus(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(
            text = label,
            color = AppColors.black,
            style = TextStyles.smallTextRegular,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    color = AppColors.primary80,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(1.5.dp)
                .background(
                    color = AppColors.white,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = AppColors.gray4,
                    style = TextStyles.smallerTextRegular,
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyles.smallerTextRegular.copy(color = AppColors.black),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
