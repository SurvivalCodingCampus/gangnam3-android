package com.example.gangnam3.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

private val PrimaryGreen = Color(0xFF129575)

// 배경 이미지 URL (프리뷰에서는 placeholder로 안전하게 표시)
private const val SPLASH_BACKGROUND_IMAGE_URL =
    "https://cdn.pixabay.com/photo/2017/02/15/10/57/salad-2068220_1280.jpg"

@Composable
fun SplashScreen(
    onStartCookingClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundImageUrl: String = SPLASH_BACKGROUND_IMAGE_URL
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A241E))
    ) {
        // 1. 배경 음식 재료 사진 (Coil AsyncImage + 프리뷰 placeholder 지원)
        AsyncImage(
            model = backgroundImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
            error = painterResource(android.R.drawable.ic_menu_gallery),
            modifier = Modifier.fillMaxSize()
        )

        // 2. 텍스트 가독성을 높이기 위한 상하단 다크 그라데이션 오버레이
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Black.copy(alpha = 0.35f),
                        0.5f to Color.Black.copy(alpha = 0.25f),
                        1.0f to Color.Black.copy(alpha = 0.85f)
                    )
                )
        )

        // 3. 전경 콘텐츠 레이아웃
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.1f))

            // 상단 브랜딩 아이콘 & 문구 ("100K+ Premium Recipe")
            Icon(
                imageVector = Icons.Outlined.Restaurant,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "100K+ Premium Recipe",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1.5f))

            // 중앙 메인 타이틀 ("Get Cooking") 및 서브 타이틀
            Text(
                text = "Get\nCooking",
                fontSize = 48.sp,
                lineHeight = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Simple way to find Tasty Recipe",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.92f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.9f))

            // 하단 액션 버튼 ("Start Cooking ->")
            Button(
                onClick = onStartCookingClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(54.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Start Cooking",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(44.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onStartCookingClick = {})
}