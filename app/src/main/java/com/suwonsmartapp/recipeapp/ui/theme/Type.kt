package com.suwonsmartapp.recipeapp.ui.theme

import android.content.Context
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 시스템 기본 타이포 — Poppins 자산을 읽지 못할 때의 폴백.
 */
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
)

/**
 * Poppins 타이포 — 디자인 시스템 명세 §1.2 (Bold 22–30 / SemiBold 16–18 / Regular 14 / Medium 11–12).
 * 자산: app/src/main/assets/fonts/Poppins-{Regular,Medium,SemiBold,Bold}.ttf (SIL OFL 1.1 — 라이선스 동봉).
 * 자산 누락/손상/API 미지원 시 시스템 Typeface로 강등해 앱 시작이 깨지지 않게 한다.
 */
fun poppinsTypography(context: Context): Typography {
    val base = Typography()
    val fonts = listOfNotNull(
        poppinsFont(context, "Poppins-Regular.ttf", FontWeight.Normal),
        poppinsFont(context, "Poppins-Medium.ttf", FontWeight.Medium),
        poppinsFont(context, "Poppins-SemiBold.ttf", FontWeight.SemiBold),
        poppinsFont(context, "Poppins-Bold.ttf", FontWeight.Bold),
    )
    if (fonts.isEmpty()) return base
    val family = FontFamily(*fonts.toTypedArray())
    return Typography(
        displaySmall = base.displaySmall.copy(fontFamily = family),
        displayMedium = base.displayMedium.copy(fontFamily = family),
        displayLarge = base.displayLarge.copy(fontFamily = family),
        headlineSmall = base.headlineSmall.copy(fontFamily = family),
        headlineMedium = base.headlineMedium.copy(fontFamily = family),
        headlineLarge = base.headlineLarge.copy(fontFamily = family),
        titleSmall = base.titleSmall.copy(fontFamily = family),
        titleMedium = base.titleMedium.copy(fontFamily = family),
        titleLarge = base.titleLarge.copy(fontFamily = family),
        bodySmall = base.bodySmall.copy(fontFamily = family),
        bodyMedium = base.bodyMedium.copy(fontFamily = family),
        bodyLarge = base.bodyLarge.copy(fontFamily = family),
        labelSmall = base.labelSmall.copy(fontFamily = family),
        labelMedium = base.labelMedium.copy(fontFamily = family),
        labelLarge = base.labelLarge.copy(fontFamily = family),
    )
}

private fun poppinsFont(context: Context, asset: String, weight: FontWeight): Font? =
    runCatching { Font("fonts/$asset", context.assets, weight = weight, style = FontStyle.Normal) }
        .getOrNull()