package com.rokapps.bankapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import com.rokapps.bankapp.feature.accounts.data.FakeAccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
import com.rokapps.bankapp.feature.accounts.ui.AccountsScreen
import com.rokapps.bankapp.feature.accounts.ui.AccountsViewModel
import com.rokapps.bankapp.feature.login.data.FakeAuthRepository
import com.rokapps.bankapp.feature.login.domain.LoginUseCase
import com.rokapps.bankapp.feature.login.ui.LoginScreen
import com.rokapps.bankapp.feature.login.ui.LoginViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            var loggedIn by remember { mutableStateOf(false) }
            if (!loggedIn) {
                val loginViewModel = viewModel { LoginViewModel(LoginUseCase(FakeAuthRepository())) }
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoggedIn = { loggedIn = true },
                )
            } else {
                val accountsViewModel = viewModel { AccountsViewModel(GetAccountsUseCase(FakeAccountsRepository())) }
                AccountsScreen(
                    viewModel = accountsViewModel,
                    onLogout = { loggedIn = false },
                )
            }
        }
    }
}
