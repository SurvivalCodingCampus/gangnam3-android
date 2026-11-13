package com.suwonsmartapp.recipeapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private fun poppins(size: TextUnit, lineHeight: TextUnit, weight: FontWeight) = TextStyle(
    fontFamily = AppFonts.Poppins,
    fontWeight = weight,
    fontSize = size,
    lineHeight = lineHeight,
)

object TextStyles {
    // Bold (Figma: Poppins SemiBold, Smaller는 Medium)
    val titleTextBold = poppins(50.sp, 75.sp, FontWeight.SemiBold)
    val headerTextBold = poppins(30.sp, 45.sp, FontWeight.SemiBold)
    val largeTextBold = poppins(20.sp, 30.sp, FontWeight.SemiBold)
    val mediumTextBold = poppins(18.sp, 27.sp, FontWeight.SemiBold)
    val normalTextBold = poppins(16.sp, 24.sp, FontWeight.SemiBold)
    val smallTextBold = poppins(14.sp, 21.sp, FontWeight.SemiBold)
    val smallerTextBold = poppins(11.sp, 17.sp, FontWeight.Medium)

    // Regular
    val titleTextRegular = poppins(50.sp, 75.sp, FontWeight.Normal)
    val headerTextRegular = poppins(30.sp, 45.sp, FontWeight.Normal)
    val largeTextRegular = poppins(20.sp, 30.sp, FontWeight.Normal)
    val mediumTextRegular = poppins(18.sp, 27.sp, FontWeight.Normal)
    val normalTextRegular = poppins(16.sp, 24.sp, FontWeight.Normal)
    val smallTextRegular = poppins(14.sp, 21.sp, FontWeight.Normal)
    val smallerTextRegular = poppins(11.sp, 17.sp, FontWeight.Normal)
}
