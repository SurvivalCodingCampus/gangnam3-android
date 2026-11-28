package com.example.gangnam3.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gangnam3.data.model.Recipe

/**
 * 홈 화면의 연결 담당(Root).
 *
 * ViewModel의 상태를 구독해 [HomeScreen]에 넘기고, 화면의 이벤트를 ViewModel로 전달한다.
 * 기본 ViewModel은 [HomeViewModel.Factory]가 저장소를 넣어 만든다.
 * @param onSearchClick 검색 화면으로 이동을 요청하는 콜백. 실제 이동은 호출자가 처리한다.
 * @param onRecipeClick 카드를 눌렀을 때의 콜백
 * @param onToggleBookmark 북마크를 실제로 저장하는 콜백. null이면 화면 상태만 바꾼다.
 */
@Composable
fun HomeRoot(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    onRecipeClick: (Recipe) -> Unit = {},
    onToggleBookmark: ((Recipe) -> Unit)? = null
) {
    // StateFlow를 Compose 상태로 변환한다. 화면이 보이지 않는 동안에는 수집을 멈춘다.
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        // viewModel::함수 는 함수 참조로, { viewModel.함수(it) } 와 같은 역할을 한다.
        onSelectCategory = viewModel::onSelectCategory,
        onToggleBookmark = { id ->
            val recipe = state.allRecipes.firstOrNull { it.id == id }
            // 저장을 맡은 쪽이 있으면 요청만 전달한다. 결과는 저장소 Flow로 돌아온다.
            if (onToggleBookmark == null) viewModel.onToggleBookmark(id)
            else if (recipe != null) onToggleBookmark(recipe)
        },
        onSearchClick = onSearchClick,
        modifier = modifier,
        onRecipeClick = onRecipeClick
    )
}
