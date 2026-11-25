package com.suwonsmartapp.recipeapp.presentation.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.theme.AppColors
import com.suwonsmartapp.recipeapp.presentation.theme.AppTextStyles

// 조리 시간 + 북마크 버튼
@Composable
fun RecipeTimeBookmark(
    time: String,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 타이머 아이콘
        Icon(
            imageVector = Icons.Outlined.Timer,
            contentDescription = "조리 시간",
            tint = AppColors.gray4,
        )
        Spacer(modifier = Modifier.width(5.dp))

        // 시간
        Text(
            text = time,
            color = AppColors.gray4,
            style = AppTextStyles.smallerTextRegular
        )
        Spacer(modifier = Modifier.width(10.dp))

        // 북마크 아이콘
        Icon(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = AppColors.white,
                    shape = CircleShape,
                )
                .clickable(
                    onClick = onBookmarkClick
                )
                .padding(4.dp),
            imageVector = icon,
            contentDescription = "북마크",
            tint = AppColors.primary80,
        )
    }
}

@Preview
@Composable
private fun RecipeTimeBookmarkPreview() {
    var isBookmarked by rememberSaveable { mutableStateOf(true) }

    RecipeTimeBookmark(
        modifier = Modifier.background(AppColors.black1),
        time = "20 min",
        isBookmarked = isBookmarked,
        onBookmarkClick = { isBookmarked = !isBookmarked },
    )
}
