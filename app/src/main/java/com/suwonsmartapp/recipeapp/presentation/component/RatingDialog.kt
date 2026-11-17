package com.suwonsmartapp.recipeapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppIcons
import com.suwonsmartapp.recipeapp.ui.TextStyles

// Opens the dialog as a popup and shows the score it returns.
@Preview(showBackground = true)
@Composable
fun RatingDialogPreview() {
    var isOpen by remember { mutableStateOf(true) }
    var rating by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Rating: $rating",
            color = AppColors.black1,
            style = TextStyles.smallTextRegular,
        )
        FilterButton(
            text = "Open dialog",
            isSelected = isOpen,
            onClick = {
                isOpen = true
            },
        )
    }

    if (isOpen) {
        RatingDialog(
            title = "Rate recipe",
            actionName = "Send",
            onChange = {
                rating = it
                isOpen = false
            },
            onDismissRequest = {
                isOpen = false
            },
        )
    }
}

// Both states side by side: before (0 points) and after choosing a score.
@Preview(showBackground = true)
@Composable
fun RatingDialogContentPreview() {
    Row(
        modifier = Modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        RatingDialogContent(
            title = "Rate recipe",
            actionName = "Send",
            score = 0,
            onScoreChange = {},
            onActionClick = {},
        )
        RatingDialogContent(
            title = "Rate recipe",
            actionName = "Send",
            score = 4,
            onScoreChange = {},
            onActionClick = {},
        )
    }
}

/**
 * Rating dialog (popup).
 * The score starts at 0, which cannot be sent. Choosing 1..5 stars enables the
 * action button, and pressing it returns the score through [onChange].
 */
@Composable
fun RatingDialog(
    title: String,
    actionName: String,
    onChange: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var score by remember { mutableIntStateOf(0) }

    Dialog(onDismissRequest = onDismissRequest) {
        RatingDialogContent(
            title = title,
            actionName = actionName,
            score = score,
            onScoreChange = {
                score = it
            },
            onActionClick = {
                onChange(score)
            },
            // Room for the shadow inside the dialog window.
            modifier = Modifier.padding(16.dp),
        )
    }
}

/**
 * Rating dialog card (stateless).
 * Layout: 170 x 91, radius 8, padding 10 (vertical) / 15 (horizontal), gap 5.
 */
@Composable
fun RatingDialogContent(
    title: String,
    actionName: String,
    score: Int,
    onScoreChange: (Int) -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRated = score in 1..5

    Column(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = AppColors.white,
                shape = RoundedCornerShape(8.dp),
            )
            .width(170.dp)
            .height(91.dp)
            .padding(
                start = 15.dp,
                top = 10.dp,
                end = 15.dp,
                bottom = 10.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = AppColors.black1,
            style = TextStyles.smallerTextRegular,
        )

        // Stars 1..5: filled up to the chosen score
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (star in 1..5) {
                Icon(
                    imageVector = if (star <= score) AppIcons.Bold.star else AppIcons.Outline.star,
                    contentDescription = "$star star",
                    tint = AppColors.rating,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onScoreChange(star)
                            },
                        ),
                )
            }
        }

        // Action button: disabled (gray) while the score is 0
        Box(
            modifier = Modifier
                .height(20.dp)
                .background(
                    color = if (isRated) AppColors.rating else AppColors.gray4,
                    shape = RoundedCornerShape(6.dp),
                )
                .clickable(
                    enabled = isRated,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onActionClick,
                )
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = actionName,
                color = AppColors.white,
                style = TextStyles.smallerTextRegular.copy(fontSize = 8.sp),
            )
        }
    }
}
