package com.suwonsmartapp.recipeapp.data

/**
 * 데모용 샘플 데이터 — 명세 §3 와이어프레임 예시 기반.
 * 실제 앱은 데이터 레이어(네트워크/DB) 로 교체할 것.
 */
val sampleRecipes: List<Recipe> = listOf(
    Recipe(
        id = "r1",
        title = "Classic Spaghetti Carbonara",
        description = "느끼함 없이 크리미한 정통 파스타",
        chef = "Chef Mario",
        category = RecipeCategory.Italian,
        cookingMinutes = 20,
        rating = 4.8,
        reviews = 1200,
        emoji = "🍝",
        ingredients = listOf(
            Ingredient("Spaghettis Pasta", "200g"),
            Ingredient("Fresh Eggs", "2 pcs"),
            Ingredient("Parmesan Cheese", "50g"),
            Ingredient("Pancetta / Bacon", "100g")
        ),
        steps = listOf(
            "스파게티를 소금물에 알덴테로 삶는다.",
            "볼에 계란·치즈·소금을 섞어 크림을 만든다.",
            "불 끈 팬에서 면과 반죽을 빠르게 버무린다.",
            "후추를 뿌려 마무리한다."
        )
    ),
    Recipe(
        id = "r2",
        title = "Thai Green Curry",
        description = "레몬그라스와 고추의 향긋한 커리",
        chef = "Chef Somsri",
        category = RecipeCategory.Asian,
        cookingMinutes = 30,
        rating = 4.6,
        reviews = 840,
        emoji = "🍛",
        ingredients = listOf(
            Ingredient("Coconut Milk", "400ml"),
            Ingredient("Green Curry Paste", "2 tbsp"),
            Ingredient("Chicken Breast", "300g"),
            Ingredient("Thai Basil", "1 cup")
        ),
        steps = listOf(
            "커리 페이스트를 코코넛 밀크와 함께 볶아 향을 낸다.",
            "닭고기를 넣고 겉면이 익을 때까지 조린다.",
            "바질을 넣고 1분 더 익혀 마무리한다."
        )
    ),
    Recipe(
        id = "r3",
        title = "Tacos al Pastor",
        description = "구운 돼지고기와 파인애플의 멕시칸 타코",
        chef = "Chef Diego",
        category = RecipeCategory.Mexican,
        cookingMinutes = 10,
        rating = 4.3,
        reviews = 310,
        emoji = "🌮",
        ingredients = listOf(
            Ingredient("Corn Tortillas", "6 pcs"),
            Ingredient("Pork Shoulder", "300g"),
            Ingredient("Pineapple", "1/2"),
            Ingredient("Onion", "1")
        ),
        steps = listOf(
            "돼지고기를 또르띠야와 함께 따뜻하게 굽는다.",
            "토르티야에 고기·파인애플·양파를 올려 완성한다."
        )
    ),
    Recipe(
        id = "r4",
        title = "Margherita Pizza",
        description = "토마토·모차렐라·바질의 심플 피자",
        chef = "Chef Nadia",
        category = RecipeCategory.Italian,
        cookingMinutes = 25,
        rating = 4.7,
        reviews = 950,
        emoji = "🍕",
        ingredients = listOf(
            Ingredient("Pizza Dough", "500g"),
            Ingredient("Tomato Sauce", "300g"),
            Ingredient("Mozzarella", "250g"),
            Ingredient("Fresh Basil", "10g")
        ),
        steps = listOf(
            "반죽을 둥글게 펴서 팬에 올린다.",
            "소스·모차렐라·바질을 올리고 250도에서 굽는다."
        )
    ),
    Recipe(
        id = "r5",
        title = "Miso Ramen",
        description = "미소 페이스트 육수의 진한 라멘",
        chef = "Chef Kenji",
        category = RecipeCategory.Asian,
        cookingMinutes = 35,
        rating = 4.5,
        reviews = 520,
        emoji = "🍜",
        isNew = true,
        ingredients = listOf(
            Ingredient("Ramen Noodles", "4 pcs"),
            Ingredient("Miso Paste", "2 tbsp"),
            Ingredient("Pork Belly", "200g"),
            Ingredient("Soft-boiled Eggs", "2")
        ),
        steps = listOf(
            "미소 페이스트를 육수에 풀어 끓인다.",
            "삶은 면을 넣고 그릇에 담는다.",
            "차슈·계란을 올려 완성한다."
        )
    ),
    Recipe(
        id = "r6",
        title = "Chili con Carne",
        description = "우스터소스로 끓인 멕시칸 스튜",
        chef = "Chef Lucia",
        category = RecipeCategory.Mexican,
        cookingMinutes = 40,
        rating = 4.2,
        reviews = 260,
        emoji = "🌶️",
        isNew = true,
        ingredients = listOf(
            Ingredient("Kidney Beans", "1 can"),
            Ingredient("Ground Beef", "400g"),
            Ingredient("Tomato", "2"),
            Ingredient("Chili Powder", "2 tbsp")
        ),
        steps = listOf(
            "소고기를 볶다가 양파·고추를 더해 익힌다.",
            "토마토와 콩을 넣고 약불에서 30분 끓인다."
        )
    )
)

val sampleFeatured: List<Recipe> = sampleRecipes.filter { it.id in setOf("r1", "r2") }
val sampleNew: List<Recipe> = sampleRecipes.filter { it.isNew }
