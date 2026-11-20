package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.presentation.component.examples.CounterScreen
import com.suwonsmartapp.recipeapp.presentation.saved_recipes.SavedRecipesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SavedRecipesScreen()
        //           CounterScreen()
        }
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        enableEdgeToEdge()
//        setContent {
//            //화면을 rotate하면 dialog에도 반영
//            var isOpen by rememberSaveable { mutableStateOf(false) }
//
//            Column(
//                modifier = Modifier
//                    .safeDrawingPadding()
//                    .fillMaxSize(),
//            ) {
//                Button(onClick = {
//                    isOpen = !isOpen
//                }) {
//                    Text("Dialog")
//                }
//
//                if (isOpen) {
//                    Dialog(onDismissRequest = { isOpen = false }) {
//                        Box(
//                            modifier = Modifier
//                                .size(300.dp)
//                                .background(color = Color.Red)
//                        ) {
//                            Text("나는 다이얼로그다")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun MyComp(modifier: Modifier = Modifier) {
}
