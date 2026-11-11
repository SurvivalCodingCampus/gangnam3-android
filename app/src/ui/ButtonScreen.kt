@Composable
fun ButtonScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Big Button
        RecipeButton(
            text = "Button",
            size = ButtonSize.BIG,
            onClick = { /* 빅 버튼 클릭 처리 */ }
        )

        // Medium Button
        RecipeButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            onClick = { /* 미디엄 버튼 클릭 처리 */ }
        )

        // Small Button
        RecipeButton(
            text = "Button",
            size = ButtonSize.SMALL,
            onClick = { /* 스몰 버튼 클릭 처리 */ }
        )
    }
}