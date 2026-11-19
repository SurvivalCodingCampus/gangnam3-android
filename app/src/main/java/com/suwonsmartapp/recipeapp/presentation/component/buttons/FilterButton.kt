package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun FilterButtonPreview() {
    val text = "Text"
    var isSelected1 by remember { mutableStateOf(false) }
    var isSelected2 by remember { mutableStateOf(true) }

    Row {
        FilterButton(
            text = text,
            isSelected = isSelected1,
        ) {
            isSelected1 = !isSelected1
        }

        Spacer(Modifier.size(5.dp))

        FilterButton(
            text = text,
            isSelected = isSelected2,
        ) {
            isSelected2 = !isSelected2
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)

    FilterIcon(
        text = text,
        isSelected = isSelected,
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .selectable(
                selected = isSelected,
                role = Role.Tab,
                onClick = onClick,
            ),
    )
}

@Composable
private fun FilterIcon( //파일 내부에서만 사용할 때
    text: String,
    isSelected: Boolean,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) AppColors.white else AppColors.primary80
    val borderColor = if (isSelected) AppColors.primary100 else AppColors.primary80
    val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white

    Row(
        modifier = Modifier
            .width(51.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                start = 10.dp,
                top = 5.dp,
                end = 10.dp,
                bottom = 5.dp,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyles.smallerTextBold.copy(
                fontWeight = FontWeight.Medium,
            )
        )
    }
}
