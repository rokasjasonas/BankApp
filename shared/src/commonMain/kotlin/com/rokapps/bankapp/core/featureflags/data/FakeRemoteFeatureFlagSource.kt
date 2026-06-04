package com.rokapps.bankapp.core.featureflags.data

import kotlinx.coroutines.delay

/**
 * Stand-in for a real remote config backend (e.g. Firebase Remote Config, LaunchDarkly).
 * Returns a static override map; any flag it omits falls back to its default.
 *
 * Swap this for a real [RemoteFeatureFlagSource] implementation later — nothing else changes.
 */
class FakeRemoteFeatureFlagSource : RemoteFeatureFlagSource {
    override suspend fun fetch(): Map<String, Boolean> {
        delay(300) // simulate network latency
        return mapOf(
            "show_account_balance" to true,
            // "show_login_demo_hint" intentionally omitted -> falls back to its default
        )
    }
}
