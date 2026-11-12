package com.suwonsmartapp.recipeapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyles {
    val smallerTextRegular = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 11.sp,
    )
    val smallerTextSemiBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 11.sp,
    )
    val smallerTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    )
    val smallTextRegular = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
    )
}