package com.example.gangnam3.data.datasource

import com.example.gangnam3.data.model.Recipe

/** 레시피를 가져오는 원본(서버, DB 등)의 공통 규격. */
interface RecipeDataSource {
    /** 저장된 레시피를 가져온다. 오래 걸릴 수 있어 suspend로 선언한다. */
    suspend fun fetchSavedRecipes(): List<Recipe>
}

/** 서버 없이 화면을 확인하기 위한 고정 데이터. */
class MockRecipeDataSource : RecipeDataSource {
    override suspend fun fetchSavedRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                title = "Traditional spare ribs baked",
                chefName = "Chef John",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 2,
                title = "spice roasted chicken with flavored rice",
                chefName = "Mark Kelvin",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2017/05/07/08/56/pancakes-2291908_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 3,
                title = "Spicy fried rice mix chicken bali",
                chefName = "Spicy Nelly",
                cookingTime = "20 min",
                rating = 4.0,
                imageUrl = "https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg",
                isBookmarked = true
            ),
            Recipe(
                id = 4,
                title = "Lamb chops with fruity couscous and mint",
                chefName = "Spicy Nelly",
                cookingTime = "20 min",
                rating = 3.0,
                imageUrl = "https://cdn.pixabay.com/photo/2016/06/01/21/40/steak-1429796_1280.jpg",
                isBookmarked = true
            ),
            // 홈 화면 카테고리 필터용 샘플
            Recipe(
                id = 5,
                title = "Classic Greek Salad",
                chefName = "Chef John",
                cookingTime = "15 Mins",
                rating = 4.5,
                imageUrl = "https://cdn.pixabay.com/photo/2017/02/15/10/57/salad-2068220_1280.jpg",
                category = "Italian",
                isBookmarked = false
            ),
            Recipe(
                id = 6,
                title = "Crunchy Nut Coleslaw",
                chefName = "Mark Kelvin",
                cookingTime = "10 Mins",
                rating = 3.5,
                imageUrl = "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246_1280.jpg",
                category = "Asian",
                isBookmarked = false
            ),
            Recipe(
                id = 7,
                title = "Spicy Chicken Tikka",
                chefName = "Spicy Nelly",
                cookingTime = "25 Mins",
                rating = 4.8,
                imageUrl = "https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg",
                category = "Indian",
                isBookmarked = true
            ),
            Recipe(
                id = 8,
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
