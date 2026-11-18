package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
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
private fun RatingButtonPreview() {
    val text = "개인정보"
    var isSelected1 by remember { mutableStateOf(false) }
    var isSelected2 by remember { mutableStateOf(true) }

    // Padding keeps the button clear of the status bar when the preview runs on a device.
    Row {
        RatingButton(
            text = text,
            isSelected = isSelected1,
        ) {
            isSelected1 = !isSelected1
        }

        Spacer(Modifier.size(5.dp))

        RatingButton(
            text = text,
            isSelected = isSelected2,
        ) {
            isSelected2 = !isSelected2
        }
    }
}
/**
 * Rating button.
 * Layout: 50 x 28, radius 10, border 1, padding 5 (vertical) / 10 (horizontal), gap 5.
 */
@Composable
fun RatingButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)

    RatingStarIcon(
        text = text,
        isSelected = isSelected,
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .selectable(
                selected = isSelected,
                role = Role.Tab,
                onClick = onClick,
            )
    )
}

@Composable
private fun RatingStarIcon(
    text: String,
    isSelected: Boolean,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) AppColors.white else AppColors.primary80
    val borderColor = if (isSelected) AppColors.white else AppColors.primary80
    val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                vertical = 5.dp,
                horizontal = 10.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyles.smallerTextBold.copy(
                fontWeight = FontWeight.Medium,
            ),
        )
        Spacer(Modifier.size(1.dp))

        Icon(
            imageVector = Icons.Filled.StarRate,
            contentDescription = "Star rate",
            tint = textColor,
            modifier = Modifier
                .size(18.dp),
        )
    }
}
