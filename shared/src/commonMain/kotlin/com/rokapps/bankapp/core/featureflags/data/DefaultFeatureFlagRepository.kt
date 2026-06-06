package com.rokapps.bankapp.core.featureflags.data

import com.rokapps.bankapp.core.featureflags.domain.FeatureFlag
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Holds feature-flag values in memory, seeded with defaults and updated from a
 * [RemoteFeatureFlagSource] on [refresh]. Unknown/omitted keys keep their default.
 */
class DefaultFeatureFlagRepository(
    private val source: RemoteFeatureFlagSource,
) : FeatureFlagRepository {

    private val _values = MutableStateFlow(defaults())
    override val values: StateFlow<Map<FeatureFlag, Boolean>> = _values.asStateFlow()

    override suspend fun refresh() {
        val overrides = source.fetch()
        _values.value = FeatureFlag.entries.associateWith { flag ->
            overrides[flag.key] ?: flag.defaultValue
        }
    }

    private fun defaults(): Map<FeatureFlag, Boolean> =
        FeatureFlag.entries.associateWith { it.defaultValue }
}
