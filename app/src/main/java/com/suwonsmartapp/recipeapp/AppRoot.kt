package com.suwonsmartapp.recipeapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.core.content.edit
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwonsmartapp.recipeapp.data.FilterState
import com.suwonsmartapp.recipeapp.data.Recipe
import com.suwonsmartapp.recipeapp.data.RatingFilter
import com.suwonsmartapp.recipeapp.data.RecipeCategory
import com.suwonsmartapp.recipeapp.data.RoomRecipeRepository
import com.suwonsmartapp.recipeapp.data.TimeFilter
import com.suwonsmartapp.recipeapp.data.toShareText
import com.suwonsmartapp.recipeapp.screens.CookingModeScreen
import com.suwonsmartapp.recipeapp.screens.HomeScreen
import com.suwonsmartapp.recipeapp.screens.OnboardingScreen
import com.suwonsmartapp.recipeapp.screens.RecipeDetailScreen
import com.suwonsmartapp.recipeapp.screens.SavedScreen
import com.suwonsmartapp.recipeapp.screens.SearchFilterSheet
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/** 화면 전환 상태(데모용 컴포저블 로컬 상태 — 실제 앱은 내비게이션/상태 저장소 사용). */
private enum class Tab { Home, Saved, Notification, Profile }

/** 회전/프로세스 재생성에도 필터를 유지한다. [시간, 평점, 카테고리...] 이름 목록으로 저장. */
internal val FilterStateSaver: Saver<FilterState, Any> = listSaver(
    save = { state -> listOf(state.time.name, state.rating.name) + state.categories.map { it.name } },
    restore = { names ->
        FilterState(
            time = TimeFilter.valueOf(names[0]),
            rating = RatingFilter.valueOf(names[1]),
            categories = names.drop(2).map { RecipeCategory.valueOf(it) }.toSet()
        )
    }
)

/**
 * 앱 전체의 상태를 소유하고 어떤 화면을 보여줄지 결정하는 최상위 컴포저블.
 * 
 * - 데이터는 Room 저장소의 Flow를 구독한다. DB가 바뀌면 화면이 자동으로 갱신된다.
 * - 하위 화면은 상태를 직접 바꾸지 않고 콜백으로 요청한다(상태 호이스팅).
 */
@Composable
fun AppRoot() {
    val context = LocalContext.current
    // recomposition마다 새로 만들지 않도록 remember로 보관한다.
    val repository = remember(context) { RoomRecipeRepository(context.applicationContext) }
    // 버튼 클릭처럼 컴포지션 밖에서 실행되는 이벤트용 코루틴 스코프.
    val scope = rememberCoroutineScope()
    // DB 조회가 실패했는지 여부. true면 로딩 표시 대신 오류 안내를 보여준다.
    var loadFailed by remember { mutableStateOf(false) }
    // 첫 DB 조회 전에는 null — 시딩 전 빈 목록이 잠깐 보이는 현상 방지.
    val recipes by produceState<List<Recipe>?>(initialValue = null, repository) {
        // 빈 DB라면 샘플을 넣는다. 실패해도 기존 데이터 조회는 계속 시도한다.
        try {
            repository.initialize()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("AppRoot", "레시피 DB 초기화 실패", e)
        }
        repository.recipeFlow
            .catch { e ->
                Log.e("AppRoot", "레시피 조회 실패", e)
                loadFailed = true
            }
            .collect { value = it }
    }
    // 북마크한 레시피 id 목록. 조회가 실패하면 마지막 값을 유지한다.
    val bookmarks by produceState<List<String>>(initialValue = emptyList(), repository) {
        repository.bookmarkFlow
            .catch { e -> Log.e("AppRoot", "북마크 조회 실패", e) }
            .collect { value = it }
    }
    // 저장 실패가 앱 종료로 이어지지 않게 한다.
    fun launchWrite(action: suspend () -> Unit) {
        scope.launch {
            try {
                action()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("AppRoot", "북마크 변경 실패", e)
                Toast.makeText(context, R.string.bookmark_error, Toast.LENGTH_SHORT).show()
            }
        }
    }
    val toggleBookmark: (String) -> Unit = { id -> launchWrite { repository.toggleBookmark(id) } }

    // 명세 §3.1: 최초 실행 시 온보딩 → Start Cooking → 홈. 완료 여부는 로컬에 보존.
    val preferences = remember(context) {
        context.getSharedPreferences("recipeapp_prefs", Context.MODE_PRIVATE)
    }
    var onboardingDone by rememberSaveable {
        mutableStateOf(preferences.getBoolean("onboarding_done", false))
    }
    if (!onboardingDone) {
        // edge-to-edge에서 시스템 바와 겹치지 않도록 인셋을 적용한다.
        Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
            Box(Modifier.fillMaxSize().padding(innerPadding)) {
                OnboardingScreen(
                    onStart = {
                        preferences.edit { putBoolean("onboarding_done", true) }
                        onboardingDone = true
                    }
                )
            }
        }
        return
    }

    // rememberSaveable: 화면 회전이나 프로세스 재생성 후에도 값이 유지된다.
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Home) }
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable(stateSaver = FilterStateSaver) { mutableStateOf(FilterState()) }
    var showFilter by rememberSaveable { mutableStateOf(false) }
    var selectedRecipeId by rememberSaveable { mutableStateOf<String?>(null) }
    // 조리 모드(전체화면 스텝 뷰어) — 인덱스 포함 상태는 호출자 소유.
    var cookingRecipeId by rememberSaveable { mutableStateOf<String?>(null) }
    var cookingStep by rememberSaveable { mutableIntStateOf(0) }

    // 뒤로가기 우선순위: 조리 모드 닫기 → 상세 화면 닫기. 필터 시트는 시트가 직접 처리한다.
    BackHandler(enabled = !showFilter && (selectedRecipeId != null || cookingRecipeId != null)) {
        if (cookingRecipeId != null) cookingRecipeId = null else selectedRecipeId = null
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        // 상세·조리 화면에서는 하단 탭을 숨긴다.
        bottomBar = {
            if (selectedRecipeId == null && cookingRecipeId == null) {
                RecipeBottomBar(selectedTab) { selectedTab = it }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            val loaded = recipes
            if (loadFailed) {
                PlaceholderScreen(
                    title = stringResource(R.string.app_load_error),
                    note = stringResource(R.string.app_load_error_hint)
                )
            } else if (loaded == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // id만 저장하고 실제 레시피는 최신 목록에서 찾는다. DB 변경이 바로 반영된다.
                val recipe = selectedRecipeId?.let { id -> loaded.firstOrNull { it.id == id } }
                val cooking = cookingRecipeId?.let { id -> loaded.firstOrNull { it.id == id } }
                // 조리 모드가 덮는 동안 뒤 화면은 TalkBack 탐색에서 제외한다(상태 보존을 위해 컴포지션은 유지).
                Box(
                    if (cooking != null) Modifier.fillMaxSize().clearAndSetSemantics { }
                    else Modifier.fillMaxSize()
                ) {
                    when {
                        recipe != null -> RecipeDetailScreen(
                            recipe = recipe,
                            saved = recipe.id in bookmarks,
                            onToggleBookmark = toggleBookmark,
                            onBack = { selectedRecipeId = null },
                            onShare = { shareRecipe(context, recipe) },
                            onStartCooking = { cookingRecipeId = recipe.id; cookingStep = 0 }
                        )
                        selectedTab == Tab.Home -> HomeScreen(
                            recipes = loaded,
                            query = query,
                            onQueryChange = { query = it },
                            filter = filter,
                            onFilterChange = { filter = it },
                            onOpenFilter = { showFilter = true },
                            bookmarks = bookmarks,
                            onToggleBookmark = toggleBookmark,
                            onOpenRecipe = { selectedRecipeId = it },
                            onOpenProfile = { selectedTab = Tab.Profile }
                        )
                        selectedTab == Tab.Saved -> SavedScreen(
                            recipes = loaded,
                            bookmarks = bookmarks,
                            onToggleBookmark = toggleBookmark,
                            onClearAll = { launchWrite { repository.clearBookmarks() } },
                            onOpenRecipe = { selectedRecipeId = it }
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
                }
                // 필터 시트는 Apply를 눌렀을 때만 선택값을 반영한다.
                if (showFilter) {
                    SearchFilterSheet(
                        state = filter,
                        onApply = { updated -> filter = updated; showFilter = false },
                        onDismiss = { showFilter = false }
                    )
                }
                // 조리 모드 전체화면 뷰어(명세 §4 플로우 최종 단계).
                if (cooking != null) {
                    // Surface가 터치를 막아 뒤쪽 상세 화면이 눌리지 않는다.
                    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
}

/** 시스템 공유 시트로 레시피 본문(제목·재료·조리 단계)을 보낸다. */
private fun shareRecipe(context: Context, recipe: Recipe) {
    val send = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, recipe.title)
        putExtra(Intent.EXTRA_TEXT, recipe.toShareText())
    }
    context.startActivity(Intent.createChooser(send, recipe.title))
}

/**
 * 하단 탭 바.
 * @param selected 현재 선택된 탭
 * @param onSelect 탭을 눌렀을 때 호출되는 콜백
 */
@Composable
private fun RecipeBottomBar(selected: Tab, onSelect: (Tab) -> Unit) {
    NavigationBar {
        // (탭, 아이콘, 라벨 문자열 리소스) 묶음.
        val items = listOf(
            Triple(Tab.Home, "🏠", R.string.tab_home),
            Triple(Tab.Saved, "🔖", R.string.tab_saved),
            Triple(Tab.Notification, "🔔", R.string.tab_notification),
            Triple(Tab.Profile, "👤", R.string.tab_profile)
        )
        items.forEach { (tab, icon, labelRes) ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelect(tab) },
                // 이모지는 장식 — 탭 라벨만 낭독되고, 글자 확대 시 라벨은 1줄로 제한한다.
                icon = { Text(icon, modifier = Modifier.clearAndSetSemantics { }) },
                label = {
                    Text(
                        stringResource(labelRes),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

/** 미구현 탭과 오류 안내에 함께 쓰는 제목+설명 화면. */
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
