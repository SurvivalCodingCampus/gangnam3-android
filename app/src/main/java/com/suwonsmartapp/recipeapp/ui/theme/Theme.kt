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
    onPrimary = Color(0xFF062019),
    primaryContainer = Color(0xFF0B4F40),
    onPrimaryContainer = Color(0xFFA9F2DC),
    secondary = Color(0xFFFFC266),
    onSecondary = Color(0xFF3A2800),
    secondaryContainer = Color(0xFF5A3F00),
    onSecondaryContainer = Color(0xFFFFE0A3),
    tertiary = Color(0xFFFF7A7A),
    background = Color(0xFF121212),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF1F1F1F),
    onSurface = Color(0xFFEDEDED),
    // 화면 코드가 직접 참조하는 역할 — 미지정 시 M3 기본 보라 계열이 노출된다.
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFC9C9C9),
    surfaceContainer = Color(0xFF1A1A1A),
    surfaceContainerHigh = Color(0xFF242424),
    outline = Color(0xFF5A5A5A),
    outlineVariant = Color(0xFF3A3A3A)
)

// 라이트 모드 색상.
private val LightColorScheme = lightColorScheme(
    primary = RecipePrimary,
    secondary = RecipeStar,
    tertiary = RecipeRed,
    // 선택 상태(세그먼트 탭/칩/하단 탭 인디케이터)도 브랜드 색으로 고정 —
    // 미지정 시 M3 기본 보라색 컨테이너가 노출된다.
    primaryContainer = Color(0xFFD7F2E9),
    onPrimaryContainer = Color(0xFF0B4F40),
    secondaryContainer = Color(0xFFFFE7BF),
    onSecondaryContainer = Color(0xFF4A3200),
    background = Color.White,
    onBackground = Color(0xFF121212),
    surface = RecipeSurface,
    onSurface = Color(0xFF292D32),
    surfaceVariant = RecipeSurfaceVariant,
    onSurfaceVariant = Color(0xFF49454F),
    surfaceContainer = Color.White,
    surfaceContainerHigh = RecipeSurface,
    outline = RecipeBorder
)

/**
 * 앱 공통 테마. 색상과 글꼴을 하위 모든 화면에 제공한다.
 * @param darkTheme 다크 모드 사용 여부(기본값은 시스템 설정)
 * @param dynamicColor Android 12 이상의 배경화면 기반 색상 사용 여부
 */
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
