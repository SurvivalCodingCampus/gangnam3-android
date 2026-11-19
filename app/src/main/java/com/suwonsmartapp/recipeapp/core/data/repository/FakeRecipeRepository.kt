package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.Recipe
import kotlinx.serialization.json.Json

class FakeRecipeRepository : RecipeRepository {
    private val recipesJson = "[\n" +
            "    {\n" +
            "      \"category\": \"Indian\",\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Traditional spare ribs baked\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg\",\n" +
            "      \"chef\": \"Chef John\",\n" +
            "      \"time\": \"20 min\",\n" +
            "      \"rating\": 4.0,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"Pork\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2019/12/20/14/44/meat-4708596_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 500\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 9,\n" +
            "            \"name\": \"Onion\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2013/02/21/19/14/onion-bulbs-84722_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 50\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"Pepper\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/03/05/22/31/pepper-1239308_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 10\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Tomato\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 100\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"category\": \"Asian\",\n" +
            "      \"name\": \"Spice roasted chicken with flavored rice\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2018/12/04/16/49/tandoori-3856045_1280.jpg\",\n" +
            "      \"chef\": \"Mark Kelvin\",\n" +
            "      \"time\": \"20 min\",\n" +
            "      \"rating\": 4.0,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"Chicken\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2010/12/10/08/chicken-1140_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 300\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"Rice\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/02/29/05/46/brown-rice-1228099_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 200\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"Pepper\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/03/05/22/31/pepper-1239308_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 5\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"Pork\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2019/12/20/14/44/meat-4708596_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 500\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"category\": \"Chinese\",\n" +
            "      \"name\": \"Spicy fried rice mix chicken bali\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2019/09/07/19/02/spanish-paella-4459519_1280.jpg\",\n" +
            "      \"chef\": \"Spicy Nelly\",\n" +
            "      \"time\": \"20 min\",\n" +
            "      \"rating\": 4.0,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"Chicken\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2010/12/10/08/chicken-1140_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 200\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"Rice\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/02/29/05/46/brown-rice-1228099_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 150\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Tomato\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 100\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"category\": \"Japanese\",\n" +
            "      \"id\": 4,\n" +
            "      \"name\": \"Ttekbokki\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2017/07/27/16/48/toppokki-2545943_1280.jpg\",\n" +
            "      \"chef\": \"Kim Dahee\",\n" +
            "      \"time\": \"30 min\",\n" +
            "      \"rating\": 5.0,\n" +
            "      \"ingredients\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"category\": \"American\",\n" +
            "      \"name\": \"Grilled salmon with avocado salsa\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_1280.jpg\",\n" +
            "      \"chef\": \"Alice Johnson\",\n" +
            "      \"time\": \"25 min\",\n" +
            "      \"rating\": 4.5,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 5,\n" +
            "            \"name\": \"Avocado\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2020/01/02/01/43/avocado-4734786_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 150\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"Pepper\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/03/05/22/31/pepper-1239308_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 5\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6,\n" +
            "      \"category\": \"British\",\n" +
            "      \"name\": \"Beef Wellington\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2019/10/22/10/11/beef-wellington-4568239_1280.jpg\",\n" +
            "      \"chef\": \"Gordon Ramsay\",\n" +
            "      \"time\": \"45 min\",\n" +
            "      \"rating\": 5.0,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Beef\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/01/21/18/08/meet-1154341_1280.png\"\n" +
            "          },\n" +
            "          \"amount\": 500\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 9,\n" +
            "            \"name\": \"Onion\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2013/02/21/19/14/onion-bulbs-84722_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 100\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 7,\n" +
            "      \"category\": \"Italian\",\n" +
            "      \"name\": \"Classic Margherita Pizza\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2019/05/15/18/56/pizza-4205701_1280.jpg\",\n" +
            "      \"chef\": \"Mario Batali\",\n" +
            "      \"time\": \"15 min\",\n" +
            "      \"rating\": 4.3,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Tomato\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 200\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 8,\n" +
            "      \"category\": \"Japanese\",\n" +
            "      \"name\": \"Sushi Platter\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2017/10/15/11/41/sushi-2853382_1280.jpg\",\n" +
            "      \"chef\": \"Jiro Ono\",\n" +
            "      \"time\": \"60 min\",\n" +
            "      \"rating\": 4.8,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"Chicken\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2010/12/10/08/chicken-1140_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 200\n" +
            "        },\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"Rice\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2016/02/29/05/46/brown-rice-1228099_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 100\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9,\n" +
            "      \"category\": \"French\",\n" +
            "      \"name\": \"French Onion Soup\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2016/03/03/16/19/food-1234483_1280.jpg\",\n" +
            "      \"chef\": \"Julia Child\",\n" +
            "      \"time\": \"40 min\",\n" +
            "      \"rating\": 4.6,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 9,\n" +
            "            \"name\": \"Onion\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2013/02/21/19/14/onion-bulbs-84722_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 300\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10,\n" +
            "      \"category\": \"French\",\n" +
            "      \"name\": \"Chocolate Lava Cake\",\n" +
            "      \"image\": \"https://cdn.pixabay.com/photo/2016/11/22/18/52/cake-1850011_1280.jpg\",\n" +
            "      \"chef\": \"Paul Hollywood\",\n" +
            "      \"time\": \"30 min\",\n" +
            "      \"rating\": 4.9,\n" +
            "      \"ingredients\": [\n" +
            "        {\n" +
            "          \"ingredient\": {\n" +
            "            \"id\": 7,\n" +
            "            \"name\": \"Sugar\",\n" +
            "            \"image\": \"https://cdn.pixabay.com/photo/2014/11/28/19/10/lump-sugar-549096_1280.jpg\"\n" +
            "          },\n" +
            "          \"amount\": 100\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "]\n"

    private val recipes: List<Recipe> = Json.decodeFromString(recipesJson)

    override suspend fun getRecipe(id: Long): Recipe =
        recipes.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Recipe not found: id=$id")

    override suspend fun getRecipes(): List<Recipe> = recipes
}
