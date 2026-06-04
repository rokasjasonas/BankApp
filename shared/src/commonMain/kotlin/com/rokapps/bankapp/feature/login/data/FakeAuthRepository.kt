package com.rokapps.bankapp.feature.login.data

import com.rokapps.bankapp.feature.login.domain.AuthRepository
import kotlinx.coroutines.delay

/** In-memory stand-in for a real auth backend. Accepts a single demo account. */
class FakeAuthRepository : AuthRepository {
    override suspend fun login(username: String, password: String): Result<Unit> {
        delay(600) // simulate network latency
        return if (username == DEMO_USER && password == DEMO_PASSWORD) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException("Invalid username or password"))
        }
    }

    companion object {
        const val DEMO_USER = "demo"
        const val DEMO_PASSWORD = "password"
    }
}
