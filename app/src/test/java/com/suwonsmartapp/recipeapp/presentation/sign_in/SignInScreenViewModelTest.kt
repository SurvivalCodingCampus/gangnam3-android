@file:Suppress("NonAsciiCharacters")

package com.suwonsmartapp.recipeapp.presentation.sign_in

import com.suwonsmartapp.recipeapp.core.data.datasource.AuthDataSource
import com.suwonsmartapp.recipeapp.core.data.datasource.FakeAuthDataSource
import com.suwonsmartapp.recipeapp.core.data.dto.SignInRequestDto
import com.suwonsmartapp.recipeapp.core.data.dto.SignInResponseDto
import com.suwonsmartapp.recipeapp.core.data.repository.AuthRepositoryImpl
import com.suwonsmartapp.recipeapp.core.network.Response
import com.suwonsmartapp.recipeapp.core.network.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignInScreenViewModelTest {

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // 기본은 qwer / 1234만 통과하는 가짜 로그인
    private fun viewModel(
        dataSource: AuthDataSource = FakeAuthDataSource(),
    ) = SignInScreenViewModel(AuthRepositoryImpl(dataSource))

    @Test
    fun `생성 직후에는 입력값이 비어있고 에러 문구가 없다`() {
        val viewModel = viewModel()

        assertThat(viewModel.state.value).isEqualTo(SignInState())
    }

    @Test
    fun `이메일과 비밀번호를 입력하면 상태에 반영된다`() {
        val viewModel = viewModel()

        viewModel.onEmailChange("qwer")
        viewModel.onPasswordChange("1234")

        assertThat(viewModel.state.value.email).isEqualTo("qwer")
        assertThat(viewModel.state.value.password).isEqualTo("1234")
    }

    @Test
    fun `계정이 맞으면 onSuccess가 호출되고 에러 문구가 없다`() = runTest {
        val viewModel = viewModel()
        var isSuccess = false

        viewModel.onEmailChange("qwer")
        viewModel.onPasswordChange("1234")
        viewModel.signIn(onSuccess = { isSuccess = true })
        advanceUntilIdle()

        assertThat(isSuccess).isTrue()
        assertThat(viewModel.state.value.errorMessage).isNull()
    }

    @Test
    fun `비밀번호가 틀리면 onSuccess가 호출되지 않고 에러 문구가 나온다`() = runTest {
        val viewModel = viewModel()
        var isSuccess = false

        viewModel.onEmailChange("qwer")
        viewModel.onPasswordChange("0000")
        viewModel.signIn(onSuccess = { isSuccess = true })
        advanceUntilIdle()

        assertThat(isSuccess).isFalse()
        assertThat(viewModel.state.value.errorMessage).isEqualTo("Email or password is incorrect")
    }

    @Test
    fun `에러가 난 뒤 다시 입력하면 에러 문구가 지워진다`() = runTest {
        val viewModel = viewModel()

        viewModel.onEmailChange("qwer")
        viewModel.onPasswordChange("0000")
        viewModel.signIn(onSuccess = {})
        advanceUntilIdle()
        assertThat(viewModel.state.value.errorMessage).isNotNull()

        viewModel.onPasswordChange("1234")

        assertThat(viewModel.state.value.errorMessage).isNull()
    }

    // 항상 statusCode만 돌려주는 데이터소스
    private fun statusDataSource(statusCode: StatusCode) = object : AuthDataSource {
        override suspend fun signIn(request: SignInRequestDto): Response<SignInResponseDto> =
            Response(statusCode = statusCode)
    }

    // 해당 응답 코드로 로그인했을 때 나오는 에러 문구
    private fun TestScope.errorMessageOf(statusCode: StatusCode): String? {
        val viewModel = viewModel(statusDataSource(statusCode))
        viewModel.signIn(onSuccess = {})
        advanceUntilIdle()
        return viewModel.state.value.errorMessage
    }

    @Test
    fun `401이면 계정이 틀렸다는 문구가 나온다`() = runTest {
        assertThat(errorMessageOf(StatusCode.Unauthorized)).isEqualTo("Email or password is incorrect")
    }

    @Test
    fun `401이 아닌 4xx면 클라 에러 문구가 나온다`() = runTest {
        assertThat(errorMessageOf(StatusCode.BadRequest)).isEqualTo("클라 에러")
        assertThat(errorMessageOf(StatusCode.NotFound)).isEqualTo("클라 에러")
    }

    @Test
    fun `서버 에러면 서버 문제 문구가 나온다`() = runTest {
        assertThat(errorMessageOf(StatusCode.InternalServerError)).isEqualTo("서버 문제")
    }

    @Test
    fun `성공인데 body가 없으면 널 데이터 에러 문구가 나온다`() = runTest {
        assertThat(errorMessageOf(StatusCode.Ok)).isEqualTo("널 데이터 에러")
    }
}
