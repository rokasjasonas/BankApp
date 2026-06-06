package com.rokapps.bankapp.core.featureflags.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/** Observes [flag] reactively, emitting whenever its value changes. */
class ObserveFeatureFlagUseCase(private val repository: FeatureFlagRepository) {
    operator fun invoke(flag: FeatureFlag): Flow<Boolean> =
        repository.values
            .map { it[flag] ?: flag.defaultValue }
            .distinctUntilChanged()
}
