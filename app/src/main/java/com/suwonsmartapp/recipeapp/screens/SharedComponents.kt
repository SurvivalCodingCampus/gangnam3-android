package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.suwonsmartapp.recipeapp.R

/**
 * URL 없음/로딩/실패 시 이모지 폴백. 사진은 카드 크기에 맞춰 크롭하고 둥근 경계로 자른다.
 * 제목이 별도로 표시되므로 이미지와 폴백은 장식으로 취급한다.
 */
@Composable
fun RecipeMedia(
    emoji: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    height: Dp = 130.dp,
    emojiSize: TextUnit = 40.sp,
    // 다크 모드에서도 브랜드 틴트가 유지되도록 테마 색을 사용한다.
    tint: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
    rounded: Dp = 16.dp
) {
    // 빈 문자열이나 공백 URL은 이미지가 없는 것으로 처리한다.
    val url = imageUrl?.trim()?.takeIf { it.isNotBlank() }
    // url을 키로 사용해 이미지 주소가 바뀌면 로딩 상태를 초기화한다.
    var loading by remember(url) { mutableStateOf(url != null) }
    var loaded by remember(url) { mutableStateOf(false) }
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(rounded))
            .background(tint),
        contentAlignment = Alignment.Center
    ) {
        // 이미지가 준비될 때까지, 또는 실패했을 때 이모지를 대신 보여준다.
        if (!loaded && emoji.isNotEmpty()) Text(text = emoji, fontSize = emojiSize,
            modifier = Modifier.clearAndSetSemantics { })
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                onLoading = { loading = true; loaded = false },
                onSuccess = { loading = false; loaded = true },
                onError = { loading = false; loaded = false }
            )
        }
        if (loading) {
            // 이미지는 장식(제목이 별도 표시) — 진행률까지 읽어 주지 않도록 semantics 제거.
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(20.dp)
                    .clearAndSetSemantics { },
                strokeWidth = 2.dp
            )
        }
    }
}

/**
 * 뒤로가기 화살표 — RTL에서는 오른쪽(→)을 가리키도록 미러링한다.
 * 화살표 글리프는 장식이므로 semantics에서 제외하고 버튼 라벨만 읽힌다.
 */
@Composable
fun BackArrow(fontSize: TextUnit = 16.sp) {
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Text(
        text = if (rtl) "→" else "←",
        modifier = Modifier.clearAndSetSemantics { },
        fontSize = fontSize
    )
}

/** 북마크 하트 토글(명세 §3.2/§3.3/§3.4) — 저장됨: 브랜드 레드, 미저장: 중립 그레이. */
@Composable
fun HeartToggle(
    saved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 현재 상태에서 누르면 일어날 동작을 읽어 준다.
    val label = stringResource(if (saved) R.string.bookmark_remove else R.string.bookmark_save)
    IconToggleButton(
        checked = saved,
        onCheckedChange = { onClick() },
        modifier = modifier.size(48.dp).semantics { contentDescription = label }
    ) {
        // 하트 글리프는 contentDescription으로 대체 — 중복 낭독 방지.
        Text(text = if (saved) "♥" else "♡",
            modifier = Modifier.clearAndSetSemantics { },
            color = if (saved) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 24.sp)
    }
}
