package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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




@Preview(showBackground = true, backgroundColor = 0xFFFFAD30)
@Composable
private fun Rating0DialogContentPreview() {
    var selectedRating by rememberSaveable { mutableIntStateOf(0) }

    RatingDialogContent(
        title = "Rate recipe",
        actionName = "Send",
        rating = selectedRating,
        onRatingChange = { rating ->
            selectedRating = rating
        },
        onSendClick = { rating ->
            println("별점: $rating")
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFAD30)
@Composable
private fun Rating3DialogContentPreview() {
    var selectedRating by rememberSaveable { mutableIntStateOf(3) }

    RatingDialogContent(
        title = "Rate recipe",
        actionName = "Send",
        rating = selectedRating,
        onRatingChange = { rating ->
            selectedRating = rating
        },
        onSendClick = { rating ->
            println("별점: $rating")
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun RatingDialogPreview() {
    var selectedRating by rememberSaveable { mutableIntStateOf(0) }

    RatingDialog(
        title = "Rate recipe",
        actionName = "Send",
        rating = selectedRating,
        onRatingChange = { rating ->
            selectedRating = rating
        },
        onSendClick = { rating ->
            println("별점: $rating")
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
    // 바깥 영역 클릭이나 뒤로가기 시 onDismissRequest 호출
    Dialog(onDismissRequest = onDismissRequest) {
        RatingDialogContent(
            title = title,
            actionName = actionName,
            rating = rating,
            onRatingChange = onRatingChange,
            onSendClick = onSendClick,
        )
    }
}

@Composable
private fun RatingDialogContent(
    title: String,
    actionName: String,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    onSendClick: (Int) -> Unit,
) {
    val isEnabled = rating > 0
    val sendButtonColor = if (isEnabled) AppColors.rating else AppColors.gray4

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
        Text(
            modifier = Modifier
                .width(61.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(sendButtonColor)
                .clickable(enabled = isEnabled) { onSendClick(rating) }
                .wrapContentSize(), // 가운데 정렬
            text = actionName,
            color = AppColors.white,
            style = AppTextStyles.smallerTextRegular,
        )
    }
}
