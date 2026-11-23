package com.suwonsmartapp.recipeapp.data.mapper

import com.suwonsmartapp.recipeapp.data.dto.RecipeDto
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeMapperTest {

    @Test
    fun `null 필드는 기본값으로, 범위 밖 평점은 0~5로 보정된다`() {
        assertEquals(
            Recipe(0, "", "", "", "", "", 0.0),
            RecipeDto().toModel(),
        )
        assertEquals(5.0, RecipeDto(rating = 9.0).toModel().rating, 0.0)
    }
}
