package com.rokapps.bankapp.di

import com.rokapps.bankapp.core.featureflags.data.DefaultFeatureFlagRepository
import com.rokapps.bankapp.core.featureflags.data.FakeRemoteFeatureFlagSource
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlagRepository

/**
 * Minimal manual dependency graph for app-wide singletons.
 *
 * The feature-flag repository is a singleton so its cached values are shared
 * across features. Replace [FakeRemoteFeatureFlagSource] with a real source later.
 */
object AppGraph {
    val featureFlagRepository: FeatureFlagRepository by lazy {
        DefaultFeatureFlagRepository(FakeRemoteFeatureFlagSource())
    }
}
