package com.suwonsmartapp.recipeapp.presentation.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.presentation.component.buttons.SmallButton
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.TextStyles

@Composable
fun RatingDialog(
    modifier: Modifier = Modifier,
    initialRating: Int = 0,
    onChange: (Int) -> Unit,
) {
    var selectedRating by remember { mutableIntStateOf(initialRating.coerceIn(0, 5)) }

    Card(
        modifier = modifier
            .width(170.dp)
            .height(91.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.white,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Rate recipe",
                color = AppColors.black2,
                style = TextStyles.smallerTextRegular,
                textAlign = TextAlign.Center,
            )

            // 별점
            RatingStarBar(
                rating = selectedRating,
            ) {  // onRatingChange =
                selectedRating = it
            }

            // 버튼
            SmallButton(
                text = "Send",
                enable = selectedRating > 0,
                containerColor = AppColors.rating,
                modifier = Modifier
                    .width(61.dp)
                    .height(20.dp)
            ) {  // onClick = 아래 로직
                if (selectedRating in 1..5) {
                    onChange(selectedRating)
                }
            }
        }
    }
}

@Composable
fun RatingStarBar(
    rating: Int,
    modifier: Modifier = Modifier,
    onRatingChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        for (score in 1..5) {
            val isSelected = score <= rating

            Icon(
                imageVector = if (isSelected) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = "Score $score",
                tint = AppColors.rating,
                modifier = Modifier
                    .size(20.dp)
                    .selectable(
                        selected = isSelected,
                        role = Role.RadioButton,
                        onClick = {
                            onRatingChange(score)
                        }
                    ),
            )
        }
    }
}

@Preview(name = "기본")
@Composable
private fun RatingDialogPreview() {
    RatingDialog(onChange = {})
}

@Preview(name = "별 3개")
@Composable
private fun RatingDialogThreeStarsPreview() {
    RatingDialog(initialRating = 3, onChange = {})
}
