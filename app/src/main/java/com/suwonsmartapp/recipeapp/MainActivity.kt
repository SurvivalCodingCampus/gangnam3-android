package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            var showDialog by remember { mutableStateOf(true) }
//            var rating by remember { mutableIntStateOf(0) }
//
//            if (showDialog) {
//                Dialog(onDismissRequest = { showDialog = false }) {
//                    RatingDialog(
//                        title = "Rate recipe",
//                        actionName = "Send",
//                        rating = rating,
//                        onRatingChange = { rating = it },
//                        onChange = {
//                            println(it)
//                            showDialog = false
//                        },
//                    )
//                }
//            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Example()
            }
        }
    }
}

