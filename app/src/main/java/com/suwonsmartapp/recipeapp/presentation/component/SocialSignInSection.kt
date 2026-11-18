package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun SocialSignInSectionPreview() {
    SocialSignInSection(
        onGoogleClick = {},
        onFacebookClick = {},
    )
}

// "Or Sign in With" 구분선 + 구글/페이스북 버튼 (Sign In, Sign Up 화면 공통)
@Composable
fun SocialSignInSection(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 구분선 + Or Sign in With
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DividerLine()
            Text(
                modifier = Modifier.padding(horizontal = 7.dp),
                text = "Or Sign in With",
                color = AppColors.gray4,
                style = AppTextStyles.smallerTextBold,
            )
            DividerLine()
        }

        // 소셜 로그인 버튼
        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            SocialButton(
                iconResId = R.drawable.ic_google,
                description = "Google",
                onClick = onGoogleClick,
            )
            SocialButton(
                iconResId = R.drawable.ic_facebook,
                description = "Facebook",
                onClick = onFacebookClick,
            )
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(1.dp)
            .background(AppColors.gray4)
    )
}

@Composable
private fun SocialButton(
    iconResId: Int,
    description: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .clip(RoundedCornerShape(10.dp))
            .background(AppColors.white)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconResId),
            contentDescription = description,
        )
    }
}
