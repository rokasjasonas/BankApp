package com.rokapps.bankapp.feature.login.domain

/** Authenticates a user against a credentials backend. */
interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Unit>
}
