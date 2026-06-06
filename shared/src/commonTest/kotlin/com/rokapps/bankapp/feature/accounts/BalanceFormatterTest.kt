package com.rokapps.bankapp.feature.accounts

import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import com.rokapps.bankapp.feature.accounts.ui.formattedBalance
import kotlin.test.Test
import kotlin.test.assertEquals

class BalanceFormatterTest {

    @Test
    fun formatsBalanceInMajorAndMinorUnits() {
        val account = BankAccount("1", "Test", "123", 1_234_56, "EUR")
        assertEquals("1234.56 EUR", account.formattedBalance())
    }

    @Test
    fun formatsZeroBalance() {
        val account = BankAccount("2", "Zero", "456", 0, "USD")
        assertEquals("0.00 USD", account.formattedBalance())
    }

    @Test
    fun formatsSmallBalance() {
        val account = BankAccount("3", "Small", "789", 99, "EUR")
        assertEquals("0.99 EUR", account.formattedBalance())
    }

    @Test
    fun formatsLargeBalance() {
        val account = BankAccount("4", "Large", "000", 12_345_678_90L, "GBP")
        assertEquals("12345678.90 GBP", account.formattedBalance())
    }
}
