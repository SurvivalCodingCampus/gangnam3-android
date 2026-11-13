package com.suwonsmartapp.recipeapp.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyles {

    private fun poppins(size: Int, lineHeight: Int, weight: FontWeight) = TextStyle(
        fontFamily = AppFonts.Poppins,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
    )

    // Poppins - Bold
    val titleTextBold = poppins(size = 50, lineHeight = 75, weight = FontWeight.Bold)
    val headerTextBold = poppins(size = 30, lineHeight = 45, weight = FontWeight.Bold)
    val largeTextBold = poppins(size = 20, lineHeight = 30, weight = FontWeight.Bold)
    val mediumTextBold = poppins(size = 18, lineHeight = 27, weight = FontWeight.Bold)
    val normalTextBold = poppins(size = 16, lineHeight = 24, weight = FontWeight.Bold)
    val smallTextBold = poppins(size = 14, lineHeight = 21, weight = FontWeight.Bold)
    val smallerTextBold = poppins(size = 11, lineHeight = 17, weight = FontWeight.Bold)

    // Poppins - Regular
    val titleTextRegular = poppins(size = 50, lineHeight = 75, weight = FontWeight.Normal)
    val headerTextRegular = poppins(size = 30, lineHeight = 45, weight = FontWeight.Normal)
    val largeTextRegular = poppins(size = 20, lineHeight = 30, weight = FontWeight.Normal)
    val mediumTextRegular = poppins(size = 18, lineHeight = 27, weight = FontWeight.Normal)
    val normalTextRegular = poppins(size = 16, lineHeight = 24, weight = FontWeight.Normal)
    val smallTextRegular = poppins(size = 14, lineHeight = 21, weight = FontWeight.Normal)
    val smallerTextRegular = poppins(size = 11, lineHeight = 17, weight = FontWeight.Normal)
}
