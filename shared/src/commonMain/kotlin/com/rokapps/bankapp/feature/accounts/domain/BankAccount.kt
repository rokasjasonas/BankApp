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

/** Formats the balance as "major.minor CURRENCY", e.g. "1234.56 EUR". */
fun BankAccount.formattedBalance(): String {
    val major = balanceMinor / 100
    val minor = (balanceMinor % 100).toString().padStart(2, '0')
    return "$major.$minor $currency"
}
