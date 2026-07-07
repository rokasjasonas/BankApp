package com.rokapps.bankapp.feature.login

import com.rokapps.bankapp.feature.login.data.FakeAuthRepository
import com.rokapps.bankapp.feature.login.domain.LoginUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class LoginUseCaseTest {

    private val useCase = LoginUseCase(FakeAuthRepository())

    @Test
    fun blankCredentialsFailWithoutHittingRepository() = runTest {
        listOf("" to "", "demo" to "").forEach { (username, password) ->
            val result = useCase(username, password)
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        }
    }

    @Test
    fun validDemoCredentialsSucceed() = runTest {
        val result = useCase(FakeAuthRepository.DEMO_USER, FakeAuthRepository.DEMO_PASSWORD)
        assertTrue(result.isSuccess)
    }

    @Test
    fun wrongPasswordFails() = runTest {
        val result = useCase(FakeAuthRepository.DEMO_USER, "wrong")
        assertTrue(result.isFailure)
    }
}
