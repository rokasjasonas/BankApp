package com.rokapps.bankapp.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlag
import com.rokapps.bankapp.core.featureflags.domain.ObserveFeatureFlagUseCase
import com.rokapps.bankapp.feature.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDemoHint: Boolean = true,
) {
    val canSubmit: Boolean get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    observeFeatureFlag: ObserveFeatureFlagUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeFeatureFlag(FeatureFlag.ShowLoginDemoHint).collect { enabled ->
                _state.update { it.copy(showDemoHint = enabled) }
            }
        }
    }

    fun onUsernameChange(value: String) = _state.update { it.copy(username = value, error = null) }

    fun onPasswordChange(value: String) = _state.update { it.copy(password = value, error = null) }

    fun login(onSuccess: () -> Unit) {
        val current = _state.value
        if (!current.canSubmit) return
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            loginUseCase(current.username, current.password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Login failed") }
                }
        }
    }
}
