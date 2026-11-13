package com.suwonsmartapp.recipeapp.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyles {
    // Bold
    val titleTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        lineHeight = 75.sp,
    )
    val headerTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 45.sp,
    )
    val largeTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    )
    val mediumTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
    )
    val normalTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )
    val smallTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    )
    val smallerTextBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 17.sp,
    )

    val smallerTextSemiBold = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 11.sp,
    )

    // Regular
    val titleTextRegular = titleTextBold.copy(fontWeight = FontWeight.Normal)
    val headerTextRegular = headerTextBold.copy(fontWeight = FontWeight.Normal)
    val largeTextRegular = largeTextBold.copy(fontWeight = FontWeight.Normal)
    val mediumTextRegular = mediumTextBold.copy(fontWeight = FontWeight.Normal)
    val normalTextRegular = normalTextBold.copy(fontWeight = FontWeight.Normal)
    val smallTextRegular = smallTextBold.copy(fontWeight = FontWeight.Normal)
    val smallerTextRegular = smallerTextBold.copy(fontWeight = FontWeight.Normal)
}