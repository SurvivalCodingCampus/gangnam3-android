package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
fun BigButton(
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = false,  // 버튼 활성화 여부
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    // interactionSource : 터치, 클릭, 드래그 같은 상호작용 이벤트가 오고가는 파이프라인
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val buttonColor = when {  // 상태 우선순위에 따라 결정 : 비활성화 > 눌림 중 > 기본 상태
        !enable -> AppColors.primary100
        isPressed -> AppColors.gray4
        else -> AppColors.primary100
    }
    // isPressed가 바뀔 때마다 BigButton 함수 전체가 처음부터 Recomposition되기 때문에 val로 ㄱㄱ
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .clip(shape = RoundedCornerShape(10.dp))  // 먼저 자르기. 그래야 클릭했을 때 색이 잘린 대로 됨
            .background(color = buttonColor)
            .clickable(
                // clickable이 터치 이벤트를 감지하면 interactionSource로 신호 보냄
                interactionSource = interactionSource,
                indication = null,
                enabled = enable,  // false일 때 클릭/눌림 효과 제어
                onClick = onClick
            )
            .padding(  // 패딩은 클릭 뒤에 넣어야 함. 아니면 클릭할 수 있는 영역 좁아짐
                vertical = 18.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 11.dp,  // Spacer 끼워넣는 것보다 우아함
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        Text(
            text = text,
            color = AppColors.white,
            style = AppTextStyles.normalTextBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(min = 114.dp),
        )
        
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow forward",
            tint = AppColors.white,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BigButtonPreview() {
    BigButton(text = "Button") { }
}
