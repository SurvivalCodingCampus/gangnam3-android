package com.suwonsmartapp.recipeapp.presentation.component.labels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun CookingTimeLabel(
    cookingTimeMinute: Int,
) {
    Row(
        modifier = Modifier
            .width(60.dp)
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        CompositionLocalProvider(  // 스타일 일괄 적용
            LocalContentColor provides AppColors.gray4,
            LocalTextStyle provides AppTextStyles.smallerTextRegular,
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "Timer",
                modifier = Modifier
                    .size(17.dp)
            )
            
            Spacer(Modifier.size(5.dp))
            
            Text(
                text = "$cookingTimeMinute",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f, fill = false)
            )
            
            Text(
                text = " min",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CookingTimeLabelPreview() {
    val cookingTimeMinute1 = 20
    val cookingTimeMinute2 = 20000000
    
    Column {
        CookingTimeLabel(cookingTimeMinute1)
        CookingTimeLabel(cookingTimeMinute2)
    }
}