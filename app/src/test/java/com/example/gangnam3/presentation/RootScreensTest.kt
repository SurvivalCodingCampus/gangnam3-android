package com.example.gangnam3.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.gangnam3.presentation.home.HomeRoot
import com.example.gangnam3.presentation.home.HomeScreen
import com.example.gangnam3.presentation.home.HomeState
import com.example.gangnam3.presentation.search_recipes.SearchRecipesRoot
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** Root가 Factory로 ViewModel을 만들고 상태와 이벤트를 화면에 연결하는지 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w375dp-h812dp-mdpi")
class RootScreensTest {
    // viewModel()에는 ViewModel을 보관할 액티비티가 필요하다.
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `홈에서 카테고리를 누르면 해당 레시피만 보인다`() {
        compose.setContent { HomeRoot(onSearchClick = {}) }
        compose.onNodeWithText("Traditional spare ribs baked").assertIsDisplayed()

        compose.onNodeWithText("Indian").performClick()

        compose.onNodeWithText("Spicy Chicken Tikka").assertIsDisplayed()
        compose.onNodeWithText("Traditional spare ribs baked").assertDoesNotExist()
    }

    @Test
    fun `홈의 검색 영역과 필터 버튼은 검색 이동을 요청한다`() {
        var clicks = 0
        compose.setContent { HomeRoot(onSearchClick = { clicks++ }) }

        compose.onNodeWithText("Search recipe").performClick()
        compose.onNodeWithContentDescription("Filter").performClick()

        compose.runOnIdle { assertEquals(2, clicks) }
    }

    @Test
    fun `홈은 빈 결과를 안내한다`() {
        compose.setContent { HomeScreen(HomeState(), {}, {}, {}) }

        compose.onNodeWithText("해당 카테고리의 레시피가 없습니다.").assertIsDisplayed()
    }

    @Test
    fun `홈은 오류 문구를 우선 표시한다`() {
        compose.setContent { HomeScreen(HomeState(errorMessage = "네트워크 오류"), {}, {}, {}) }

        compose.onNodeWithText("네트워크 오류").assertIsDisplayed()
        compose.onNodeWithText("해당 카테고리의 레시피가 없습니다.").assertDoesNotExist()
    }

    @Test
    fun `검색에서 필터를 적용하면 시트가 닫히고 결과가 바뀐다`() {
        compose.setContent { SearchRecipesRoot(onBackClick = {}) }
        compose.onNodeWithText("Traditional spare ribs baked").assertIsDisplayed()

        compose.onNodeWithContentDescription("Filter").performClick()
        compose.onNodeWithText("Chinese").performClick()
        compose.onNodeWithText("Filter").performClick()

        compose.onNodeWithText("Filter Search").assertDoesNotExist()
        compose.onNodeWithText("Sweet & Sour Pork").assertIsDisplayed()
        compose.onNodeWithText("Traditional spare ribs baked").assertDoesNotExist()
    }
}
