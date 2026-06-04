package com.rokapps.bankapp.feature.accounts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountsUiState(
    val isLoading: Boolean = true,
    val accounts: List<BankAccount> = emptyList(),
    val error: String? = null,
)

class AccountsViewModel(
    private val getAccounts: GetAccountsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AccountsUiState())
    val state: StateFlow<AccountsUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { getAccounts() }
                .onSuccess { accounts -> _state.update { it.copy(isLoading = false, accounts = accounts) } }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message ?: "Failed to load accounts") } }
        }
    }
}
