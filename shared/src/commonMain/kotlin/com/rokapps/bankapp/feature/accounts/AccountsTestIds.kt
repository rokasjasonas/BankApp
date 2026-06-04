package com.rokapps.bankapp.feature.accounts

/** All test ids used by the accounts feature. Screens must reference these, not string literals. */
object AccountsTestIds {
    const val Title = "accounts_title"
    const val LogoutButton = "accounts_logout_button"
    const val Loading = "accounts_loading"
    const val Error = "accounts_error"

    // Per-row ids, keyed by account id.
    fun accountName(accountId: String): String = "accounts_name_$accountId"
    fun accountNumber(accountId: String): String = "accounts_number_$accountId"
    fun accountBalance(accountId: String): String = "accounts_balance_$accountId"
}
