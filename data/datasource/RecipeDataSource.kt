package com.example.gangnam3.data.datasource

import com.example.gangnam3.data.model.Recipe

/** 레시피 원본 데이터를 제공하는 계약. 구현을 바꿔도 저장소 코드는 그대로다. */
interface RecipeDataSource {
    /** 레시피를 가져온다. 시간이 걸릴 수 있어 코루틴에서 호출하는 suspend 함수다. */
    suspend fun fetchSavedRecipes(): List<Recipe>
}

/** 서버 없이 고정된 샘플을 돌려주는 실습용 구현. */
class MockRecipeDataSource : RecipeDataSource {
    override suspend fun fetchSavedRecipes(): List<Recipe> {
        // 카테고리 필터를 확인할 수 있도록 네 가지 카테고리를 하나씩 넣었다.
        return listOf(
            Recipe(
                id = 1,
                title = "Classic Greek Salad",
                chefName = "Chef John",
                cookingTime = "15 Mins",
                rating = 4.5,
                imageUrl = "https://cdn.pixabay.com/photo/2017/02/15/10/57/salad-2068220_1280.jpg",
                category = "Italian",
                isBookmarked = false
            ),
            Recipe(
                id = 2,
                title = "Crunchy Nut Coleslaw",
                chefName = "Mark Kelvin",
                cookingTime = "10 Mins",
                rating = 3.5,
                imageUrl = "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246_1280.jpg",
                category = "Asian",
                isBookmarked = false
            ),
            Recipe(
                id = 3,
                title = "Spicy Chicken Tikka",
                chefName = "Spicy Nelly",
                cookingTime = "25 Mins",
                rating = 4.8,
                imageUrl = "https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg",
                category = "Indian",
                isBookmarked = true
            ),
            Recipe(
                id = 4,
                title = "Sweet & Sour Pork",
                chefName = "Laura Wilson",
                cookingTime = "20 Mins",
                rating = 4.2,
                imageUrl = "https://cdn.pixabay.com/photo/2016/06/01/21/40/steak-1429796_1280.jpg",
                category = "Chinese",
                isBookmarked = false
            )
        )
    }
}