import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = if (isSelected) Color(0xFF129575) else Color.White,
            contentColor = if (isSelected) Color.White else Color(0xFF129575)
        )
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
fun RatingButton(
    rating: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = if (isSelected) Color(0xFF129575) else Color.White,
            contentColor = if (isSelected) Color.White else Color(0xFF129575)
        )
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(text = "$rating", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                tint = if (isSelected) Color.White else Color(0xFF129575)
            )
        }
    }
}

// 상태 호이스팅 프리뷰 검증
@Preview(showBackground = true)
@Composable
fun ButtonStateHoistingPreview() {
    var isFilterSelected by remember { mutableStateOf(false) }
    var isRatingSelected by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterButton(
            text = "All",
            isSelected = isFilterSelected,
            onClick = { isFilterSelected = !isFilterSelected }
        )
        RatingButton(
            rating = 5,
            isSelected = isRatingSelected,
            onClick = { isRatingSelected = !isRatingSelected }
        )
    }
}