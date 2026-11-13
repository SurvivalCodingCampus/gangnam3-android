package com.suwonsmartapp.recipeapp.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R

object AppFonts {
    val Poppins = FontFamily(
        // Thin (100)
        Font(resId = R.font.poppins_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
        Font(
            resId = R.font.poppins_thin_italic,
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        ),

        // ExtraLight (200)
        Font(
            resId = R.font.poppins_extra_light,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.poppins_extra_light_italic,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        ),

        // Light (300)
        Font(resId = R.font.poppins_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(
            resId = R.font.poppins_light_italic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),

        // Regular / Normal (400)
        Font(resId = R.font.poppins_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(resId = R.font.poppins_italic, weight = FontWeight.Normal, style = FontStyle.Italic),

        // Medium (500)
        Font(resId = R.font.poppins_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(
            resId = R.font.poppins_medium_italic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),

        // SemiBold (600)
        Font(
            resId = R.font.poppins_semi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.poppins_semi_bold_italic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        ),

        // Bold (700)
        Font(resId = R.font.poppins_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(
            resId = R.font.poppins_bold_italic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),

        // ExtraBold (800)
        Font(
            resId = R.font.poppins_extra_bold,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.poppins_extra_bold_italic,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),

        // Black (900)
        Font(resId = R.font.poppins_black, weight = FontWeight.Black, style = FontStyle.Normal),
        Font(
            resId = R.font.poppins_black_italic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
    )
}