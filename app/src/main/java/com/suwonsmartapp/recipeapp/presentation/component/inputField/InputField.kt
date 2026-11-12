package com.suwonsmartapp.recipeapp.presentation.component.inputField

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = "value",
    ) {}
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(315.dp)
            .height(81.dp)
    ) {
        Box(
            modifier = Modifier
                .width(38.dp)
                .height(21.dp)
        ) {
            Text(
                text = label,
                color = AppColors.black2,
                style = AppTextStyles.smallTextRegular,
            )
        }
        
        Spacer(modifier = Modifier.size(5.dp))
        
        Box(
            modifier = Modifier
                .width(315.dp)
                .height(55.dp)
                .border(
                    width = 1.5.dp,
                    color = AppColors.gray4,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 19.dp,
                ),
            
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = placeholder,
                    color = AppColors.gray4,
                )
            }
        }
    }
}