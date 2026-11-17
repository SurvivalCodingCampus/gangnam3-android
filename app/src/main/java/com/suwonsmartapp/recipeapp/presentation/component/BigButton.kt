package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding


@Preview(showBackground = true)
@Composable
fun BigButtonPreview() {
    BigButton("Big Button",) {
        println("클릭!!")
    }
}

/**
 * Big button.
 * Layout: 315 x 60, radius 10, padding 18 (vertical) / 85 (horizontal), gap 11.
 */
@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
) {
    // Pressed -> Disable colour; tap-up or cancel -> Default colour.
    var isPressed by remember { mutableStateOf(false) }
    val currentOnClick by rememberUpdatedState(onClick)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isPressed) AppColors.gray4 else AppColors.primary100,
                shape = RoundedCornerShape(10.dp),
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // returns on tap-up or cancel
                        isPressed = false
                    },
                    onTap = { currentOnClick() },
                )
            }
            .padding (
                horizontal = 85.dp,
                vertical = 18.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text (
                text = text,
                color = AppColors.white,
                style = TextStyles.normalTextBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp),
            )

            Spacer(modifier = Modifier.size(11.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow forward",
                tint = AppColors.white,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
