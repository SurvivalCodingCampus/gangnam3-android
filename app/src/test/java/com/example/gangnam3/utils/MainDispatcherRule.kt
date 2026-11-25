package com.example.gangnam3.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/** JVM 테스트에는 안드로이드 Main 스레드가 없으므로 테스트용 디스패처로 바꿔 주는 규칙. */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    // 각 테스트 시작 전에 교체하고
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    // 끝나면 원래대로 되돌려 다른 테스트에 영향을 주지 않는다.
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}