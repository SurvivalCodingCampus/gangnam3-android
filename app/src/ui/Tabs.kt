import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomTabs(
    labels: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        labels.forEachIndexed { index, label ->
            val isSelected = (index == selectedIndex)

            ElevatedButton(
                onClick = { onValueChange(index) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = if (isSelected) Color(0xFF129575) else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Gray
                )
            ) {
                Text(text = label)
            }
        }
    }
}