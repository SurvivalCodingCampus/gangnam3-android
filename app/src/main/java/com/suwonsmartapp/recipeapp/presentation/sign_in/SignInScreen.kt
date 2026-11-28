package com.suwonsmartapp.recipeapp.presentation.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .safeDrawingPadding()
            .padding(
                top = 50.dp,
                end = 30.dp,
                bottom = 65.dp,
                start = 30.dp,
            ),
    ) {
        Text(
            text = stringResource(id = R.string.sign_in_hello),
            style = AppTextStyles.headerTextBold,
        )
        Text(
            text = stringResource(id = R.string.sign_in_welcome_back),
            style = AppTextStyles.largeTextRegular,
            color = AppColors.black2,
        )
        Spacer(modifier = Modifier.height(height = 57.dp))
        Text(
            text = stringResource(id = R.string.sign_in_email),
            style = AppTextStyles.smallTextRegular,
            color = AppColors.black2,
        )
        Spacer(modifier = Modifier.height(height = 5.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = stringResource(id = R.string.sign_in_enter_email),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
            },
            textStyle = AppTextStyles.smallerTextRegular,
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.gray4,
                focusedBorderColor = AppColors.gray4,
            ),
        )
        Spacer(modifier = Modifier.height(height = 30.dp))
        Text(
            text = stringResource(id = R.string.sign_in_password),
            style = AppTextStyles.smallTextRegular,
            color = AppColors.black2,
        )
        Spacer(modifier = Modifier.height(height = 5.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = stringResource(id = R.string.sign_in_enter_password),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
            },
            textStyle = AppTextStyles.smallerTextRegular,
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.gray4,
                focusedBorderColor = AppColors.gray4,
            ),
        )
        Spacer(modifier = Modifier.height(height = 20.dp))
        Text(
            text = stringResource(id = R.string.sign_in_forgot_password),
            style = AppTextStyles.smallerTextRegular,
            color = AppColors.secondary100,
        )
        Spacer(modifier = Modifier.height(height = 25.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppColors.primary100,
                    shape = RoundedCornerShape(size = 10.dp),
                )
                .clickable(onClick = onSignInClick)
                .padding(vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.sign_in_button),
                style = AppTextStyles.normalTextBold,
                color = AppColors.white,
            )
            Spacer(modifier = Modifier.width(width = 11.dp))
            Icon(
                modifier = Modifier.size(size = 20.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow forward",
                tint = AppColors.white,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(weight = 1f),
                thickness = 1.dp,
                color = AppColors.gray4,
            )
            Text(
                modifier = Modifier.padding(horizontal = 7.dp),
                text = stringResource(id = R.string.sign_in_or_with),
                style = AppTextStyles.smallerTextSemiBold,
                color = AppColors.gray4,
            )
            HorizontalDivider(
                modifier = Modifier.weight(weight = 1f),
                thickness = 1.dp,
                color = AppColors.gray4,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .dropShadow(
                        shape = RoundedCornerShape(10.dp), shadow = Shadow(
                            radius = 5.dp,
                            spread = 3.dp,
                            color = AppColors.shadow.copy(alpha = 0.1f),
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                    )
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(size = 10.dp),
                    )
                    .padding(all = 10.dp)
            ) {
                Image(
                    modifier = Modifier.size(size = 24.dp),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                )
            }
            Spacer(modifier = Modifier.width(width = 25.dp))
            Box(
                modifier = Modifier
                    .dropShadow(
                        shape = RoundedCornerShape(10.dp), shadow = Shadow(
                            radius = 5.dp,
                            spread = 3.dp,
                            color = AppColors.shadow.copy(alpha = 0.1f),
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                    )
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(size = 10.dp),
                    )
                    .padding(all = 10.dp)
            ) {
                Image(
                    modifier = Modifier.size(size = 24.dp),
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "Facebook Icon",
                )
            }
        }
        Spacer(modifier = Modifier.height(height = 55.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.sign_in_have_account),
                style = AppTextStyles.smallerTextSemiBold,
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                modifier = Modifier.clickable(onClick = onSignUpClick),
                text = stringResource(id = R.string.sign_in_or_up),
                style = AppTextStyles.smallerTextSemiBold,
                color = AppColors.secondary100,
            )
        }
    }
}