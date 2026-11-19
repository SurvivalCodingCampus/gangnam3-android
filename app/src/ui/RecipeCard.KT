import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeCard(
    recipe: Recipe,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().height(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 배경 이미지
            Image(
                painter = painterResource(id = recipe.imageUrl),
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 상단 평점 배지
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFE082)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Rating", tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${recipe.rating}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }

            // 하단 정보 및 북마크 버튼
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = recipe.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "By ${recipe.chefName}",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⏱ ${recipe.cookingTime}",
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    IconButton(onClick = onBookmarkClick) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) Color(0xFFFFB800) else Color.White
                        )
                    }
                }
            }
        }
    }
}

// 상태 호이스팅 적용 Preview (Interactive Mode로 검증 가능)
@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    var isBookmarked by remember { mutableStateOf(false) }
    val sampleRecipe = Recipe(
        title = "Traditional spare ribs baked",
        chefName = "Chef John",
        cookingTime = "20 min",
        rating = 4.0f,
        imageUrl = android.R.drawable.ic_menu_gallery // 샘플 이미지 대체
    )

    Box(modifier = Modifier.padding(16.dp)) {
        RecipeCard(
            recipe = sampleRecipe,
            isBookmarked = isBookmarked,
            onBookmarkClick = { isBookmarked = !isBookmarked }
        )
    }
}