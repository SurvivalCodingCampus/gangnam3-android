package com.suwonsmartapp.recipeapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.domain.model.Recipe
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun HomeScreen(
    state: HomeState,
    onSelectCategory: (RecipeCategory) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(vertical = 20.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = stringResource(id = R.string.home_hello, state.userName),
                    style = AppTextStyles.largeTextBold,
                    color = AppColors.black1,
                )
                Spacer(modifier = Modifier.height(height = 5.dp))
                Text(
                    text = stringResource(id = R.string.home_greeting),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray3,
                )
            }
            Image(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = AppColors.secondary40),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = stringResource(id = R.string.home_profile),
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(height = 30.dp))
        Row(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 40.dp)
                    .border(
                        width = 1.dp,
                        color = AppColors.gray4,
                        shape = RoundedCornerShape(size = 10.dp),
                    )
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = AppColors.gray4,
                )
                Spacer(modifier = Modifier.width(width = 10.dp))
                Text(
                    text = stringResource(id = R.string.search_recipes_placeholder),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray4,
                )
            }
            Spacer(modifier = Modifier.width(width = 20.dp))
            Icon(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = AppColors.primary100)
                    .padding(all = 10.dp),
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = stringResource(id = R.string.search_recipes_filter),
                tint = AppColors.white,
            )
        }
        Spacer(modifier = Modifier.height(height = 15.dp))
        RecipeCategorySelector(
            modifier = Modifier.fillMaxWidth(),
            selectedCategory = state.selectedCategory,
            onSelectCategory = onSelectCategory,
            contentPadding = PaddingValues(horizontal = 30.dp),
        )
        Spacer(modifier = Modifier.height(height = 15.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 15.dp),
        ) {
            items(
                items = state.filteredRecipes,
                key = { it.id },
            ) { recipe ->
                HomeRecipeCard(
                    recipe = recipe,
                    isBookmarked = recipe.id in state.bookmarkedIds,
                    onBookmarkClick = { onBookmarkClick(recipe.id) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val recipe = Recipe(
        id = 1,
        category = "Indian",
        name = "Classic Greek Salad",
        imageUrl = "",
        chef = "Chef John",
        time = "15 Mins",
        rating = 4.5,
    )
    HomeScreen(
        state = HomeState(
            recipes = listOf(recipe, recipe.copy(id = 2, name = "Crunchy Nut Coleslaw", rating = 3.5)),
            bookmarkedIds = setOf(1),
        ),
        onSelectCategory = {},
        onBookmarkClick = {},
    )
}
