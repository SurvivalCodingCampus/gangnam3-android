package com.suwonsmartapp.recipeapp

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suwonsmartapp.recipeapp.presentation.component.buttons.RatingButton
import com.suwonsmartapp.recipeapp.ui.AppColors
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Clicks RatingButton with hoisted state (same setup as RatingButtonPreview)
 * and checks that the background colour toggles.
 */
@RunWith(AndroidJUnit4::class)
class RatingButtonTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun click_togglesSelectedColours() {
        composeRule.setContent {
            var isSelected by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(20.dp),
            ) {
                RatingButton(
                    text = "5",
                    isSelected = isSelected,
                    onClick = {
                        isSelected = !isSelected
                    },
                )
            }
        }

        val button = composeRule.onNodeWithText("5")

        // Unselected: white background
        val before = button.captureToImage()
        save(before, "rating_button_1_unselected.png")
        assertEquals(AppColors.white.toArgb(), backgroundOf(before))

        // First click: selected, primary100 background
        button.performClick()
        composeRule.waitForIdle()
        val selected = button.captureToImage()
        save(selected, "rating_button_2_selected.png")
        assertEquals(AppColors.primary100.toArgb(), backgroundOf(selected))

        // Second click: back to unselected
        button.performClick()
        composeRule.waitForIdle()
        val after = button.captureToImage()
        save(after, "rating_button_3_unselected_again.png")
        assertEquals(AppColors.white.toArgb(), backgroundOf(after))
    }

    /** Colour of a pixel inside the start padding, away from the border, text and icon. */
    private fun backgroundOf(image: ImageBitmap): Int {
        val x = with(composeRule.density) { 5.dp.roundToPx() }
        return image.toPixelMap()[x, image.height / 2].toArgb()
    }

    private fun save(image: ImageBitmap, name: String) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val outputDir = InstrumentationRegistry.getArguments().getString("additionalTestOutputDir")
        val dir = if (outputDir != null) File(outputDir) else instrumentation.targetContext.filesDir
        dir.mkdirs()
        File(dir, name).outputStream().use {
            image.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
}
