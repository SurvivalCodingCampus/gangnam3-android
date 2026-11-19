package com.suwonsmartapp.recipeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.screens.CookingModeScreen
import com.suwonsmartapp.recipeapp.screens.HomeScreen
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.screens.SavedScreen
import com.suwonsmartapp.recipeapp.screens.SearchFilterSheet

/** 화면 전환 상태(데모용 컴포저블 로컬 상태 — 실제 앱은 내비게이션/상태 저장소 사용). */
private enum class Tab { Home, Saved, Notification, Profile }

@Composable
fun AppRoot() {
    var filter by remember { mutableStateOf(FilterState()) }
    var showFilter by remember { mutableStateOf(false) }
    // 조리 모드(전체화면 스텝 뷰어) — 인덱스 포함 상태는 호출자 소유.
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                when {
                    recipe != null -> RecipeDetailScreen(
                        recipe = recipe,
                        saved = recipe.id in bookmarks,
                        onBack = { selectedRecipeId = null },
                        onShare = {},
                        onStartCooking = { cookingRecipeId = recipe.id; cookingStep = 0 }
                    )
                    selectedTab == Tab.Home -> HomeScreen(
                        query = query,
                        onQueryChange = { query = it },
                        filter = filter,
                        onFilterChange = { filter = it },
                        onOpenFilter = { showFilter = true },
                        bookmarks = bookmarks,
                        onOpenRecipe = { selectedRecipeId = it },
                        onOpenProfile = { selectedTab = Tab.Profile }
                    )
                    selectedTab == Tab.Saved -> SavedScreen(
                        bookmarks = bookmarks,
                    )
                    else -> PlaceholderScreen(
                        title = if (selectedTab == Tab.Notification) {
                            stringResource(R.string.app_placeholder_notifications)
                        } else {
                            stringResource(R.string.app_placeholder_profile)
                        },
                        note = stringResource(R.string.app_placeholder_note)
                    )
                }
                if (showFilter) {
                    SearchFilterSheet(
                        state = filter,
                        onApply = { updated -> filter = updated; showFilter = false },
                        onDismiss = { showFilter = false }
                    )
                }
                // 조리 모드 전체화면 뷰어(명세 §4 플로우 최종 단계).
                if (cooking != null) {
                    CookingModeScreen(
                        steps = cooking.steps,
                        index = cookingStep,
                        onStepChange = { cookingStep = it },
                        onDone = { cookingRecipeId = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeBottomBar(selected: Tab, onSelect: (Tab) -> Unit) {
    NavigationBar {
            NavigationBarItem(
                    )
            )
    }
}

@Composable
private fun PlaceholderScreen(title: String, note: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                note,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
