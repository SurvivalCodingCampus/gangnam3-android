package com.suwonsmartapp.recipeapp.presentation.main

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.suwonsmartapp.recipeapp.core.routing.Route
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.presentation.home.HomeScreen
import com.suwonsmartapp.recipeapp.presentation.home.HomeState
import com.suwonsmartapp.recipeapp.presentation.saved_recipes.SavedRecipesScreen
import com.suwonsmartapp.recipeapp.ui.AppColors

@Composable
fun MainScreen(
    currentRoute: NavKey,
    onTabClick: (Route) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = AppColors.white,
        bottomBar = {
            MainNavigationBar(
                currentRoute = currentRoute,
                onTabClick = onTabClick,
            )
        },
    ) { innerPadding ->
        content(
            Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        )
    }
}

private val previewRecipes = listOf(
    Recipe(
        id = 1,
        category = "Indian",
        name = "Classic Greek Salad",
        imageUrl = "",
        chef = "Chef John",
        time = "15 Mins",
        rating = 4.5,
    ),
    Recipe(
        id = 2,
        category = "Asian",
        name = "Crunchy Nut Coleslaw",
        imageUrl = "",
        chef = "Mark Kelvin",
        time = "10 Mins",
        rating = 3.5,
    ),
)

@Preview(showBackground = true)
@Composable
private fun MainScreenHomePreview() {
    MainScreen(
        currentRoute = Route.Home,
        onTabClick = {},
    ) { contentModifier ->
        HomeScreen(
            modifier = contentModifier,
            state = HomeState(
                recipes = previewRecipes,
                bookmarkedIds = setOf(1),
            ),
            onSelectCategory = {},
            onBookmarkClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenSavedRecipesPreview() {
    MainScreen(
        currentRoute = Route.SavedRecipes,
        onTabClick = {},
    ) { contentModifier ->
        SavedRecipesScreen(
            modifier = contentModifier,
            recipes = previewRecipes,
        )
    }
}
