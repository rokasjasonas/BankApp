package com.rokapps.bankapp.feature.accounts

import com.rokapps.bankapp.feature.accounts.data.FakeAccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
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
}
