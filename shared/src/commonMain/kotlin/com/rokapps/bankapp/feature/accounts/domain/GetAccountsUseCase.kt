package com.rokapps.bankapp.feature.accounts.domain

/** Loads the user's accounts, ordered by balance (highest first). */
class GetAccountsUseCase(private val repository: AccountsRepository) {
    suspend operator fun invoke(): List<BankAccount> =
        repository.getAccounts().sortedByDescending { it.balanceMinor }
}
