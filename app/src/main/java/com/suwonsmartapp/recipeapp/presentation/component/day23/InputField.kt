package com.suwonsmartapp.recipeapp.presentation.component.day23

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        label = "Label",
        placeholder = "Placeholder",
        value = ""
    ) {
        println(it)
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(315.dp)
            .height(81.dp),
    ) {
        // 라벨
        Text(
            text = label,
            color = AppColors.label,
            style = AppTextStyles.smallTextRegular,
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 입력 칸 (테두리 박스)
        Box(
            modifier = Modifier
                .width(315.dp)
                .height(55.dp)
                .border(
                    width = 1.5.dp,
                    color = AppColors.gray4,
                    shape = RoundedCornerShape(10.dp),
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(20.dp))

                Box {
                    // 아무것도 안 썼을 때만 placeholder 보여주기
                    if (value.isBlank()) {
                        Text(
                            text = placeholder,
                            color = AppColors.gray4,
                            style = AppTextStyles.smallerTextRegular,
                        )
                    }

                    // 실제로 글자를 입력하는 칸
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.width(275.dp),
                        textStyle = AppTextStyles.smallerTextRegular.copy(color = AppColors.black1),
                        singleLine = true,
                    )
                }
            }
        }
    }
}
