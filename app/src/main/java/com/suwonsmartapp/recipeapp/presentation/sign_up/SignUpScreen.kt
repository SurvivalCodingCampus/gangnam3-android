package com.suwonsmartapp.recipeapp.presentation.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun SignUpHeader(
    modifier: Modifier = Modifier,
    title: String = "Create an account",
    subtitle: String = "Let’s help you set up your account,\nit won’t take long."
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            color = AppColors.black1,
            style = AppTextStyles.largeTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        
        Text(
            text = subtitle,
            color = AppColors.black2,
            style = AppTextStyles.smallerTextRegular,
        )
        
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen()
}