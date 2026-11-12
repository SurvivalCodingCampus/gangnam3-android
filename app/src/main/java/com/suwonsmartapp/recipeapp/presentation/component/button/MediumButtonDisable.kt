package com.suwonsmartapp.recipeapp.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppIcons
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun MediumButtonDisablePreview() {
    MediumButtonDisable("Sign In")
}

/**
 * Medium button (disabled state).
 * Same layout as MediumButton. Background: #D9D9D9 (gray4). Not clickable.
 */
@Composable
fun MediumButtonDisable(
    text: String,
) {
    Box(
        modifier = Modifier
            .width(243.dp)
            .height(54.dp)
            .background(
                color = AppColors.gray4,
                shape = RoundedCornerShape(10.dp),
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(114.dp)
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = AppColors.white,
                    style = TextStyles.smallerTextBold,
                )
            }

            Spacer(modifier = Modifier.width(9.dp))

            Icon(
                imageVector = AppIcons.Bold.arrowRight,
                contentDescription = null,
                tint = AppColors.white,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
