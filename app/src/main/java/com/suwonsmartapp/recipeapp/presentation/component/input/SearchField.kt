package com.suwonsmartapp.recipeapp.presentation.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 검색창 (Figma: Search, 높이 40)
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search recipe",
) {
    val shape = RoundedCornerShape(10.dp)

    Row(
        modifier = modifier
            .height(40.dp)
            .background(color = AppColors.white, shape = shape)
            .border(width = 1.3.dp, color = AppColors.gray4, shape = shape)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 돋보기 아이콘
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Outlined.Search,
            contentDescription = "검색",
            tint = AppColors.gray4,
        )
        Spacer(modifier = Modifier.width(10.dp))

        // 입력칸 (비어 있으면 placeholder 표시)
        Box(modifier = Modifier.fillMaxWidth()) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = AppColors.gray4,
                    style = AppTextStyles.smallerTextRegular,
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                textStyle = AppTextStyles.smallerTextRegular.copy(color = AppColors.black1),
                singleLine = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchFieldEmptyPreview() {
    SearchField(
        modifier = Modifier.width(255.dp),
        value = "",
        onValueChange = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchFieldFilledPreview() {
    SearchField(
        modifier = Modifier.width(255.dp),
        value = "Jollof rice",
        onValueChange = {},
    )
}
