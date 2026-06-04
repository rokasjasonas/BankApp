package com.rokapps.bankapp.feature.accounts.domain

/** Provides the signed-in user's bank accounts. */
interface AccountsRepository {
    suspend fun getAccounts(): List<BankAccount>
}
