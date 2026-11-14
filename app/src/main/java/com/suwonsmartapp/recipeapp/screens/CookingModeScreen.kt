package com.suwonsmartapp.recipeapp.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.suwonsmartapp.recipeapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import com.suwonsmartapp.recipeapp.ui.theme.RecipePrimary
import com.suwonsmartapp.recipeapp.ui.theme.RecipeSurfaceVariant

/**
 * 명세 §4 플로우의 최종 단계 "Step-by-Step Cooking Mode"용 전체화면 스텝 뷰어.
 * 명세(§3)에 독립 화면 정의가 없어 레이아웃은 와이어프레임 수준 자체 결정(계획 문서 기록):
 * 상단 진행 표시("단계 n/N") + 단계 텍스트 + (준비 시) 단계 이미지 슬롯 + 엄지 존 하단 이전/다음/완료.
 * 상태 없는(stateless) 뷰어 — 현재 인덱스/이동/완료는 호출자(AppRoot)가 소유, 재료 체크 상태와 독립.
 * @param steps 조리 단계 문구 목록(비었으면 "단계 정보 없음" + 완료만 노출)
 * @param stepImages 단계 이미지 URL 목록(스키마 step_img_url 대응 자리 — 길이 불일치 안전 처리)
 * @param index 현재 단계 인덱스(호출자 소유)
 */
@Composable
fun CookingModeScreen(
    steps: List<String>,
    stepImages: List<String> = emptyList(),
    index: Int,
    onStepChange: (Int) -> Unit,
    onDone: () -> Unit
) {
    val safeIndex = index.coerceIn(0, (steps.size - 1).coerceAtLeast(0))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onDone) { Text(stringResource(R.string.cooking_close)) }
            Spacer(Modifier.weight(1f))
            Text(
                if (steps.isEmpty()) stringResource(R.string.cooking_steps_label)
                else stringResource(R.string.cooking_step_counter, safeIndex + 1, steps.size),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val step = steps.getOrNull(safeIndex)
            if (step == null) {
                Text(
                    stringResource(R.string.cooking_no_steps),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    stringResource(R.string.cooking_step_label, safeIndex + 1),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = RecipePrimary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    step,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            // 단계 이미지 슬롯 — 데이터(스키마 step_img_url) 준비 전 자리표시, 로딩/실패 상태 후속.
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(RecipeSurfaceVariant, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (stepImages.isEmpty()) stringResource(R.string.cooking_image_pending) else stringResource(R.string.cooking_image_placeholder),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { if (safeIndex > 0) onStepChange(safeIndex - 1) },
                enabled = safeIndex > 0,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RecipeSurfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = RecipeSurfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            ) { Text(stringResource(R.string.cooking_prev), fontSize = 15.sp) }
            Button(
                onClick = {
                    if (steps.isNotEmpty() && safeIndex < steps.size - 1) onStepChange(safeIndex + 1)
                    else onDone()
                },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RecipePrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    if (steps.isEmpty() || safeIndex >= steps.size - 1) stringResource(R.string.cooking_done) else stringResource(R.string.cooking_next),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Preview(name = "Cooking Mode", showBackground = true)
@Composable
private fun CookingModePreview() {
    RecipeAppTheme {
        CookingModeScreen(
            steps = listOf(
                "스파게티를 소금물에 알덴테로 삶는다.",
                "볼에 계란·치즈·소금을 섞어 크림을 만든다.",
                "불 끈 팬에서 면과 반죽을 빠르게 버무린다.",
                "후추를 뿌려 마무리한다."
            ),
            index = 0,
            onStepChange = {},
            onDone = {}
        )
    }
}
