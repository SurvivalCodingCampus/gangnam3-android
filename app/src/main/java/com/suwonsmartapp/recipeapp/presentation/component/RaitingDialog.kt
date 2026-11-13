package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
private fun RatingDialogPreview() {
    Box(modifier = Modifier.padding(20.dp)) {
        RatingDialog(
            title = "Rate recipe",
            actionName = "Send",
            onChange = { score ->
                println("별점: $score")
            },
        )
    }
}

@Composable
fun RatingDialog(
    title: String,
    actionName: String,
    onChange: (Int) -> Unit,
) {
    // 기본값 0점 (0점은 전송 불가)
    var score by remember { mutableIntStateOf(0) }
    val isEnabled = score > 0

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AppColors.white)
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        // TODO: 속성체크
    ) {
        Text(
            text = title,
            color = AppColors.black1,
            style = AppTextStyles.smallTextRegular,
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 별 5개 (1 ~ 5점)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (star in 1..5) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { score = star },
                    imageVector = if (star <= score) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "별점 ${star}점",
                    tint = AppColors.rating,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // 전송 버튼 (0점이면 회색 + 클릭 불가)
        Box(
            modifier = Modifier
                .width(61.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isEnabled) AppColors.rating else AppColors.gray4)
                .clickable(enabled = isEnabled) { onChange(score) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = actionName,
                color = AppColors.white,
                style = AppTextStyles.smallerTextRegular,
            )
        }
    }
}
