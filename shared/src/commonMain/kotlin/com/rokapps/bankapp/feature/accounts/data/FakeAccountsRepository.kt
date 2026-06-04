package com.rokapps.bankapp.feature.accounts.data

import com.rokapps.bankapp.feature.accounts.domain.AccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import kotlinx.coroutines.delay

/** In-memory stand-in for a real accounts backend. */
class FakeAccountsRepository : AccountsRepository {
    override suspend fun getAccounts(): List<BankAccount> {
        delay(400) // simulate network latency
        return listOf(
            BankAccount("1", "Everyday Checking", "LT12 1000 0111 0100 1000", 1_234_56, "EUR"),
            BankAccount("2", "Savings", "LT12 1000 0111 0100 2000", 9_876_543, "EUR"),
            BankAccount("3", "Travel Card", "LT12 1000 0111 0100 3000", 450_00, "USD"),
        )
    }
}
