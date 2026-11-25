package com.suwonsmartapp.recipeapp.presentation.search_recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.data.model.Recipe
import com.suwonsmartapp.recipeapp.data.model.RecipeCategory
import com.suwonsmartapp.recipeapp.data.model.UserSummary
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles


@Composable
fun SearchRecipesScreen(
    state: SearchRecipesState,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val isSearching = state.query.isNotBlank() || state.filterSearchState != FilterSearchState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 30.dp, vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(size = 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onBackClick,
                    ),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.search_recipes_back),
                tint = AppColors.black2,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.search_recipes_title),
                style = AppTextStyles.mediumTextBold,
                color = AppColors.black2,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(height = 17.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 40.dp),
                value = state.query,
                onValueChange = onQueryChange,
                textStyle = AppTextStyles.smallerTextRegular.copy(color = AppColors.black1),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
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
                        Box(modifier = Modifier.weight(weight = 1f)) {
                            if (state.query.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.search_recipes_placeholder),
                                    style = AppTextStyles.smallerTextRegular,
                                    color = AppColors.gray4,
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.width(width = 20.dp))
            Icon(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = AppColors.primary100)
                    .clickable { onFilterClick() }
                    .padding(all = 10.dp),
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = stringResource(id = R.string.search_recipes_filter),
                tint = AppColors.white,
            )
        }
        Spacer(modifier = Modifier.height(height = 20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(weight = 1f),
                text = stringResource(
                    id = if (isSearching) {
                        R.string.search_recipes_result
                    } else {
                        R.string.search_recipes_recent
                    },
                ),
                style = AppTextStyles.normalTextBold,
                color = AppColors.black1,
            )
            if (isSearching) {
                Text(
                    text = pluralStringResource(
                        id = R.plurals.search_recipes_result_count,
                        count = state.filteredRecipes.size,
                        state.filteredRecipes.size,
                    ),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray3,
                )
            }
        }
        Spacer(modifier = Modifier.height(height = 20.dp))
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.primary100)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(space = 15.dp),
                verticalArrangement = Arrangement.spacedBy(space = 15.dp),
            ) {
                items(
                    items = state.filteredRecipes,
                    key = { it.id },
                ) { recipe ->
                    SearchRecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Composable
private fun SearchRecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(ratio = 1f)
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .paint(
                painter = rememberAsyncImagePainter(
                    model = recipe.imageUrl,
                    placeholder = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                    error = painterResource(id = R.drawable.traditional_spare_ribs_baked),
                ),
                contentScale = ContentScale.Crop,
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.black1.copy(alpha = 0f),
                        AppColors.black1,
                    ),
                ),
            )
            .padding(all = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(size = 20.dp),
                )
                .padding(horizontal = 7.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 3.dp)
                    .size(size = 8.dp),
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                tint = AppColors.rating,
            )
            Text(
                text = recipe.rating.toString(),
                style = AppTextStyles.smallerTextRegular,
                color = AppColors.black1,
            )
        }
        Column(modifier = Modifier.align(alignment = Alignment.BottomStart)) {
            Text(
                text = recipe.title,
                style = AppTextStyles.smallerTextBold,
                color = AppColors.white,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(R.string.recipe_chef, recipe.chef.name),
                style = AppTextStyles.smallerTextRegular,
                color = AppColors.gray3,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchRecipesScreenPreview() {
    val recipes = listOf(
        Recipe(
            id = "1",
            title = "Traditional spare ribs baked",
            imageUrl = "",
            videoUrl = "",
            chef = UserSummary(id = "", name = "Chef John", profileImageUrl = "", location = ""),
            category = RecipeCategory.INDIAN,
            reviewCount = 0,
            cookingTimeMinutes = 20,
            createAt = 0L,
            rating = 4.0,
        ),
        Recipe(
            id = "2",
            title = "Spice roasted chicken with flavored rice",
            imageUrl = "",
            videoUrl = "",
            chef = UserSummary(id = "", name = "Mark Kelvin", profileImageUrl = "", location = ""),
            category = RecipeCategory.ASIAN,
            reviewCount = 0,
            cookingTimeMinutes = 20,
            createAt = 0L,
            rating = 4.0,
        ),
    )
    SearchRecipesScreen(
        state = SearchRecipesState(
            recipes = recipes,
            filteredRecipes = recipes,
        ),
        onQueryChange = {},
        onBackClick = {},
        onFilterClick = {},
    )
}
