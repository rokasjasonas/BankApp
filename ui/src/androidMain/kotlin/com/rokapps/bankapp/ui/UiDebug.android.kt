package com.rokapps.bankapp.ui

// The library can't read the host app's BuildConfig.DEBUG without a context,
// so default to off and let the app opt in via UiDebug.exposeTestIds.
internal actual val platformDefaultExposeTestIds: Boolean = false
