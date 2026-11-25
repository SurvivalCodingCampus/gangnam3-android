package com.suwonsmartapp.recipeapp.core.data.mapper

import com.suwonsmartapp.recipeapp.core.data.dto.IngredientDto
import com.suwonsmartapp.recipeapp.core.data.dto.RecipeDto
import com.suwonsmartapp.recipeapp.core.data.dto.RecipeIngredientDto
import com.suwonsmartapp.recipeapp.core.data.model.Ingredient
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import com.suwonsmartapp.recipeapp.core.data.model.RecipeIngredient
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipeMapperTest {

    private val recipe = Recipe(
        id = 1L,
        category = "Indian",
        name = "Traditional spare ribs baked",
        image = "https://example.com/ribs.jpg",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0,
        ingredients = listOf(
            RecipeIngredient(
                ingredient = Ingredient(3L, "Pork", "https://example.com/pork.jpg"),
                amount = 500,
            ),
        ),
    )

    @Test
    fun `유효한 DTO는 값 그대로 Model로 변환된다`() {
        val dto = RecipeDto(
            id = 1L,
            category = "Indian",
            name = "Traditional spare ribs baked",
            image = "https://example.com/ribs.jpg",
            chef = "Chef John",
            time = "20 min",
            rating = 4.0,
            ingredients = listOf(
                RecipeIngredientDto(
                    ingredient = IngredientDto(3L, "Pork", "https://example.com/pork.jpg"),
                    amount = 500,
                ),
            ),
        )

        assertThat(dto.toModel()).isEqualTo(recipe)
    }

    @Test
    fun `모든 필드가 null이어도 예외 없이 기본값 Model이 만들어진다`() {
        val model = RecipeDto().toModel()

        assertThat(model).isEqualTo(Recipe(0L, "", "", "", "", "", 0.0, emptyList()))
    }

    @Test
    fun `JSON에 ingredients가 없으면 빈 리스트로 변환된다`() {
        val dto = Json.decodeFromString<RecipeDto>("""{ "id": 1 }""")

        assertThat(dto.toModel().ingredients).isEmpty()
    }

    @Test
    fun `RecipeIngredientDto의 ingredient가 null이면 기본값 Ingredient로 변환된다`() {
        val model = RecipeIngredientDto(ingredient = null, amount = null).toModel()

        assertThat(model).isEqualTo(RecipeIngredient(Ingredient(0L, "", ""), 0))
    }

    @Test
    fun `Model을 DTO로 변환하면 모든 필드가 그대로 옮겨진다`() {
        val dto = recipe.toDto()

        assertThat(dto.id).isEqualTo(1L)
        assertThat(dto.category).isEqualTo("Indian")
        assertThat(dto.chef).isEqualTo("Chef John")
        assertThat(dto.rating).isEqualTo(4.0)
        assertThat(dto.ingredients!!.first().ingredient!!.name).isEqualTo("Pork")
        assertThat(dto.ingredients!!.first().amount).isEqualTo(500)
    }

    @Test
    fun `유효한 Model은 DTO를 거쳐 다시 변환해도 동일하다`() {
        assertThat(recipe.toDto().toModel()).isEqualTo(recipe)
    }
}
