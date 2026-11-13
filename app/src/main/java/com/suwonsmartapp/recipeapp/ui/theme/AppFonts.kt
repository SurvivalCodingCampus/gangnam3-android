package com.suwonsmartapp.recipeapp.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R

object AppFonts {
    val Poppins = FontFamily(
        Font(
            resId = R.font.poppins_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            resId = R.font.poppins_bold,
            weight = FontWeight.Bold,
        ),
    )
}