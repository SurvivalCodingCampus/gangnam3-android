package com.example.gangnam3.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.gangnam3.presentation.saved_recipes.SavedRecipesScreen
import com.example.gangnam3.presentation.saved_recipes.SavedRecipesUiState
import com.example.gangnam3.presentation.search_recipes.SearchRecipesScreen
import com.example.gangnam3.presentation.search_recipes.SearchRecipesState
import com.example.gangnam3.presentation.sign_up.SignUpScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** 실습 화면의 빈 상태·오류 상태·콜백 전달을 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w375dp-h812dp-mdpi")
class PracticeScreensTest {
    @get:Rule val compose = createComposeRule()

    private fun showSearch(
        state: SearchRecipesState,
        onQueryChange: (String) -> Unit = {},
        onFilterClick: () -> Unit = {}
    ) = compose.setContent {
        SearchRecipesScreen(state, onQueryChange, onBackClick = {}, onFilterClick = onFilterClick)
    }

    @Test
    fun `검색 결과가 없으면 안내 문구를 보여준다`() {
        showSearch(SearchRecipesState(query = "없는 요리"))

        compose.onNodeWithText("검색 결과가 없습니다.").assertIsDisplayed()
        compose.onNodeWithText("0 results").assertIsDisplayed()
    }

    @Test
    fun `검색 로드가 실패하면 오류 문구를 보여준다`() {
        showSearch(SearchRecipesState(errorMessage = "네트워크 오류"))

        compose.onNodeWithText("네트워크 오류").assertIsDisplayed()
    }

    @Test
    fun `검색어 입력과 필터 클릭이 콜백으로 전달된다`() {
        var query = ""
        var filterClicked = false
        showSearch(SearchRecipesState(), { query = it }, { filterClicked = true })

        compose.onNodeWithText("Search recipe").performTextInput("lamb")
        compose.onNodeWithContentDescription("Filter").performClick()

        compose.runOnIdle { assertEquals("lamb", query); assertTrue(filterClicked) }
    }

    @Test
    fun `저장 목록이 비면 안내 문구를 보여준다`() {
        compose.setContent { SavedRecipesScreen(SavedRecipesUiState(), onBookmarkClick = {}) }

        compose.onNodeWithText("저장한 레시피가 없습니다.").assertIsDisplayed()
    }

    @Test
    fun `약관을 누르면 반대 값이 전달된다`() {
        var accepted = false
        compose.setContent {
            SignUpScreen(
                name = "", email = "", password = "", confirmPassword = "",
                isTermsAccepted = false,
                onNameChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmPasswordChange = {},
                onTermsAcceptedChange = { accepted = it },
                onSignUpClick = {}, onGoogleSignInClick = {}, onFacebookSignInClick = {}, onSignInClick = {}
            )
        }

        compose.onNodeWithText("Accept terms & Condition").performScrollTo().performClick()

        compose.runOnIdle { assertTrue(accepted) }
    }
}
