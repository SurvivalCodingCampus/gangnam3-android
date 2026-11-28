package com.suwonsmartapp.recipeapp

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import com.suwonsmartapp.recipeapp.data.RecipeDatabase
import com.suwonsmartapp.recipeapp.data.RoomRecipeRepository
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** 홈·검색 화면이 Room 데이터, 상세 화면, 저장 탭과 실제로 이어지는지 앱 전체로 검증한다. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w375dp-h812dp-mdpi")
class AppRootTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()
    private lateinit var db: RecipeDatabase

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        // 온보딩을 마친 상태에서 시작한다.
        context.getSharedPreferences("recipeapp_prefs", Context.MODE_PRIVATE)
            .edit().putBoolean("onboarding_done", true).commit()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        val repository = RoomRecipeRepository(db)
        compose.setContent { RecipeAppTheme { AppRoot(repository) } }
        waitForText(CARBONARA)
    }

    @After
    fun tearDown() = db.close()

    /** DB 조회는 다른 스레드에서 끝나므로 화면에 나타날 때까지 기다린다. */
    private fun waitForText(text: String) = compose.waitUntil(5_000) {
        compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
    }

    private fun back() = compose.runOnUiThread { compose.activity.onBackPressedDispatcher.onBackPressed() }

    @Test
    fun `홈 카드를 누르면 상세가 열리고 뒤로가면 홈으로 돌아온다`() {
        compose.onNodeWithText(CARBONARA).performClick()
        compose.onNodeWithText("Share").assertIsDisplayed()

        back()

        compose.onNodeWithText("What are you cooking today?").assertIsDisplayed()
    }

    @Test
    fun `홈에서 북마크하면 DB에 저장되어 저장 탭에 보인다`() {
        compose.onAllNodesWithContentDescription("Bookmark").onFirst().performClick()
        compose.waitUntil(5_000) {
            compose.onAllNodesWithContentDescription("Remove bookmark").fetchSemanticsNodes().isNotEmpty()
        }

        compose.onNodeWithText("Saved").performClick()

        compose.onNodeWithText("Saved Recipes").assertIsDisplayed()
        compose.onNodeWithText(CARBONARA).assertIsDisplayed()
    }

    @Test
    fun `홈 카테고리는 DB의 분류를 사용한다`() {
        compose.onNodeWithText("Mexican").performClick()

        compose.onNodeWithText("Tacos al Pastor").assertIsDisplayed()
        compose.onNodeWithText(CARBONARA).assertDoesNotExist()
        compose.onNodeWithText("Chinese").assertDoesNotExist()
    }

    @Test
    fun `검색 결과에서 상세를 열고 뒤로가면 검색어가 유지되며 다시 뒤로가면 홈이다`() {
        compose.onNodeWithText("Search recipe").performClick()
        compose.onNodeWithText("Search recipes").assertIsDisplayed()
        compose.onNodeWithText("Search recipe").performTextInput("ramen")
        waitForText("1 results")
        compose.onNodeWithText("Miso Ramen").performClick()
        compose.onNodeWithText("Share").assertIsDisplayed()

        back()
        compose.onNodeWithText("ramen").assertIsDisplayed()

        back()
        compose.onNodeWithText("What are you cooking today?").assertIsDisplayed()
    }

    @Test
    fun `검색 필터는 DB의 분류로 결과를 거른다`() {
        compose.onNodeWithContentDescription("Filter").performClick()
        compose.onNodeWithContentDescription("Filter").performClick()
        compose.onNodeWithText("Mexican").performClick()
        compose.onNode(hasText("Filter") and hasClickAction()).performClick()

        compose.onNodeWithText("Tacos al Pastor").assertIsDisplayed()
        compose.onNodeWithText(CARBONARA).assertDoesNotExist()
    }

    @Test
    fun `검색 중 홈 탭을 누르면 홈으로 돌아온다`() {
        compose.onNodeWithText("Search recipe").performClick()

        compose.onNodeWithText("Home").performClick()

        compose.onNodeWithText("What are you cooking today?").assertIsDisplayed()
    }

    private companion object {
        const val CARBONARA = "Classic Spaghetti Carbonara"
    }
}
