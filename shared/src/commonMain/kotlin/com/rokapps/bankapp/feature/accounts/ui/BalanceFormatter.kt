package com.rokapps.bankapp.feature.accounts.ui

import com.rokapps.bankapp.feature.accounts.domain.BankAccount

fun BankAccount.formattedBalance(): String {
    val major = balanceMinor / 100
    val minor = (balanceMinor % 100).toString().padStart(2, '0')
    return "$major.$minor $currency"
}
