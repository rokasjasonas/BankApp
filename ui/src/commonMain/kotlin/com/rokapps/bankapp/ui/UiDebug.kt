package com.rokapps.bankapp.ui

/**
 * Controls whether base components publish their `testId` as an accessibility
 * content description (see [Modifier.testId][testId]).
 *
 * Defaults to the platform's debug-build flag: on iOS this is detected
 * automatically; on Android the host app should set it from `BuildConfig.DEBUG`
 * (the library cannot read it without an application context).
 */
object UiDebug {
    var exposeTestIds: Boolean = platformDefaultExposeTestIds
}

/** Best-effort per-platform default for [UiDebug.exposeTestIds]. */
internal expect val platformDefaultExposeTestIds: Boolean
