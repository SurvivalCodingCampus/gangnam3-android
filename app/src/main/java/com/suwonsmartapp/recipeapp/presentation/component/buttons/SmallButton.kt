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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun SmallButtonPreview() {
    SmallButton("Sign In") {
        println("클릭!!")
    }
}

/**
 * Small button.
 * Layout: 174 x 37, radius 10, padding 10 (vertical) / 30 (horizontal), gap 9.
 */
@Composable
fun SmallButton(
    text: String,
    enable: Boolean = true,
    containerColor: Color = AppColors.primary100,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    // Pressed or disabled -> Disable colour; otherwise the container colour.
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        // Caller's size comes first so it overrides the default 174 x 37.
        modifier = modifier
            .width(174.dp)
            .height(37.dp)
            .background(
                color = if (!enable || isPressed) AppColors.gray4 else containerColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                enabled = enable,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = AppColors.white,
            style = TextStyles.smallerTextBold,
            textAlign = TextAlign.Center,
        )
    }
}
