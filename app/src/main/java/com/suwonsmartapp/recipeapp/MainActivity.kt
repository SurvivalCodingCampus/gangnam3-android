package com.suwonsmartapp.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suwonsmartapp.recipeapp.ui.theme.RecipeAppTheme

/** 앱의 유일한 액티비티. 화면 전환은 모두 Compose(AppRoot) 안에서 처리한다. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 콘텐츠를 상태바·내비게이션바 아래까지 그린다. 겹침은 각 Scaffold의 innerPadding으로 막는다.
        enableEdgeToEdge()
        // XML 대신 Compose로 화면을 구성하고, 앱 공통 테마로 감싼다.
        setContent {
            RecipeAppTheme {
                AppRoot()
            }
        }
    }
}
