package com.suwonsmartapp.recipeapp.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
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
        horizontalArrangement = Arrangement.spacedBy(
            space = 7.dp,
            alignment = Alignment.CenterHorizontally,
        ),
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
        SocialButton(
            imageResId = R.drawable.ic_community_google,
            contentDescription = "Continue with Google",
            onClick = onGoogleClick,
        )
        
        // Facebook
        SocialButton(
            imageResId = R.drawable.ic_community_facebook,
            contentDescription = "Continue with Facebook",
            onClick = onFaceBookClick,
        )
    }
}

@Composable
fun SocialButton(
    @DrawableRes imageResId: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(44.dp),
        shape = RoundedCornerShape(10.dp),
        color = AppColors.white,  // 배경색
        shadowElevation = 5.dp,  // 흐린 정도
    ) {
        Box(  // Surface 특성상 정렬 등의 이유로 Box 쓰는 경우가 많음
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
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