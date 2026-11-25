package com.example.gangnam3.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomColorScheme = lightColorScheme(
    primary = Color(0xFF129575),
    secondary = Color(0xFFFF9C00),
    surface = Color(0xFF121212),
    surfaceVariant = Color(0xFFD9D9D9)
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        content = content
    )
}