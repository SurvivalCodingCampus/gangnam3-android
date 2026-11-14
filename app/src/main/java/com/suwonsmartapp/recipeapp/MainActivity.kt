package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suwonsmartapp.recipeapp.presentation.component.InputField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InputField(
                label = "Label",
                placeholder = "Placeholder",
                value = ""
            ) {
                println(it)
            }
        }
    }
}

