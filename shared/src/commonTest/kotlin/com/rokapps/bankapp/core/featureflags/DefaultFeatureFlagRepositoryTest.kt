package com.rokapps.bankapp.core.featureflags

import com.rokapps.bankapp.core.featureflags.data.DefaultFeatureFlagRepository
import com.rokapps.bankapp.core.featureflags.data.RemoteFeatureFlagSource
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlag
import com.rokapps.bankapp.core.featureflags.domain.isEnabled
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private class FakeSource(private val overrides: Map<String, Boolean>) : RemoteFeatureFlagSource {
    override suspend fun fetch(): Map<String, Boolean> = overrides
}

class DefaultFeatureFlagRepositoryTest {

    @Test
    fun usesDefaultsBeforeRefresh() {
        val repo = DefaultFeatureFlagRepository(FakeSource(emptyMap()))
        FeatureFlag.entries.forEach { flag ->
            assertEquals(flag.defaultValue, repo.isEnabled(flag), "default for ${flag.key}")
        }
    }

    @Test
    fun refreshAppliesRemoteOverrides() = runTest {
        val repo = DefaultFeatureFlagRepository(
            FakeSource(mapOf(FeatureFlag.ShowAccountBalance.key to false)),
        )
        repo.refresh()
        assertFalse(repo.isEnabled(FeatureFlag.ShowAccountBalance))
    }

    @Test
    fun omittedFlagsFallBackToDefaultAfterRefresh() = runTest {
        // Source overrides only one flag; the other must keep its default.
        val repo = DefaultFeatureFlagRepository(
            FakeSource(mapOf(FeatureFlag.ShowAccountBalance.key to false)),
        )
        repo.refresh()
        assertEquals(
            FeatureFlag.ShowLoginDemoHint.defaultValue,
            repo.isEnabled(FeatureFlag.ShowLoginDemoHint),
        )
    }

    @Test
    fun unknownRemoteKeysAreIgnored() = runTest {
        val repo = DefaultFeatureFlagRepository(FakeSource(mapOf("not_a_real_flag" to true)))
        repo.refresh()
        FeatureFlag.entries.forEach { flag ->
            assertEquals(flag.defaultValue, repo.isEnabled(flag))
        }
    }

    @Test
    fun valuesFlowReflectsLatestRefresh() = runTest {
        val repo = DefaultFeatureFlagRepository(
            FakeSource(mapOf(FeatureFlag.ShowAccountBalance.key to false)),
        )
        repo.refresh()
        assertTrue(repo.values.value.containsKey(FeatureFlag.ShowAccountBalance))
        assertEquals(false, repo.values.value[FeatureFlag.ShowAccountBalance])
    }
}
