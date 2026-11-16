package com.suwonsmartapp.recipeapp.presentation.component.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
) {
    val bookmarkImage = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder
    val bookmarkColor = if (isBookmarked) AppColors.primary80 else AppColors.gray4
    
    Icon(
        imageVector = bookmarkImage,
        contentDescription = "Bookmark",
        tint = bookmarkColor,
        modifier = Modifier
            .size(24.dp)
            .clickable(
                onClick = onClick
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun BookmarkButtonPreview() {
    var isBookmarked1 by remember { mutableStateOf(false) }
    var isBookmarked2 by remember { mutableStateOf(true) }
    
    Column {
        BookmarkButton(
            isBookmarked = isBookmarked1,
        ) {
            isBookmarked1 = !isBookmarked1
        }
        
        BookmarkButton(
            isBookmarked = isBookmarked2,
        ) {
            isBookmarked2 = !isBookmarked2
        }
    }
}
