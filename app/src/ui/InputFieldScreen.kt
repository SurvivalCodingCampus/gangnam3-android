Kotlin
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun InputFieldScreen() {
    // 상위에서 관리하는 입력값 상태
    var textState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecipeInputField(
            label = "Label",
            placeholder = "Placeholder",
            value = textState,
            onValueChange = { newValue ->
                textState = newValue // 글자 변경 시 콜백을 통해 상위 상태 업데이트
            }
        )
    }
}