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
        assertTrue(useCase("", "").isFailure)
        assertTrue(useCase("demo", "").isFailure)
    }

    @Test
    fun validDemoCredentialsSucceed() = runTest {
        val result = useCase(FakeAuthRepository.DEMO_USER, FakeAuthRepository.DEMO_PASSWORD)
        assertTrue(result.isSuccess)
    }

    @Test
    fun wrongPasswordFails() = runTest {
        assertTrue(useCase(FakeAuthRepository.DEMO_USER, "wrong").isFailure)
    }
}
