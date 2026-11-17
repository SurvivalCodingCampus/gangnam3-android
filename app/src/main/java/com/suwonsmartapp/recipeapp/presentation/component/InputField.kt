package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // One box, three states: Default -> Focus when tapped -> Filled when text is typed.
    // Tapping the white background clears focus, so the box leaves the Focus state.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    },
                )
            }
            .systemBarsPadding()
            .padding(20.dp),
    ) {
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = text,
        ) {
            text = it
        }
    }
}

/**
 * Input field.
 * Layout: 55 high, radius 10, border 1.5, padding 20 (horizontal).
 * Default: not focused, empty -> gray4 border.
 * Focus: tapped, still empty -> primary80 border.
 * Filled: has text -> gray4 border.
 */
@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isFilled = value.isNotEmpty()
    val borderColor = if (isFocused && !isFilled) AppColors.primary80 else AppColors.gray4

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = TextStyles.smallTextRegular,
            color = AppColors.black1
        )
        Spacer(Modifier.height(5.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyles.smallerTextRegular.copy(
                color = AppColors.black1,
            ),
            singleLine = true,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 20.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyles.smallerTextRegular,
                            color = AppColors.gray4,
                        )
                    }
                    innerTextField()
                }
            },
        )
    }
}
