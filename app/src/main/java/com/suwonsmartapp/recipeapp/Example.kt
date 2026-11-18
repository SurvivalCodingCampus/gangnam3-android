package com.suwonsmartapp.recipeapp

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ExamplePreview() {
    Example()
}

@Composable
fun Example() {
//    Box(
//        modifier = Modifier
//            .size(size = 100.dp)
//            .clip(shape = CircleShape)
//            .background(color = Color.Red)
//            .padding(all = 16.dp)
//            .clip(shape = CircleShape)
//            .background(color = Color.Green),
//        contentAlignment = Alignment.Center,
//    ) {
//        Text(
//            text = "Hello world"
//        )
//    }

//    Image(
//        painter = painterResource(id = R.drawable.iu),
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        modifier = Modifier
//            .size(size = 200.dp)
//            // .clip(shape = CircleShape)
//            .clip(shape = RoundedCornerShape(topStartPercent = 50))
//    )

//    Text(
//        text = "Hello world",
//        modifier = Modifier
//            // .offset(x = 50.dp, y = 20.dp)
//            .padding(start = 50.dp, top = 20.dp)
//            .background(color = Color.Green)
//    )

//    Box(
//        modifier = Modifier
//            .size(size = 100.dp)
//            .background(color = Color.Gray)
//            .clickable(enabled = false) {
//                Log.d("TAG", "Hello world")
//            }
//    )

//     val interactionSource = remember { MutableInteractionSource() }
//     val isPressed by interactionSource.collectIsPressedAsState()

//    Button(
//        onClick = {},
//        interactionSource = interactionSource,
//    ) {
//        Text(
//            text = if (isPressed) "Pressed!" else "Not pressed"
//        )
//    }

//    Box(
//        modifier = Modifier
//            .size(size = 100.dp)
//            .clickable(
//                onClick = {}, interactionSource = interactionSource, indication = ripple()
//            ), contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "Hello"
//        )
//    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(
        value = "",
        onValueChange = {},
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        modifier = Modifier.focusRequester(focusRequester),
    )

    Button(
        onClick = {
            focusRequester.requestFocus()
        }) {
        Text(
            text = "Hello"
        )
    }
}