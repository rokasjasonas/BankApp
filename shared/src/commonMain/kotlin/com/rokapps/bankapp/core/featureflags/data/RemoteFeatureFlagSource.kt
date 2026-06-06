package com.rokapps.bankapp.core.featureflags.data

/** Remote provider of feature-flag overrides, keyed by [FeatureFlag.key][com.rokapps.bankapp.core.featureflags.domain.FeatureFlag.key]. */
interface RemoteFeatureFlagSource {
    /** Fetches the current overrides. Flags it omits fall back to their defaults. */
    suspend fun fetch(): Map<String, Boolean>
}
