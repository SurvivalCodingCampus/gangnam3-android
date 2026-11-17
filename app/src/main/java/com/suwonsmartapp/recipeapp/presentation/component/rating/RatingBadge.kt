package com.suwonsmartapp.recipeapp.presentation.component.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .width(37.dp)
            .height(16.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = AppColors.secondary20)
            .padding(
                vertical = 2.dp,
                horizontal = 7.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.StarRate,
            contentDescription = "Star rate",
            tint = AppColors.rating,
            modifier = Modifier
                .size(8.dp)
        )
        
        Text(
            text = "$rating",
            color = AppColors.black1,
            style = AppTextStyles.smallLabelRegular,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingBadgePreview() {
    val rating = 4.0
    
    RatingBadge(rating)
}