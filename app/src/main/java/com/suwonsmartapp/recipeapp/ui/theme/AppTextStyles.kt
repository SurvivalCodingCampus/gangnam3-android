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

object AppTextStyles {
    // Bold (Figma: Poppins SemiBold, Smaller는 Medium)
    val titleTextBold = poppins(size = 50.sp, lineHeight = 75.sp, weight = FontWeight.SemiBold)
    val headerTextBold = poppins(size = 30.sp, lineHeight = 45.sp, weight = FontWeight.SemiBold)
    val largeTextBold = poppins(size = 20.sp, lineHeight = 30.sp, weight = FontWeight.SemiBold)
    val mediumTextBold = poppins(size = 18.sp, lineHeight = 27.sp, weight = FontWeight.SemiBold)
    val normalTextBold = poppins(size = 16.sp, lineHeight = 24.sp, weight = FontWeight.SemiBold)
    val smallTextBold = poppins(size = 14.sp, lineHeight = 21.sp, weight = FontWeight.SemiBold)
    val smallerTextBold = poppins(size = 11.sp, lineHeight = 17.sp, weight = FontWeight.Medium)
    val smallerTextSemiBold = poppins(size = 11.sp, lineHeight = 11.sp, weight = FontWeight.SemiBold)

    // Regular
    val titleTextRegular = titleTextBold.copy(fontWeight = FontWeight.Normal)
    val headerTextRegular = headerTextBold.copy(fontWeight = FontWeight.Normal)
    val largeTextRegular = largeTextBold.copy(fontWeight = FontWeight.Normal)
    val mediumTextRegular = mediumTextBold.copy(fontWeight = FontWeight.Normal)
    val normalTextRegular = normalTextBold.copy(fontWeight = FontWeight.Normal)
    val smallTextRegular = smallTextBold.copy(fontWeight = FontWeight.Normal)
    val smallerTextRegular = smallerTextBold.copy(fontWeight = FontWeight.Normal)
}
