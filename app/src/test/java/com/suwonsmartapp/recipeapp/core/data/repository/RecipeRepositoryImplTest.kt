package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeRecipeDataSource
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipeRepositoryImplTest {
    private val repository: RecipeRepository = RecipeRepositoryImpl(FakeRecipeDataSource())

    private suspend fun getAllRecipes(): List<Recipe> =
        (repository.getRecipes() as Result.Success).value

    @Test
    fun `getRecipes - 전체 레시피 10개를 반환한다`() = runTest {
        val recipes = getAllRecipes()

        assertThat(recipes).hasSize(10)
        assertThat(recipes.map { it.id }).containsExactlyElementsOf(1L..10L)
    }

    @Test
    fun `getRecipes - 첫 번째 레시피 필드가 JSON과 일치한다`() = runTest {
        val recipe = getAllRecipes().first()

        assertThat(recipe.id).isEqualTo(1L)
        assertThat(recipe.category).isEqualTo("Indian")
        assertThat(recipe.name).isEqualTo("Traditional spare ribs baked")
        assertThat(recipe.image)
            .isEqualTo("https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg")
        assertThat(recipe.chef).isEqualTo("Chef John")
        assertThat(recipe.time).isEqualTo("20 min")
        assertThat(recipe.rating).isEqualTo(4.0)

        assertThat(recipe.ingredients).hasSize(4)
        val first = recipe.ingredients.first()
        assertThat(first.ingredient.id).isEqualTo(3L)
        assertThat(first.ingredient.name).isEqualTo("Pork")
        assertThat(first.amount).isEqualTo(500)
    }

    @Test
    fun `getRecipes - 재료가 없는 레시피는 빈 리스트로 파싱된다`() = runTest {
        val recipe = getAllRecipes().first { it.id == 4L }

        assertThat(recipe.name).isEqualTo("Ttekbokki")
        assertThat(recipe.ingredients).isEmpty()
    }

    @Test
    fun `getRecipes - JSON이 깨지면 PARSE_ERR를 반환한다`() = runTest {
        val brokenRepository = RecipeRepositoryImpl(FakeRecipeDataSource(recipesJson = "{ broken json"))

        assertThat(brokenRepository.getRecipes()).isEqualTo(Result.Error(NetworkError.PARSE_ERR))
    }

    @Test
    fun `getRecipes - 5xx 응답이면 SERVER_ERR를 반환한다`() = runTest {
        val serverErrorRepository = RecipeRepositoryImpl(FakeRecipeDataSource(statusCode = StatusCode.InternalServerError))

        assertThat(serverErrorRepository.getRecipes()).isEqualTo(Result.Error(NetworkError.SERVER_ERR))
    }

    @Test
    fun `getRecipes - 4xx 응답이면 CLIENT_ERR를 반환한다`() = runTest {
        val clientErrorRepository = RecipeRepositoryImpl(FakeRecipeDataSource(statusCode = StatusCode.NotFound))

        assertThat(clientErrorRepository.getRecipes()).isEqualTo(Result.Error(NetworkError.CLIENT_ERR))
    }
}
