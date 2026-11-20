package com.suwonsmartapp.recipeapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = stringResource(id = R.string.splash_background),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 60.dp,
                    end = 50.dp,
                    bottom = 50.dp,
                    start = 50.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(size = 79.dp),
                painter = painterResource(id = R.drawable.chef_hat),
                contentDescription = stringResource(id = R.string.splash_chef_hat),
            )
            Spacer(modifier = Modifier.height(height = 14.dp))
            Text(
                text = stringResource(id = R.string.splash_header),
                style = AppTextStyles.mediumTextBold,
                color = AppColors.white,
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            Text(
                text = stringResource(id = R.string.splash_content),
                style = AppTextStyles.titleTextBold,
                color = AppColors.white,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(height = 20.dp))
            Text(
                text = stringResource(id = R.string.splash_content_description),
                style = AppTextStyles.normalTextRegular,
                color = AppColors.white,
            )
            Spacer(modifier = Modifier.height(height = 64.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppColors.primary100,
                        shape = RoundedCornerShape(size = 10.dp),
                    )
                    .padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.splash_start_cooking),
                    style = AppTextStyles.normalTextBold,
                    color = AppColors.white,
                )
                Spacer(modifier = Modifier.width(width = 9.dp))
                Icon(
                    modifier = Modifier.size(size = 20.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(id = R.string.splash_arrow_forward),
                    tint = AppColors.white,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}