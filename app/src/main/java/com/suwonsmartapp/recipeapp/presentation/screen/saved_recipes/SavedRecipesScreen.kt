package com.suwonsmartapp.recipeapp.presentation.screen.saved_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()  // 기기 시스템 영역 여백 먼저 확보
            .padding(
                // 디자인 여백 적용
                vertical = 10.dp,
                horizontal = 30.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.CenterVertically,
        )
    ){
        Text(
            text = "Saved recipes",
            color = AppColors.black2,
            style = AppTextStyles.mediumTextBold.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp) 이건지 카드의 위아래로 패딩이 10씩 있는건지?
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}