package com.suwonsmartapp.recipeapp.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 버튼 크기별 기본값 (Big / Medium / Small)
enum class ButtonSize(
    val height: Dp,
    val textStyle: TextStyle,
    val showArrow: Boolean,
) {
    Big(height = 60.dp, textStyle = AppTextStyles.normalTextBold, showArrow = true),
    Medium(height = 54.dp, textStyle = AppTextStyles.smallerTextBold, showArrow = true),
    Small(height = 37.dp, textStyle = AppTextStyles.smallerTextBold, showArrow = false),
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Big,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    AppButtonContent(
        text = text,
        size = size,
        isPressed = isPressed,
        modifier = modifier,
        interactionSource = interactionSource,
        onClick = onClick,
    )
}

@Composable
private fun AppButtonContent(
    text: String,
    size: ButtonSize,
    isPressed: Boolean,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    val buttonColor = if (isPressed) AppColors.gray4 else AppColors.primary100

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(size.height)
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = AppColors.white,
                style = size.textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(114.dp),
            )

            // Small은 화살표 없음
            if (size.showArrow) {
                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = AppColors.white,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BigButtonPreview() {
    AppButtonContent(text = "Button", size = ButtonSize.Big, isPressed = false) {}
}

@Preview(showBackground = true)
@Composable
private fun BigButtonPressedPreview() {
    AppButtonContent(text = "Button", size = ButtonSize.Big, isPressed = true) {}
}

@Preview(showBackground = true)
@Composable
private fun MediumButtonPreview() {
    AppButtonContent(text = "Sign In", size = ButtonSize.Medium, isPressed = false) {}
}

@Preview(showBackground = true)
@Composable
private fun SmallButtonPreview() {
    AppButtonContent(text = "Button", size = ButtonSize.Small, isPressed = false) {}
}
