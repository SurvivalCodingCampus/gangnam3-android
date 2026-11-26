package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suwonsmartapp.recipeapp.presentation.home.HomeRoot

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

            HomeRoot()
        }
    }
}
