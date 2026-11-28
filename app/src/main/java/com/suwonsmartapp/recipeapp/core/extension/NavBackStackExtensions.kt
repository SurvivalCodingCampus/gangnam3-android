package com.suwonsmartapp.recipeapp.core.extension

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.suwonsmartapp.recipeapp.core.routing.Route

// 스택에 쌓지 않고 교체 (뒤로가기 시 이전 화면으로 돌아가지 않음)
fun NavBackStack<NavKey>.replace(route: Route) {
    if(isNotEmpty()) {
        this[lastIndex] = route
    }
}
