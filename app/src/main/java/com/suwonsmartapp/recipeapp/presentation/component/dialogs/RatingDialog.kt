package com.suwonsmartapp.recipeapp.presentation.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.suwonsmartapp.recipeapp.presentation.component.buttons.SmallButton
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RatingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onChange: (Int) -> Unit,
) {
    var selectedRating by remember { mutableStateOf(0) }  // default는 0점
    
    Dialog(  // 기존 화면 위에 어두운 배경과 함께 팝업 창 띄움
        onDismissRequest = onDismissRequest,
    ) {
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
                Row() { }
                
                // 버튼
                SmallButton(
                    text = "Send",
                    enable = selectedRating > 0,
                    containerColor = AppColors.rating,
                ) {  // onClick = 아래 로직
                    if (selectedRating in 1..5) {
                        onChange(selectedRating)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingDialogPreview() {
    Column {
        Text(
            text = "Hello",
            modifier = Modifier.size(200.dp)
        )
        
        RatingDialog(
            onDismissRequest = {},
            onChange = {}
        )
    }
}