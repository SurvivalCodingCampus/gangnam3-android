package com.suwonsmartapp.recipeapp.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppIcons
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun BigButtonPreview() {
    BigButton("Sign In") {
        println("클릭!!")
    }
}

/**
 * Big button.
 * Layout: 315 x 60, radius 10, padding 18 (vertical) / 85 (horizontal), gap 11.
 */
@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(
                color = AppColors.primary100,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 85.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = AppColors.white,
                style = TextStyles.normalTextBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )

            Icon(
                imageVector = AppIcons.Bold.arrowRight,
                contentDescription = null,
                tint = AppColors.white,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
