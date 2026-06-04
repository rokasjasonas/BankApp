package com.rokapps.bankapp.feature.accounts

import com.rokapps.bankapp.feature.accounts.data.FakeAccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
import com.rokapps.bankapp.feature.accounts.domain.formattedBalance
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAccountsUseCaseTest {

    private val useCase = GetAccountsUseCase(FakeAccountsRepository())

    @Test
    fun returnsAccounts() = runTest {
        assertTrue(useCase().isNotEmpty())
    }

    @Test
    fun ordersAccountsByBalanceDescending() = runTest {
        val balances = useCase().map { it.balanceMinor }
        assertEquals(balances.sortedDescending(), balances)
    }

    @Test
    fun formatsBalanceInMajorAndMinorUnits() {
        val account = BankAccount("1", "Test", "123", 1_234_56, "EUR")
        assertEquals("1234.56 EUR", account.formattedBalance())
    }
}
