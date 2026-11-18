package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.presentation.component.images.RoundedImage
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun TextDivider(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier.width(50.dp),
            thickness = 1.dp,
            color = AppColors.gray4,
        )
        
        Text(
            text = text,
            color = AppColors.gray4,
            style = AppTextStyles.smallerTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        
        HorizontalDivider(
            modifier = Modifier.width(50.dp),
            thickness = 1.dp,
            color = AppColors.gray4,
        )
    }
}

@Composable
fun SocialAuthButtons(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit,
    onFaceBookClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    val shadowColor = Color(0xFF696969).copy(alpha = 0.1f)
    val buttonModifier = Modifier
        .shadow(
            elevation = 5.dp,
            shape = shape,
            ambientColor = shadowColor,
            spotColor = shadowColor,
        )
        .clip(shape = shape)
    
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 25.dp,
            alignment = Alignment.CenterHorizontally,
        )
    ) {
        // Google
        Box(
            modifier = buttonModifier
                .clickable(
                    onClick = onGoogleClick,
                )
        ) {
            RoundedImage(
                imageId = R.drawable.ic_community_google,
                contentDescription = "Continue with Google",
            )
        }
        
        // Facebook
        Box(
            modifier = buttonModifier
                .clickable(
                    onClick = onFaceBookClick,
                )
        ) {
            RoundedImage(
                imageId = R.drawable.ic_community_facebook,
                contentDescription = "Continue with Facebook",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SocialAuthButtonsPreview() {
    SocialAuthButtons(
        onGoogleClick = {},
        onFaceBookClick = {}
    )
}