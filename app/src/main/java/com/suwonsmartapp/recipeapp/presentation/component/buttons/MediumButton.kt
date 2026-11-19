package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun MediumButton(
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = false,  // 버튼 활성화 여부
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val buttonColor = when {  // 상태 우선순위에 따라 결정 : 비활성화 > 눌림 중 > 기본 상태
        !enable -> AppColors.primary100
        isPressed -> AppColors.gray4
        else -> AppColors.primary100
    }
    
    Row(
        modifier = modifier
            .width(243.dp)
            .height(54.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = buttonColor)
            .clickable(
                // clickable이 터치 이벤트를 감지하면 interactionSource로 신호 보냄
                interactionSource = interactionSource,
                indication = null,
                enabled = enable,  // false일 때 클릭/눌림 효과 제어
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 9.dp,
            alignment = Alignment.CenterHorizontally,
        )
    ) {
        Text(
            text = text,
            color = AppColors.white,
            style = AppTextStyles.normalTextBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(114.dp)
        )
        
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "arrow forward",
            tint = AppColors.white,
            modifier = Modifier.size(20.dp),  // 하나만 적어주면 가로세로 20.dp 적용됨
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MediumButtonPreview() {
    MediumButton("Button") { }
}
