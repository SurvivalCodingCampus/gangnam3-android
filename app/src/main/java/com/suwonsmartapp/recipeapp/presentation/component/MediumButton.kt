package com.suwonsmartapp.recipeapp.presentation.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
fun MediumButtonDefaultPreview() {
    MediumButtonContent(text = "Sign In", isPressed = false) {}
}

@Preview(showBackground = true)
@Composable
fun MediumButtonPressedPreview() {
    MediumButtonContent(text = "Sign In", isPressed = true) {}
}

@Composable
fun MediumButton(
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    MediumButtonContent(
        text = text,
        isPressed = isPressed,
        interactionSource = interactionSource,
        onClick = onClick,
    )
}

@Composable
private fun MediumButtonContent(
    text: String,
    isPressed: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    val buttonColor = if (isPressed) AppColors.gray4 else AppColors.primary100

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(114.dp)
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = AppColors.white,
                    style = AppTextStyles.smallerTextBold,
                )
            }

            Spacer(modifier = Modifier.width(9.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "뒤로 가기",
                tint = AppColors.white,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}