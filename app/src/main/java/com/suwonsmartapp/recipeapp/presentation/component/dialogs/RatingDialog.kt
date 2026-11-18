package com.suwonsmartapp.recipeapp.presentation.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RatingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onChange: (Int) -> Unit,
) {
    Dialog(  // 기존 화면 위에 어두운 배경과 함께 팝업 창 띄움
        onDismissRequest = onDismissRequest,
    ) {
        RatingCard(onChange = onChange)
    }
}

@Composable
fun RatingCard(
    modifier: Modifier = Modifier,
    onChange: (Int) -> Unit,
) {
    var selectedRating by remember { mutableIntStateOf(0) }  // default는 0점
    
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
                style = AppTextStyles.smallerTextRegular,
                textAlign = TextAlign.Center,
            )
            
            // 별점
            RatingStarBar(
                rating = selectedRating,
            ) {  // onRatingChange =
                selectedRating = it
            }
            
            // 버튼
            RatingSendButton(
                enable = selectedRating > 0,
            ) {  // onClick = 아래 로직
                if (selectedRating in 1..5) {
                    onChange(selectedRating)
                }
            }
        }
    }
}

@Composable
fun RatingSendButton(
    modifier: Modifier = Modifier,
    text: String = "Send",
    enable: Boolean = true,  // 버튼 활성화 여부
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // 상태 우선순위에 따라 결정 : 비활성화 > 눌림 중 > 기본 상태
    val buttonColor = when {
        !enable -> AppColors.gray4
        isPressed -> AppColors.gray4
        else -> AppColors.rating
    }
    
    Box(
        modifier = modifier
            .width(61.dp)
            .height(20.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = buttonColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,  // 물결 효과 끄기
                enabled = enable,  // false일 때는 클릭,눌림 효과 막아줌
                onClick = onClick,
            )
            .padding(
                horizontal = 20.dp,
                vertical = 4.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = AppColors.white,
            style = AppTextStyles.smallLabelRegular,
            textAlign = TextAlign.Center,
        )
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
            val interactionSource = remember { MutableInteractionSource() }
            // 각 별마다 독립적인 interactionSource 생성. 아니면 첫 번째 별에 포커스 가서 회색 배경 생김
            
            Icon(
                imageVector = if (isSelected) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = "Score $score",
                tint = AppColors.rating,
                modifier = Modifier
                    .size(20.dp)
                    .selectable(
                        selected = isSelected,
                        interactionSource = interactionSource,  // 기본 포커스/클릭 회색 배경 제거
                        indication = null,  // 기본 포커스/클릭 회색 배경 제거
                        role = Role.RadioButton,
                        onClick = {
                            onRatingChange(score)
                        }
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingCardPreview() {
    Column {
        RatingCard(
            onChange = {}
        )
    }
}