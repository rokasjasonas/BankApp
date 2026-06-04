package com.rokapps.bankapp.feature.accounts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rokapps.bankapp.feature.accounts.AccountsTestIds
import com.rokapps.bankapp.feature.accounts.domain.BankAccount
import com.rokapps.bankapp.feature.accounts.domain.formattedBalance
import com.rokapps.bankapp.ui.AppButton
import com.rokapps.bankapp.ui.AppText

@Composable
fun AccountsScreen(
    viewModel: AccountsViewModel,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppText("Your accounts", testId = AccountsTestIds.Title, style = MaterialTheme.typography.headlineSmall)
            AppButton(text = "Log out", onClick = onLogout, testId = AccountsTestIds.LogoutButton)
        }
        Spacer(Modifier.height(20.dp))

        when {
            state.isLoading -> AppText("Loading…", testId = AccountsTestIds.Loading)
            state.error != null -> AppText(state.error!!, testId = AccountsTestIds.Error, color = MaterialTheme.colorScheme.error)
            else -> Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                state.accounts.forEach { account -> AccountRow(account) }
            }
        }
    }
}

@Composable
private fun AccountRow(account: BankAccount) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
    ) {
        AppText(
            account.name,
            testId = AccountsTestIds.accountName(account.id),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(4.dp))
        AppText(
            account.accountNumber,
            testId = AccountsTestIds.accountNumber(account.id),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(8.dp))
        AppText(
            account.formattedBalance(),
            testId = AccountsTestIds.accountBalance(account.id),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
