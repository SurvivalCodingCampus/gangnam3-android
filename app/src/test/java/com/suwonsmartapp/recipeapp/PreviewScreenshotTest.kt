package com.suwonsmartapp.recipeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Density
import com.github.takahirom.roborazzi.captureRoboImage
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.SampleRecipeRepository
import com.suwonsmartapp.recipeapp.screens.CookingModeScreen
import com.suwonsmartapp.recipeapp.screens.HomeScreen
import com.suwonsmartapp.recipeapp.screens.OnboardingScreen
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.screens.SavedScreen
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * 기기 없이 실제 Compose 화면을 PNG로 렌더링한다(Robolectric native graphics + Roborazzi).
 * 결과는 app/build/preview-site 아래 PNG — 로컬 URL 갤러리가 이 파일들을 서빙한다.
 * 375×812(mdpi)로 Figma/명세 프레임과 동일한 크기.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w375dp-h812dp-mdpi")
class PreviewScreenshotTest {
    @get:Rule val compose = createComposeRule()
    private val recipes = SampleRecipeRepository.recipes()
    private val bookmarks = recipes.take(3).map { it.id }

    @Composable
    private fun theme(
        dark: Boolean = false,
        fontScale: Float = 1f,
        content: @Composable () -> Unit
    ) {
        RecipeAppTheme(darkTheme = dark) {
            // 실제 앱(AppRoot)의 Scaffold Surface와 동일한 배경/콘텐츠 색을 제공한다.
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                val base = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(base.density, fontScale)
                ) { content() }
            }
        }
    }

    private fun capture(name: String) {
        compose.waitForIdle()
        compose.onRoot().captureRoboImage("build/preview-site/$name.png")
    }

    @Test
    fun onboardingLight() {
        compose.setContent { theme { OnboardingScreen(onStart = {}) } }
        capture("onboarding-light")
    }

    @Test
    fun onboardingDark() {
        compose.setContent { theme(dark = true) { OnboardingScreen(onStart = {}) } }
        capture("onboarding-dark")
    }

    @Test
    fun homeLight() {
        compose.setContent {
            theme {
                HomeScreen(
                    recipes = recipes,
                    query = "",
                    onQueryChange = {},
                    filter = FilterState(),
                    onFilterChange = {},
                    onOpenFilter = {},
                    bookmarks = bookmarks,
                    onToggleBookmark = {},
                    onOpenRecipe = {},
                    onOpenProfile = {}
                )
            }
        }
        capture("home-light")
    }

    @Test
    fun homeDark() {
        compose.setContent {
            theme(dark = true) {
                HomeScreen(
                    recipes = recipes,
                    query = "",
                    onQueryChange = {},
                    filter = FilterState(),
                    onFilterChange = {},
                    onOpenFilter = {},
                    bookmarks = bookmarks,
                    onToggleBookmark = {},
                    onOpenRecipe = {},
                    onOpenProfile = {}
                )
            }
        }
        capture("home-dark")
    }

    @Test
    fun savedLight() {
        compose.setContent {
            theme {
                SavedScreen(
                    recipes = recipes,
                    bookmarks = bookmarks,
                    onToggleBookmark = {},
                    onClearAll = {},
                    onOpenRecipe = {}
                )
            }
        }
        capture("saved-light")
    }

    @Test
    fun detailLight() {
        compose.setContent {
            theme {
                RecipeDetailScreen(
                    recipe = recipes.first(),
                    saved = true,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        capture("detail-light")
    }

    @Test
    fun detailDark() {
        compose.setContent {
            theme(dark = true) {
                RecipeDetailScreen(
                    recipe = recipes.first(),
                    saved = true,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        capture("detail-dark")
    }

    @Test
    fun detailInstructionsLight() {
        compose.setContent {
            theme {
                RecipeDetailScreen(
                    recipe = recipes.first(),
                    saved = false,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        compose.onNodeWithText("Instructions").performClick()
        capture("detail-instructions-light")
    }

    @Test
    fun cookingLight() {
        compose.setContent {
            theme {
                CookingModeScreen(
                    steps = recipes.first().steps,
                    index = 1,
                    onStepChange = {},
                    onDone = {}
                )
            }
        }
        capture("cooking-light")
    }

    @Test
    fun cookingDark() {
        compose.setContent {
            theme(dark = true) {
                CookingModeScreen(
                    steps = recipes.first().steps,
                    index = 1,
                    onStepChange = {},
                    onDone = {}
                )
            }
        }
        capture("cooking-dark")
    }

    @Test
    fun onboardingFont200() {
        compose.setContent { theme(fontScale = 2f) { OnboardingScreen(onStart = {}) } }
        capture("onboarding-font200")
    }

    @Test
    fun detailFont200() {
        compose.setContent {
            theme(fontScale = 2f) {
                RecipeDetailScreen(
                    recipe = recipes.first(),
                    saved = true,
                    onToggleBookmark = {},
                    onBack = {},
                    onShare = {}
                )
            }
        }
        capture("detail-font200")
    }
}
