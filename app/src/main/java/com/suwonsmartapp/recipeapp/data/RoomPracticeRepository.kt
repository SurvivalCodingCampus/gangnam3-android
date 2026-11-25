package com.suwonsmartapp.recipeapp.data

import com.example.gangnam3.data.repository.RecipeRepository as PracticeRepository
import com.example.gangnam3.data.model.Recipe as PracticeRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

/**
 * Room 저장소를 홈·검색 화면의 저장소 규격에 맞추는 어댑터.
 *
 * 레시피와 북마크를 하나로 합쳐 내보내므로, 어느 화면에서 북마크를 바꿔도 함께 갱신된다.
 */
class RoomPracticeRepository(private val room: RoomRecipeRepository) : PracticeRepository {
    override fun observeRecipes(): Flow<List<PracticeRecipe>> =
        combine(room.recipeFlow, room.bookmarkFlow) { recipes, bookmarks ->
            // Room의 id는 문자열이라 화면용 숫자 id는 순서로 만들고, 원래 id는 sourceId에 담는다.
            recipes.mapIndexed { index, recipe -> recipe.toPractice(index + 1, recipe.id in bookmarks) }
        }

    override suspend fun getSavedRecipes(): List<PracticeRecipe> = observeRecipes().first()
}

private fun Recipe.toPractice(number: Int, bookmarked: Boolean) = PracticeRecipe(
    id = number,
    title = title,
    chefName = chef,
    cookingTime = "$cookingMinutes min",
    rating = rating,
    imageUrl = thumbnailUrl.orEmpty(),
    category = category.label,
    isBookmarked = bookmarked,
    sourceId = id,
    emoji = emoji
)
