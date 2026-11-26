package com.suwonsmartapp.recipeapp.core.data.repository

import com.suwonsmartapp.recipeapp.core.data.model.User
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result

interface UserRepository {
    // 로그인한 유저 정보 (이름, 프로필 사진)
    suspend fun getUser(): Result<User, NetworkError>
}
