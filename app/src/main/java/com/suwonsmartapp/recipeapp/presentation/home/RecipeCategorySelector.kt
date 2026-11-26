package com.suwonsmartapp.recipeapp.presentation.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RecipeCategorySelector(
    selectedCategory: RecipeCategory,
    onSelectCategory: (RecipeCategory) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(items = RecipeCategory.entries) { category ->
            val isSelected = category == selectedCategory
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = if (isSelected) AppColors.primary100 else AppColors.transparent)
                    .clickable { onSelectCategory(category) }
                    .padding(horizontal = 20.dp, vertical = 7.dp),
                text = stringResource(id = category.toLabelRes()),
                style = AppTextStyles.smallerTextBold,
                color = if (isSelected) AppColors.white else AppColors.primary80,
            )
        }
    }
}

@StringRes
fun RecipeCategory.toLabelRes(): Int = when (this) {
    RecipeCategory.ALL -> R.string.home_category_all
    RecipeCategory.INDIAN -> R.string.home_category_indian
    RecipeCategory.ITALIAN -> R.string.home_category_italian
    RecipeCategory.ASIAN -> R.string.home_category_asian
    RecipeCategory.CHINESE -> R.string.home_category_chinese
}

@Preview(showBackground = true)
@Composable
private fun RecipeCategorySelectorPreview() {
    var selectedCategory by remember { mutableStateOf(RecipeCategory.ALL) }
    RecipeCategorySelector(
        selectedCategory = selectedCategory,
        onSelectCategory = { selectedCategory = it },
    )
}
