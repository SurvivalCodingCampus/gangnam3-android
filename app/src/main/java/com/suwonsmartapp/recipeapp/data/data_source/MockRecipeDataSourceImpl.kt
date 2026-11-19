package com.suwonsmartapp.recipeapp.data.data_source

import com.suwonsmartapp.recipeapp.data.model.Recipe

class MockRecipeDataSourceImpl : RecipeDataSource {

    override suspend fun getRecipes(): List<Recipe> = listOf(
        Recipe(
            1,
            "Indian",
            "Traditional spare ribs baked",
            "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            "Chef John",
            "20 min",
            4.0
        ),
        Recipe(
            2,
            "Asian",
            "Spice roasted chicken with flavored rice",
            "https://cdn.pixabay.com/photo/2018/12/04/16/49/tandoori-3856045_1280.jpg",
            "Mark Kelvin",
            "20 min",
            4.0
        ),
        Recipe(
            3,
            "Chinese",
            "Spicy fried rice mix chicken bali",
            "https://cdn.pixabay.com/photo/2019/09/07/19/02/spanish-paella-4459519_1280.jpg",
            "Spicy Nelly",
            "20 min",
            4.0
        ),
        Recipe(
            4,
            "Japanese",
            "Ttekbokki",
            "https://cdn.pixabay.com/photo/2017/07/27/16/48/toppokki-2545943_1280.jpg",
            "Kim Dahee",
            "30 min",
            5.0
        ),
        Recipe(
            5,
            "American",
            "Grilled salmon with avocado salsa",
            "https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg",
            "Alice Johnson",
            "25 min",
            4.5
        ),
        Recipe(
            6,
            "British",
            "Beef Wellington",
            "https://cdn.pixabay.com/photo/2019/10/22/10/11/beef-wellington-4568239_1280.jpg",
            "Gordon Ramsay",
            "45 min",
            5.0
        ),
        Recipe(
            7,
            "Italian",
            "Classic Margherita Pizza",
            "https://cdn.pixabay.com/photo/2019/05/15/18/56/pizza-4205701_1280.jpg",
            "Mario Batali",
            "15 min",
            4.3
        ),
        Recipe(
            8,
            "Japanese",
            "Sushi Platter",
            "https://cdn.pixabay.com/photo/2017/10/15/11/41/sushi-2853382_1280.jpg",
            "Jiro Ono",
            "60 min",
            4.8
        ),
        Recipe(
            9,
            "French",
            "French Onion Soup",
            "https://cdn.pixabay.com/photo/2016/03/03/16/19/food-1234483_1280.jpg",
            "Julia Child",
            "40 min",
            4.6
        ),
        Recipe(
            10,
            "French",
            "Chocolate Lava Cake",
            "https://cdn.pixabay.com/photo/2016/11/22/18/52/cake-1850011_1280.jpg",
            "Paul Hollywood",
            "30 min",
            4.9
        ),
    )
}
