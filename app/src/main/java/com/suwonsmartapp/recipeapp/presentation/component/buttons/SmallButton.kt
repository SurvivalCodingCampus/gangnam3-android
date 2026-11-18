package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun SmallButtonPreview() {
    SmallButton("Button") { }
}

@Composable
fun SmallButton(
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,  // 활성화 여부
    containerColor: Color = AppColors.primary100,  // 기본 배경색
    pressedColor: Color = AppColors.gray4,  // 눌렀을 때 배경색
    disableColor: Color = AppColors.gray4,  // 비활성화 배경색
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // 상태 우선순위에 따라 결정 : 비활성화 > 눌림 중 > 기본 상태
    val buttonColor = when {
        !enable -> disableColor
        isPressed -> pressedColor
        else -> containerColor
    }
    
    Box(
        modifier = modifier
            .width(174.dp)
            .height(37.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = buttonColor)
            .clickable(
                interactionSource = interactionSource,
                enabled = enable,  // false일 때는 클릭/눌림 효과 막아줌
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .width(114.dp)
                .height(17.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = AppColors.white,
                style = AppTextStyles.smallerTextBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}