package com.rokapps.bankapp.core.featureflags.domain

/**
 * Known feature flags.
 *
 * @param key identifier used by the remote source.
 * @param defaultValue value used until a remote value is fetched, or if the remote omits this flag.
 */
enum class FeatureFlag(val key: String, val defaultValue: Boolean) {
    ShowAccountBalance(key = "show_account_balance", defaultValue = true),
    ShowLoginDemoHint(key = "show_login_demo_hint", defaultValue = true),
}
