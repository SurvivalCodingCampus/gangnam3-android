package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.suwonsmartapp.recipeapp.ui.theme.RecipePrimary
import com.suwonsmartapp.recipeapp.ui.theme.RecipeRed

/**
 * 썸네일 자리 — 실제 앱은 이미지 로더(예: Coil)로 `imageUrl`을 렌더한다.
 * `imageUrl`이 비어 있지 않으면 로더 교체 지점(현재: 자리표시 텍스트), 비어 있으면 기존 이모지/틴트 자리.
 * 빈/공백 URL은 null 정규화.
 */
@Composable
fun RecipeMedia(
    emoji: String,
    imageUrl: String? = null,
    modifier: Modifier = Modifier,
    height: Dp = 130.dp,
    emojiSize: androidx.compose.ui.unit.TextUnit = 40.sp,
    tint: Color = RecipePrimary.copy(alpha = 0.15f),
    rounded: Dp = 16.dp
) {
    val url = imageUrl?.trim()?.takeIf { it.isNotBlank() }
    Box(
        modifier = modifier
            .height(height)
            .background(tint, shape = RoundedCornerShape(rounded))
    ) {
        when {
            // 로딩/실패/성공 상태는 로더(Coil)가 처리 — URL이 없을 때만 이모지 자리표시 유지.
            url != null -> AsyncImage(
                model = url,
                contentDescription = null, // 장식 썸네일(제목 텍스트 병기) — a11y상 장식 처리
                modifier = Modifier.fillMaxSize()
            )
            emoji.isNotEmpty() -> Text(text = emoji, fontSize = emojiSize)
        }
    }
}

/** 북마크 하트 토글(명세 §3.2/§3.3/§3.4) — 저장됨: 브랜드 레드, 미저장: 중립 그레이. */
@Composable
fun HeartToggle(
    saved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "♥",
        color = if (saved) RecipeRed else MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 20.sp,
        modifier = modifier.clickable(onClickLabel = if (saved) "북마크 해제" else "북마크 저장") { onClick() }
    )
}
