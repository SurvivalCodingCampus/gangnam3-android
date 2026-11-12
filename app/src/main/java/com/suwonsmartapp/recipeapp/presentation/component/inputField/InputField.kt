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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    val (text, setValue) = remember { mutableStateOf("") }

    InputField(
        label = "Label",
        placeholder = "Placeholder",
         value = text,
        onValueChange = setValue,
    )
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }  // 커서 잡혔는지
    val borderColor = if (isFocused) AppColors.primary80 else AppColors.gray4
    
    Column(
        modifier = Modifier
            .width(315.dp)
            .height(81.dp)
    ) {
        // Label
        Box(
            modifier = Modifier
                .height(21.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = label,
                color = AppColors.black2,
                style = AppTextStyles.smallTextRegular,
            )
        }
        
        Spacer(modifier = Modifier.size(5.dp))
        
        // Input field
        Box(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 19.dp,
                ),
        ) {
            BasicTextField(  // 여백 유지하면서 글자 입력 잘 보이게하려고. TextField는 기본 여백 있음
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .height(17.dp)
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        TODO("뭐라는거야 ")
                    },
                singleLine = true,
            ) {
                Text(
                    text = placeholder,
//                        color = Color.Blue,
                    color = AppColors.gray4,
                )
            }
//            }
        }
    }
}