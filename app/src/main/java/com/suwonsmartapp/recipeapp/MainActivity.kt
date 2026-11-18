package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.presentation.RatingDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showDialog by remember { mutableStateOf(true) }
            var rating by remember { mutableIntStateOf(0) }

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    RatingDialog(
                        title = "Rate recipe",
                        actionName = "Send",
                        rating = rating,
                        onRatingChange = { rating = it },
                        onChange = {
                            println(it)
                            showDialog = false
                        },
                    )
                }
            }
        }
    }
}

