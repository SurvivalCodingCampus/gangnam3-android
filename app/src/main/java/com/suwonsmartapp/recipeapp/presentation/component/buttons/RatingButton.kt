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
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RatingStarIcon(
    text: String,
    isSelected: Boolean,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier
) {
    val textColor = if (isSelected) AppColors.white else AppColors.primary80
    val borderColor = if (isSelected) AppColors.primary100 else AppColors.primary80
    val backgroundColor = if (isSelected) AppColors.primary100 else AppColors.white
    
    Row(
        modifier = modifier
            .width(51.dp)
            .height(28.dp)
            .clip(shape = shape)
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape,  // 모양 일치 중요!
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            color = textColor,
            style = AppTextStyles.smallerTextBold.copy(
                fontWeight = FontWeight.Medium,
            ),
            
        )
        
        Spacer(Modifier.size(0.5.dp))
        
        Icon(
            imageVector = Icons.Filled.StarRate,
            contentDescription = "Star rate",
            tint = textColor,
            modifier = Modifier
                .size(18.dp)
        )
    }
}

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

@Preview(showBackground = true)
@Composable
private fun RatingButtonPreview() {
    val text = "5"
    var isSelected1 by remember { mutableStateOf(false) }
    var isSelected2 by remember { mutableStateOf(true) }
    
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