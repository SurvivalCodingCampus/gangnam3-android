package com.suwonsmartapp.recipeapp.presentation.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwonsmartapp.recipeapp.R
import com.suwonsmartapp.recipeapp.domain.model.recipe.Recipe
import com.suwonsmartapp.recipeapp.domain.model.recipe.RecipeCategory
import com.suwonsmartapp.recipeapp.domain.model.user.UserSummary
import com.suwonsmartapp.recipeapp.presentation.component.buttons.CircleBookmarkButton
import com.suwonsmartapp.recipeapp.presentation.component.labels.CookingTimeLabel
import com.suwonsmartapp.recipeapp.presentation.component.labels.RatingBadge
import com.suwonsmartapp.recipeapp.ui.AppColors
import com.suwonsmartapp.recipeapp.ui.AppTextStyles

@Composable
fun RecipeCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(recipe.imageUrl),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        
        // 이미지 위 선형 그라데이션 오버레이
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.black1.copy(alpha = 0f),
                            AppColors.black1
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // 별점
            RatingBadge(
                rating = recipe.rating,
                modifier = Modifier.align(Alignment.End)
            )
            
            Spacer(modifier = Modifier.size(60.dp))
            
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    // 제목
                    Text(
                        text = recipe.title,
                        color = AppColors.white,
                        style = AppTextStyles.smallTextBold.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,  // ... 으로 생략
                        modifier = Modifier
                            .height(42.dp)
                    )
                    
                    // Chef 정보
                    Text(
                        text = "By Chef ${recipe.chef.name}",
                        color = AppColors.gray4,
                        style = AppTextStyles.smallLabelRegular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(12.dp)
                    )
                }
                
                // 조리 시간
                CookingTimeLabel(recipe.cookingTimeMinutes)
                
                // 북마크
                CircleBookmarkButton(
                    isBookmarked = isBookmarked,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    val chef = UserSummary(
        id = "A001",
        name = "John",
        profileImageUrl = null,
        location = null
    )
    
    val recipe = Recipe(
        id = "001",
//        title = "Traditional spare ribs baked",
        title = "Traditional spare ribs baked Traditional spare ribs baked Traditional spare ribs baked Traditional spare ribs baked ",
        imageUrl = R.drawable.test_img_food01,
//        imageUrl = R.drawable.test_img_food02,
        videoUrl = null,
        chef = chef,
        category = RecipeCategory.UNKNOWN,
        reviewCount = 10,
        cookingTimeMinutes = 20,
        createAt = 261113L,
        rating = 4.0
    )
    
    var isBookmarked by remember { mutableStateOf(false) }
    var isBookmarked2 by remember { mutableStateOf(true) }
    
    Column {
        RecipeCard(
            recipe = recipe,
            isBookmarked = isBookmarked,
        ) {
            isBookmarked = !isBookmarked
        }
        Spacer(Modifier.size(5.dp))
        RecipeCard(
            recipe = recipe,
            isBookmarked = isBookmarked2,
        ) {
            isBookmarked2 = !isBookmarked2
        }
    }
}