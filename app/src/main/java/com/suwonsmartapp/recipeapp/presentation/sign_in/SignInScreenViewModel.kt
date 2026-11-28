package com.suwonsmartapp.recipeapp.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suwonsmartapp.recipeapp.RecipeApplication
import com.suwonsmartapp.recipeapp.core.data.repository.AuthRepository
import com.suwonsmartapp.recipeapp.core.network.NetworkError
import com.suwonsmartapp.recipeapp.core.network.Result
import com.suwonsmartapp.recipeapp.core.network.toMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInScreenViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        // 다시 입력하면 에러 문구 지움
        _state.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, errorMessage = null) }
    }

    // 성공하면 onSuccess 호출 (화면 이동은 바깥에서)
    fun signIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = authRepository.signIn(
                email = _state.value.email,
                password = _state.value.password,
            )

            when (result) {
                is Result.Success -> {
//                  result.value.accessToken
                    onSuccess()
                }

                is Result.Error -> {
                    // 401 → 계정 틀림, 나머지는 각 에러에 맞는 공용 문구
                    val message = if (result.error == NetworkError.UNAUTHORIZED_ERR) {
                        "Email or password is incorrect"
                    } else {
                        result.error.toMessage()
                    }
                    _state.update { it.copy(errorMessage = message) }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RecipeApplication
                SignInScreenViewModel(
                    authRepository = application.authRepository,
                )
            }
        }
    }
}
