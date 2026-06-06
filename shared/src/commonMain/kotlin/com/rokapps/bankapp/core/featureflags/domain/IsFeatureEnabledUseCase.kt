package com.rokapps.bankapp.core.featureflags.domain

/** One-shot check of whether [flag] is currently enabled. */
class IsFeatureEnabledUseCase(private val repository: FeatureFlagRepository) {
    operator fun invoke(flag: FeatureFlag): Boolean = repository.isEnabled(flag)
}
