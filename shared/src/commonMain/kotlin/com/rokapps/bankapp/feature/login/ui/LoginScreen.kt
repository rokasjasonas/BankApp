package com.rokapps.bankapp.feature.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rokapps.bankapp.feature.login.LoginTestIds
import com.rokapps.bankapp.ui.AppButton
import com.rokapps.bankapp.ui.AppText
import com.rokapps.bankapp.ui.AppTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoggedIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 420.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppText("BankApp", testId = LoginTestIds.Title, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            AppText("Sign in to continue", testId = LoginTestIds.Subtitle, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))

            AppTextField(
                value = state.username,
                onValueChange = viewModel::onUsernameChange,
                testId = LoginTestIds.UsernameField,
                label = "Username",
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
            AppTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                testId = LoginTestIds.PasswordField,
                label = "Password",
                enabled = !state.isLoading,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.error != null) {
                Spacer(Modifier.height(12.dp))
                AppText(state.error!!, testId = LoginTestIds.Error, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))
            AppButton(
                text = if (state.isLoading) "Signing in…" else "Sign in",
                onClick = { viewModel.login(onLoggedIn) },
                testId = LoginTestIds.SubmitButton,
                enabled = state.canSubmit,
                modifier = Modifier.fillMaxWidth(),
            )
            if (state.showDemoHint) {
                Spacer(Modifier.height(16.dp))
                AppText("Demo: demo / password", testId = LoginTestIds.DemoHint, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
