package com.suwonsmartapp.recipeapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.screens.CookingModeScreen
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.screens.SavedScreen
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** 화면이 상태를 올바르게 그리고, 사용자 동작을 콜백으로 전달하는지 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w375dp-h812dp-mdpi")
class RecipeScreensTest {
    @get:Rule val compose = createComposeRule()

    private val steps = listOf("면 삶기", "버무리기")

    @Test
    fun `조리 모드 첫 단계에서는 이전이 막히고 다음은 다음 인덱스를 알린다`() {
        var moved: Int? = null
        compose.setContent {
            RecipeAppTheme { CookingModeScreen(steps, index = 0, onStepChange = { moved = it }, onDone = {}) }
        }

        compose.onNodeWithText("이전").assertIsNotEnabled()
        compose.onNodeWithText("다음").performClick()

        compose.runOnIdle { assertEquals(1, moved) }
    }

    @Test
    fun `조리 모드 마지막 단계에서는 완료가 호출된다`() {
        var done = false
        compose.setContent {
            RecipeAppTheme { CookingModeScreen(steps, index = 1, onStepChange = {}, onDone = { done = true }) }
        }

        compose.onNodeWithText("이전").assertIsEnabled()
        compose.onNodeWithText("완료").performClick()

        compose.runOnIdle { assertTrue(done) }
    }

    @Test
    fun `조리 모드는 범위를 벗어난 인덱스를 마지막 단계로 보정한다`() {
        compose.setContent {
            RecipeAppTheme { CookingModeScreen(steps, index = 99, onStepChange = {}, onDone = {}) }
        }

        compose.onNodeWithText("버무리기").assertIsDisplayed()
    }

    @Test
    fun `저장 목록이 비면 안내가 보이고 전체 삭제가 막힌다`() {
        compose.setContent {
            RecipeAppTheme { SavedScreen(sampleRecipes, emptyList(), onToggleBookmark = {}, onClearAll = {}) }
        }

        compose.onNodeWithText("저장한 레시피가 없습니다.").assertIsDisplayed()
        compose.onNodeWithText("Clear all").assertIsNotEnabled()
    }

    @Test
    fun `저장 목록에는 북마크한 레시피만 보인다`() {
        compose.setContent {
            RecipeAppTheme { SavedScreen(sampleRecipes, listOf("r2"), onToggleBookmark = {}, onClearAll = {}) }
        }

        compose.onNodeWithText("Thai Green Curry").assertIsDisplayed()
        compose.onNodeWithText("Miso Ramen").assertDoesNotExist()
    }

    @Test
    fun `상세 화면의 공유와 조리 시작이 콜백으로 전달된다`() {
        var shared = false
        var started = false
        compose.setContent {
            RecipeAppTheme {
                RecipeDetailScreen(
                    recipe = sampleRecipes.first(),
                    saved = false,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = { shared = true },
                    onStartCooking = { started = true }
                )
            }
        }

        compose.onNodeWithText("Share").performClick()
        compose.onNodeWithText("Start Cooking").performScrollTo().performClick()

        compose.runOnIdle { assertTrue(shared); assertTrue(started) }
    }
}
