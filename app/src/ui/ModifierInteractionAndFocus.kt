package com.example.gangnam3.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

// 8. 기본 클릭 및 클릭 비활성화
@Composable
fun ClickableExamples(modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = modifier
                .size(100.dp)
                .background(Color.White)
                .clickable {
                    println("Hello world!")
                }
        )
        Box(
            modifier = modifier
                .size(100.dp)
                .background(Color.White)
                .clickable(enabled = false) {
                    println("Hello world!")
                }
        )
    }
}

// 9. InteractionSource를 활용한 Press 상태 감지 및 Ripple 조합
@Composable
fun InteractionSourceExample() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = { /* do something */ },
            interactionSource = interactionSource
        ) {
            Text(if (isPressed) "Pressed!" else "Not pressed")
        }

        val boxInteractionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable(
                    onClick = {},
                    interactionSource = boxInteractionSource,
                    indication = ripple()
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello!")
        }
    }
}

// 10. 포커스 제어 (FocusRequester, LocalFocusManager, focusGroup, focusable, clearFocus)
@Composable
fun FocusControlExample() {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 5.dp,
                color = if (isFocused) Color.Red else Color.Gray
            )
            .onFocusChanged {
                isFocused = it.hasFocus
            }
            .focusGroup()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = Modifier
                .focusable()
                .focusRequester(focusRequester),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = text2,
            onValueChange = { text2 = it }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { focusRequester.requestFocus() }) {
                Text("포커스 요청")
            }
            Button(onClick = { focusManager.clearFocus() }) {
                Text("포커스 제거")
            }
        }
    }
}
