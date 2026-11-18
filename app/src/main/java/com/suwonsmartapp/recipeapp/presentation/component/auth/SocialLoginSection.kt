package com.suwonsmartapp.recipeapp.presentation.component.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppIcons
import com.suwonsmartapp.recipeapp.ui.TextStyles

/**
 * "Or Sign in With" 구분선과 소셜 로그인 버튼 묶음.
 * SignInScreen, SignUpScreen 에서 공통으로 사용한다.
 */
@Composable
fun SocialLoginSection(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            HorizontalDivider(
                modifier = Modifier.width(50.dp),
                thickness = 1.dp,
                color = AppColors.gray4,
            )
            Text(
                text = "Or Sign in With",
                color = AppColors.gray4,
                style = TextStyles.smallerTextSemiBold,
            )
            HorizontalDivider(
                modifier = Modifier.width(50.dp),
                thickness = 1.dp,
                color = AppColors.gray4,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            SocialButton(
                iconRes = AppIcons.Social.google,
                contentDescription = "Sign in with Google",
                onClick = onGoogleClick,
            )
            SocialButton(
                iconRes = AppIcons.Social.facebook,
                contentDescription = "Sign in with Facebook",
                onClick = onFacebookClick,
            )
        }
    }
}

@Composable
fun SocialButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val shape = RoundedCornerShape(10.dp)

    // 순서: 그림자 -> 모양대로 자르기 -> 배경 -> 클릭 (ripple 이 모서리 밖으로 나가지 않는다)
    Box(
        modifier = modifier
            .size(44.dp)
            .shadow(elevation = 4.dp, shape = shape)
            .clip(shape)
            .background(AppColors.white)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SocialLoginSectionPreview() {
    SocialLoginSection(modifier = Modifier.padding(20.dp))
}
