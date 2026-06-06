package com.rokapps.bankapp.di

import com.rokapps.bankapp.core.featureflags.data.DefaultFeatureFlagRepository
import com.rokapps.bankapp.core.featureflags.data.FakeRemoteFeatureFlagSource
import com.rokapps.bankapp.core.featureflags.domain.FeatureFlagRepository
import com.rokapps.bankapp.core.featureflags.domain.IsFeatureEnabledUseCase
import com.rokapps.bankapp.core.featureflags.domain.ObserveFeatureFlagUseCase
import com.rokapps.bankapp.feature.accounts.data.FakeAccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.AccountsRepository
import com.rokapps.bankapp.feature.accounts.domain.GetAccountsUseCase
import com.rokapps.bankapp.feature.accounts.ui.AccountsViewModel
import com.rokapps.bankapp.feature.login.data.FakeAuthRepository
import com.rokapps.bankapp.feature.login.domain.AuthRepository
import com.rokapps.bankapp.feature.login.domain.LoginUseCase
import com.rokapps.bankapp.feature.login.ui.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {
    single<FeatureFlagRepository> { DefaultFeatureFlagRepository(FakeRemoteFeatureFlagSource()) }
    single<AccountsRepository> { FakeAccountsRepository() }
    single<AuthRepository> { FakeAuthRepository() }
}

val useCaseModule = module {
    factoryOf(::ObserveFeatureFlagUseCase)
    factoryOf(::IsFeatureEnabledUseCase)
    factoryOf(::GetAccountsUseCase)
    factoryOf(::LoginUseCase)
}

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::AccountsViewModel)
}

val appModules = listOf(dataModule, useCaseModule, viewModelModule)
