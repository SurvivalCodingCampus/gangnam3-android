enum class ButtonSize {
    BIG, MEDIUM, SMALL
}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeButton(
    text: String,
    size: ButtonSize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 크기별 높이, 패딩, 글자 크기 설정
    val (height, horizontalPadding, fontSize, showArrow) = when (size) {
        ButtonSize.BIG -> Quad(56.dp, 24.dp, 16.sp, true)
        ButtonSize.MEDIUM -> Quad(48.dp, 20.dp, 14.sp, true)
        ButtonSize.SMALL -> Quad(36.dp, 16.dp, 12.sp, false)
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .fillMaxWidth(), // 혹은 필요에 따라 wrapContentWidth()
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0F9D58) // 이미지 속 초록색톤 예시
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = fontSize
            )
            if (showArrow) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "→",
                    color = Color.White,
                    fontSize = fontSize
                )
            }
        }
    }
}

