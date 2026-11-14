package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.ui.theme.AppColors
import com.suwonsmartapp.recipeapp.ui.theme.AppTextStyles

@Preview(showBackground = true)
@Composable
private fun RatingDialogPreview() {
    var rating by rememberSaveable { mutableIntStateOf(0) }

    RatingDialog(
        title = "Rate recipe",
        actionName = "Send",
        rating = rating,
        onRatingChange = { star ->
            rating = star
        },
        onSendClick = { star ->
            println("별점: $star")
        },
        onDismissRequest = {},
    )
}

@Composable
fun RatingDialog(
    title: String,
    actionName: String,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    onSendClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val isEnabled = rating > 0
    val sendButtonColor = if (isEnabled) AppColors.rating else AppColors.gray4

    // 바깥 영역 클릭이나 뒤로가기 시 onDismissRequest 호출
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(AppColors.white)
                .padding(vertical = 10.dp, horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            // Rate recipe 글씨
            Text(
                text = title,
                color = AppColors.black2,
                style = AppTextStyles.smallTextRegular,
            )

            // 별점
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                for (star in 1..5) {
                    val starIcon = if (star <= rating)  {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.StarOutline
                    }

                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onRatingChange(star) },
                        imageVector = starIcon,
                        contentDescription = "별점 ${star}점",
                        tint = AppColors.rating,
                    )
                }
            }

            // 전송 버튼
            Box(
                modifier = Modifier
                    .width(61.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(sendButtonColor)
                    .clickable(enabled = isEnabled) { onSendClick(rating) },
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
}
