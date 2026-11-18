package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors

@Composable
fun BookmarkIcon(
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
) {
    val bookmarkImage = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder
    val bookmarkColor = if (isBookmarked) AppColors.primary80 else AppColors.gray4

    Icon(
        imageVector = bookmarkImage,
        contentDescription = "Bookmark",
        tint = bookmarkColor,
        modifier = modifier
            .size(24.dp)
    )
}

@Composable
fun BookmarkImg(
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
) {
    val bookmarkImage = if (isBookmarked) R.drawable.ic_bookmark_active else R.drawable.ic_bookmark_inactive

    Icon(
        painter = painterResource(id = bookmarkImage),
        contentDescription = "Bookmark",
        tint = Color.Unspecified,
        modifier = modifier
            .size(24.dp)
    )
}

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .toggleable(  // 버튼 누를 때 clickable과 달리 음성 안내가 true/false를 안내해줌
                value = isBookmarked,
                role = Role.Checkbox,  // 체크/토글 상태를 나타내는 표준 롤
                onValueChange = { onBookmarkClick() }
            ),
        contentAlignment = Alignment.Center,
    ) {
        BookmarkIcon(
            isBookmarked = isBookmarked
        )
    }
}
