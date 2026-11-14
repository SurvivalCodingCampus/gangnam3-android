package com.suwonsmartapp.recipeapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

/**
 * 라이트는 명세 §1.1 팔레트 그대로, 다크는 동일 색상계의 근사치(명세에 다크 정의 없음).
 * dynamicColor 기본값은 꺼짐 — Android 12+에서도 브랜드 팔레트가 고정되도록.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5BC7A7),
    secondary = Color(0xFFFFC266),
    tertiary = Color(0xFFFF7A7A),
    background = Color(0xFF121212),
    surface = Color(0xFF1F1F1F),
    onBackground = Color(0xFFEDEDED),
    onSurface = Color(0xFFEDEDED)
)

private val LightColorScheme = lightColorScheme(
    primary = RecipePrimary,
    secondary = RecipeStar,
    tertiary = RecipeRed,
    background = Color.White,
    onBackground = Color(0xFF121212),
    surface = RecipeSurface,
    onSurface = Color(0xFF292D32),
    surfaceVariant = RecipeSurfaceVariant,
    outline = RecipeBorder
)

@Composable
fun RecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ — off by default to keep the brand palette.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    // Poppins(명세 §1.2) — 자산 로드 실패 시 시스템 타이포 폴백.
    val typography = remember(context) {
        runCatching { poppinsTypography(context) }.getOrDefault(Typography)
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
