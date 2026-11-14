package com.suwonsmartapp.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme

/** 명세 §3.1 스플래시 & 온보딩 — Start Cooking → 홈. */
@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        // 내용이 화면보다 길어지면(작은 기기/큰 글자) 스크롤, 짧으면 세로 중앙 정렬.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RecipeMedia(
                emoji = "🍳",
                modifier = Modifier.fillMaxWidth(),
                height = 170.dp,
                emojiSize = 64.sp,
                tint = colorScheme.primary.copy(alpha = 0.12f),
                rounded = 24.dp
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.onboarding_title),
                modifier = Modifier.semantics { heading() },
                fontSize = 30.sp,
                lineHeight = 38.sp, // 2줄이 되어도 겹치지 않게
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.onboarding_subtitle),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.onboarding_quote),
                fontSize = 14.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center,
                color = colorScheme.onBackground
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onStart,
                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.onboarding_cta), fontSize = 16.sp)
            }
        }
    }
}

@Preview(name = "Onboarding", showBackground = true)
@Composable
fun OnboardingPreview() {
    RecipeAppTheme {
        OnboardingScreen(onStart = {})
    }
}
