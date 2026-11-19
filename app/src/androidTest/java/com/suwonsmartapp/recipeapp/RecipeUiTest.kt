package com.suwonsmartapp.recipeapp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.SampleRecipeRepository
import com.suwonsmartapp.recipeapp.data.TimeFilter
import com.suwonsmartapp.recipeapp.screens.HeartToggle
import com.suwonsmartapp.recipeapp.screens.HomeScreen
import com.suwonsmartapp.recipeapp.screens.OnboardingScreen
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.screens.SavedScreen
import com.suwonsmartapp.recipeapp.screens.SearchFilterSheet
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeUiTest {
    @get:Rule val compose = createComposeRule()

    private fun text(id: Int) = InstrumentationRegistry.getInstrumentation().targetContext.getString(id)

    @Test fun bookmarkExposesStateAndCanBeToggled() {
        compose.setContent {
            RecipeAppTheme {
                var saved by remember { mutableStateOf(false) }
                HeartToggle(saved = saved, onClick = { saved = !saved })
            }
        }
        compose.onNodeWithContentDescription(text(R.string.bookmark_save)).assertIsOff().performClick()
        compose.onNodeWithContentDescription(text(R.string.bookmark_remove)).assertIsOn().performClick()
        compose.onNodeWithContentDescription(text(R.string.bookmark_save)).assertIsOff()
    }

    @Test fun savedRecipeOpensDetailWithoutRemovingBookmark() {
        val recipe = SampleRecipeRepository.recipes().first()
        var opened: String? = null
        var toggles = 0
        compose.setContent {
            RecipeAppTheme {
                SavedScreen(
                    recipes = SampleRecipeRepository.recipes(),
                    bookmarks = listOf(recipe.id),
                    onToggleBookmark = { toggles++ },
                    onClearAll = {},
                    onOpenRecipe = { opened = it }
                )
            }
        }
        compose.onNodeWithText(recipe.title).performClick()
        compose.runOnIdle { assertEquals(recipe.id, opened); assertEquals(0, toggles) }
    }

    @Test fun filterChangesAreOnlyCommittedOnApply() {
        var applied: FilterState? = null
        compose.setContent {
            RecipeAppTheme { SearchFilterSheet(FilterState(), { applied = it }, {}) }
        }
        compose.onNodeWithText(TimeFilter.Under15.label).performClick()
        compose.runOnIdle { assertNull(applied) }
        compose.onNodeWithText(text(R.string.filter_apply)).performScrollTo().performClick()
        compose.runOnIdle { assertEquals(TimeFilter.Under15, applied?.time) }
    }

    @Test fun closingFilterDoesNotApplyPendingSelection() {
        var dismissed = false
        var applied = false
        compose.setContent {
            RecipeAppTheme { SearchFilterSheet(FilterState(), { applied = true }, { dismissed = true }) }
        }
        compose.onNodeWithText(TimeFilter.Under30.label).performClick()
        compose.onNodeWithText(text(R.string.filter_close)).performClick()
        compose.runOnIdle { assertTrue(dismissed); assertEquals(false, applied) }
    }

    @Test fun onboardingCtaStartsHomeFlow() {
        var started = false
        compose.setContent {
            RecipeAppTheme { OnboardingScreen(onStart = { started = true }) }
        }
        compose.onNodeWithText(text(R.string.onboarding_title)).assertIsDisplayed()
        compose.onNodeWithText(text(R.string.onboarding_quote)).assertIsDisplayed()
        compose.onNodeWithText(text(R.string.onboarding_cta)).performClick()
        compose.runOnIdle { assertTrue(started) }
    }

    @Test fun unmatchedSearchShowsRecoveryHint() {
        compose.setContent {
            RecipeAppTheme {
                HomeScreen(
                    recipes = SampleRecipeRepository.recipes(),
                    query = "__no_matching_recipe__",
                    onQueryChange = {},
                    filter = FilterState(),
                    onFilterChange = {},
                    onOpenFilter = {},
                    bookmarks = emptyList(),
                    onToggleBookmark = {},
                    onOpenRecipe = {},
                    onOpenProfile = {}
                )
            }
        }
        compose.onNodeWithText(text(R.string.search_empty)).performScrollTo().assertIsDisplayed()
        compose.onNodeWithText(text(R.string.search_empty_hint)).assertIsDisplayed()
    }

    @Test fun detailIngredientRowTogglesAsOneTarget() {
        val recipe = SampleRecipeRepository.recipes().first()
        val ingredient = recipe.ingredients.first()
        compose.setContent {
            RecipeAppTheme {
                RecipeDetailScreen(
                    recipe = recipe,
                    saved = false,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        // 체크박스+이름+계량이 한 행으로 합쳐져 하나의 토글 대상이 된다.
        compose.onNodeWithText(ingredient.name).performScrollTo().assertIsOff().performClick().assertIsOn()
    }

    @Test fun detailTabsExposeSelectedState() {
        val recipe = SampleRecipeRepository.recipes().first()
        val ingredientsLabel = text(R.string.detail_tab_ingredients).format(recipe.ingredients.size)
        compose.setContent {
            RecipeAppTheme {
                RecipeDetailScreen(
                    recipe = recipe,
                    saved = false,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        compose.onNodeWithText(ingredientsLabel).assertIsSelected()
        compose.onNodeWithText(text(R.string.detail_tab_instructions)).assertIsNotSelected()
            .performClick().assertIsSelected()
        compose.onNodeWithText(ingredientsLabel).assertIsNotSelected()
    }

    @Test fun detailStaysUsableAtTwoHundredPercentFontScale() {
        val recipe = SampleRecipeRepository.recipes().first()
        compose.setContent {
            RecipeAppTheme {
                val density = LocalDensity.current
                CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale = 2f)) {
                    RecipeDetailScreen(
                        recipe = recipe,
                        saved = false,
                        onToggleBookmark = {},
                        onBack = {},
                        onShare = {}
                    )
                }
            }
        }
        // 글자 200%에서도 스크롤로 하단 CTA에 도달할 수 있어야 한다.
        compose.onNodeWithText(text(R.string.detail_cta)).performScrollTo().assertIsDisplayed()
    }
}
