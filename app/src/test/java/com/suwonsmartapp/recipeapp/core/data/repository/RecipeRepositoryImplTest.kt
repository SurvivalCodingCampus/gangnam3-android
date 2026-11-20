package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.RecipeError
import com.suwonsmartapp.recipeapp.core.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeRepositoryImplTest {
    private val repository: RecipeRepository = RecipeRepositoryImpl(FakeRecipeDataSource())

    private suspend fun getAllRecipes(): List<Recipe> =
        (repository.getRecipes() as Result.Success).value

    @Test
    fun `getRecipes - 전체 레시피 10개를 반환한다`() = runTest {
        val recipes = getAllRecipes()

        assertEquals(10, recipes.size)
        assertEquals((1L..10L).toList(), recipes.map { it.id })
    }

    @Test
    fun `getRecipes - 첫 번째 레시피 필드가 JSON과 일치한다`() = runTest {
        val recipe = getAllRecipes().first()

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
    fun `getRecipes - 재료가 없는 레시피는 빈 리스트로 파싱된다`() = runTest {
        val recipe = getAllRecipes().first { it.id == 4L }

        assertEquals("Ttekbokki", recipe.name)
        assertTrue(recipe.ingredients.isEmpty())
    }

    @Test
    fun `getRecipes - JSON이 깨지면 PARSE_ERR를 반환한다`() = runTest {
        val brokenRepository = RecipeRepositoryImpl(FakeRecipeDataSource(recipesJson = "{ broken json"))

        assertEquals(Result.Error(RecipeError.PARSE_ERR), brokenRepository.getRecipes())
    }

    @Test
    fun `getRecipes - 5xx 응답이면 SERVER_ERR를 반환한다`() = runTest {
        val serverErrorRepository = RecipeRepositoryImpl(FakeRecipeDataSource(statusCode = 500))

        assertEquals(Result.Error(RecipeError.SERVER_ERR), serverErrorRepository.getRecipes())
    }

    @Test
    fun `getRecipes - 4xx 응답이면 CLIENT_ERR를 반환한다`() = runTest {
        val clientErrorRepository = RecipeRepositoryImpl(FakeRecipeDataSource(statusCode = 404))

        assertEquals(Result.Error(RecipeError.CLIENT_ERR), clientErrorRepository.getRecipes())
    }

    @Test
    fun `getRecipe - id로 해당 레시피를 반환한다`() = runTest {
        val recipe = (repository.getRecipe(6L) as Result.Success).value

        assertEquals(6L, recipe.id)
        assertEquals("Beef Wellington", recipe.name)
        assertEquals("Gordon Ramsay", recipe.chef)
        assertEquals(5.0, recipe.rating, 0.0)
    }

    @Test
    fun `getRecipe - 모든 id에 대해 getRecipes 결과와 같은 레시피를 반환한다`() = runTest {
        getAllRecipes().forEach { expected ->
            assertEquals(Result.Success(expected), repository.getRecipe(expected.id))
        }
    }

    @Test
    fun `getRecipe - 없는 id면 NOT_FOUND_ERR를 반환한다`() = runTest {
        assertEquals(Result.Error(RecipeError.NOT_FOUND_ERR), repository.getRecipe(999L))
    }
}
