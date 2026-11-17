package com.suwonsmartapp.recipeapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Preview(showBackground = true)
@Composable
fun RatingDialogPreview() {
    var rating by remember { mutableIntStateOf(0) }

    RatingDialog(
        title = "Rate recipe",
        actionName = "Send",
        rating = rating,
        onRatingChange = { rating = it },
        onChange = {},
    )
}

@Composable
fun RatingDialog(
    title: String,
    actionName: String,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = AppColors.white,
                shape = RoundedCornerShape(size = 8.dp),
            )
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = TextStyles.smallTextRegular,
            color = AppColors.black2,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(space = 10.dp)) {
            (1..5).forEach { score ->
                Icon(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                        ) { onRatingChange(score) },
                    painter = painterResource(
                        id = if (score <= rating) R.drawable.star else R.drawable.star_outline,
                    ),
                    contentDescription = "$score",
                    tint = AppColors.rating,
                )
            }
        }
        Text(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 6.dp))
                .background(color = if (rating > 0) AppColors.rating else AppColors.gray4)
                .clickable(enabled = rating > 0) { onChange(rating) }
                .padding(horizontal = 20.dp, vertical = 4.dp),
            text = actionName,
            style = TextStyles.smallerTextRegular,
            color = AppColors.white,
        )
    }
}
