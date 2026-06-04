package com.rokapps.bankapp.feature.login.domain

/** Validates input and delegates authentication to [AuthRepository]. */
class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Username and password are required"))
        }
        return repository.login(username.trim(), password)
    }
}
