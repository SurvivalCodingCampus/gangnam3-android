package com.suwonsmartapp.recipeapp.core.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeRecipeRepositoryTest {
    private val repository: RecipeRepository = FakeRecipeRepository()

    @Test
    fun `getRecipes - 전체 레시피 10개를 반환한다`() = runBlocking {
        val recipes = repository.getRecipes()

        assertEquals(10, recipes.size)
        assertEquals((1L..10L).toList(), recipes.map { it.id })
    }

    @Test
    fun `getRecipes - 첫 번째 레시피 필드가 JSON과 일치한다`() = runBlocking {
        val recipe = repository.getRecipes().first()

        assertEquals(1L, recipe.id)
        assertEquals("Indian", recipe.category)
        assertEquals("Traditional spare ribs baked", recipe.name)
        assertEquals(
            "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            recipe.image,
        )
        assertEquals("Chef John", recipe.chef)
        assertEquals("20 min", recipe.time)
        assertEquals(4.0, recipe.rating, 0.0)

        assertEquals(4, recipe.ingredients.size)
        val first = recipe.ingredients.first()
        assertEquals(3L, first.ingredient.id)
        assertEquals("Pork", first.ingredient.name)
        assertEquals(500, first.amount)
    }

    @Test
    fun `getRecipes - 재료가 없는 레시피는 빈 리스트로 파싱된다`() = runBlocking {
        val recipe = repository.getRecipes().first { it.id == 4L }

        assertEquals("Ttekbokki", recipe.name)
        assertTrue(recipe.ingredients.isEmpty())
    }

    @Test
    fun `getRecipe - id로 해당 레시피를 반환한다`() = runBlocking {
        val recipe = repository.getRecipe(6L)

        assertEquals(6L, recipe.id)
        assertEquals("Beef Wellington", recipe.name)
        assertEquals("Gordon Ramsay", recipe.chef)
        assertEquals(5.0, recipe.rating, 0.0)
    }

    @Test
    fun `getRecipe - 모든 id에 대해 getRecipes 결과와 같은 레시피를 반환한다`() = runBlocking {
        repository.getRecipes().forEach { expected ->
            assertEquals(expected, repository.getRecipe(expected.id))
        }
    }

    @Test(expected = NoSuchElementException::class)
    fun `getRecipe - 없는 id면 NoSuchElementException을 던진다`() {
        runBlocking { repository.getRecipe(999L) }
    }
}
