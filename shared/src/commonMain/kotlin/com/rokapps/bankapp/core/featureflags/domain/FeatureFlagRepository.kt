package com.rokapps.bankapp.core.featureflags.domain

import kotlinx.coroutines.flow.StateFlow

/** Provides current feature-flag values and refreshes them from a remote source. */
interface FeatureFlagRepository {
    /** Current value for every flag; each flag's default until [refresh] succeeds. */
    val values: StateFlow<Map<FeatureFlag, Boolean>>

    /** Fetches the latest values from the remote source. */
    suspend fun refresh()
}

/** Current value of [flag], falling back to its [FeatureFlag.defaultValue]. */
fun FeatureFlagRepository.isEnabled(flag: FeatureFlag): Boolean =
    values.value[flag] ?: flag.defaultValue
