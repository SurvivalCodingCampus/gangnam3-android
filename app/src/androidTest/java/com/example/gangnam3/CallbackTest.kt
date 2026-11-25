package com.example.gangnam3

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CallbackTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 버튼_클릭_콜백이_호출되는지_테스트() {
        var callbackExecuted = false

        composeTestRule.setContent {
            Button(onClick = {
                callbackExecuted = true
            }) {
                Text("클릭하세요")
            }
        }

        // 버튼 클릭
        composeTestRule
            .onNodeWithText("클릭하세요")
            .performClick()

        // 콜백이 실행되었는지 확인
        assertTrue(callbackExecuted)
    }
}