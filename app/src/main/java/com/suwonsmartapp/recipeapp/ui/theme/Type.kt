package com.suwonsmartapp.recipeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

private fun poppins(size: Int, lineHeight: Int, weight: FontWeight) = TextStyle(
    fontFamily = Poppins,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp
)

// Poppins - Bold
val TitleTextBold = poppins(50, 75, FontWeight.Bold)
val HeaderTextBold = poppins(30, 45, FontWeight.Bold)
val LargeTextBold = poppins(20, 30, FontWeight.Bold)
val MediumTextBold = poppins(18, 27, FontWeight.Bold)
val NormalTextBold = poppins(16, 24, FontWeight.Bold)
val SmallTextBold = poppins(14, 21, FontWeight.Bold)
val SmallerTextBold = poppins(11, 17, FontWeight.Bold)

// Poppins - Regular
val TitleTextRegular = poppins(50, 75, FontWeight.Normal)
val HeaderTextRegular = poppins(30, 45, FontWeight.Normal)
val LargeTextRegular = poppins(20, 30, FontWeight.Normal)
val MediumTextRegular = poppins(18, 27, FontWeight.Normal)
val NormalTextRegular = poppins(16, 24, FontWeight.Normal)
val SmallTextRegular = poppins(14, 21, FontWeight.Normal)
val SmallerTextRegular = poppins(11, 17, FontWeight.Normal)

// Material 3 typography mapped onto the Poppins scale
val Typography = Typography(
    displayLarge = TitleTextBold,
    displayMedium = TitleTextRegular,
    headlineLarge = HeaderTextBold,
    headlineMedium = HeaderTextRegular,
    titleLarge = LargeTextBold,
    titleMedium = MediumTextBold,
    titleSmall = NormalTextBold,
    bodyLarge = LargeTextRegular,
    bodyMedium = MediumTextRegular,
    bodySmall = NormalTextRegular,
    labelLarge = SmallTextBold,
    labelMedium = SmallTextRegular,
    labelSmall = SmallerTextRegular
)
