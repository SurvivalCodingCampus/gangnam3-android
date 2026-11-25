package com.suwonsmartapp.recipeapp

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.suwonsmartapp.recipeapp.data.sampleRecipes
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** 회전(저장 상태 복원) 후에도 사용자 선택이 유지되는지 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class StateRestorationTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun checkedIngredientSurvivesRestoration() {
        val recipe = sampleRecipes.first()
        val tester = StateRestorationTester(compose)
        tester.setContent {
            RecipeAppTheme {
                RecipeDetailScreen(recipe, saved = false, onToggleBookmark = {}, onBack = {}, onShare = {})
            }
        }
        compose.onNodeWithText(recipe.ingredients[0].name).performScrollTo().performClick().assertIsOn()

        // 화면 회전처럼 상태를 저장한 뒤 새로 만들어 복원한다.
        tester.emulateSavedInstanceStateRestore()

        compose.onNodeWithText(recipe.ingredients[0].name).assertIsOn()
        compose.onNodeWithText(recipe.ingredients[1].name).assertIsOff()
    }
}
