package com.rokapps.bankapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlagRepository
import com.rokapps.bankapp.di.appModules
import com.rokapps.bankapp.feature.accounts.ui.AccountsScreen
import com.rokapps.bankapp.feature.login.ui.LoginScreen
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModules) }) {
        val featureFlagRepository: FeatureFlagRepository = koinInject()

        LaunchedEffect(Unit) {
            runCatching { featureFlagRepository.refresh() }
                .onFailure { println("Failed to refresh feature flags: $it") }
        }

        MaterialTheme {
            Surface {
                var loggedIn by remember { mutableStateOf(false) }
                if (!loggedIn) {
                    LoginScreen(
                        onLoggedIn = { loggedIn = true },
                    )
                } else {
                    AccountsScreen(
                        onLogout = { loggedIn = false },
                    )
                }
            }
        }
    }
}
