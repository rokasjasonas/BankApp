package com.rokapps.bankapp.feature.accounts.domain

/**
 * A single bank account. [balanceMinor] is stored in the currency's minor unit
 * (e.g. cents) to avoid floating-point rounding errors on money.
 */
data class BankAccount(
    val id: String,
    val name: String,
    val accountNumber: String,
    val balanceMinor: Long,
    val currency: String,
)
