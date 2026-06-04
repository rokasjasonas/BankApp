package com.rokapps.bankapp.ui

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
internal actual val platformDefaultExposeTestIds: Boolean = Platform.isDebugBinary
