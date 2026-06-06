package com.rokapps.bankapp.feature.accounts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlag
import com.rokapps.bankapp.core.featureflags.domain.ObserveFeatureFlagUseCase
import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AccountsUiState {
    data object Loading : AccountsUiState
    data class Error(val message: String) : AccountsUiState
    data class Success(
        val accounts: List<BankAccount>,
        val showBalance: Boolean,
    ) : AccountsUiState
}

class AccountsViewModel(
    private val getAccounts: GetAccountsUseCase,
    observeFeatureFlag: ObserveFeatureFlagUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AccountsUiState>(AccountsUiState.Loading)
    val state: StateFlow<AccountsUiState> = _state.asStateFlow()

    init {
        load()
        viewModelScope.launch {
            observeFeatureFlag(FeatureFlag.ShowAccountBalance).collect { enabled ->
                val current = _state.value
                if (current is AccountsUiState.Success) {
                    _state.value = current.copy(showBalance = enabled)
                }
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            _state.value = AccountsUiState.Loading
            runCatching { getAccounts() }
                .onSuccess { accounts ->
                    val showBalance = (_state.value as? AccountsUiState.Success)?.showBalance ?: true
                    _state.value = AccountsUiState.Success(accounts = accounts, showBalance = showBalance)
                }
                .onFailure { e ->
                    _state.value = AccountsUiState.Error(e.message ?: "Failed to load accounts")
                }
        }
    }
}
