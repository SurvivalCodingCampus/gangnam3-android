package com.suwonsmartapp.recipeapp.core.data.datasource

import com.suwonsmartapp.recipeapp.core.data.dto.UserDto
import com.suwonsmartapp.recipeapp.core.network.Response

interface UserDataSource {
    // 서버 쿼리 예: /user → 로그인한 유저 정보
    suspend fun getUser(): Response<UserDto>
}
