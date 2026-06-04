package com.rokapps.bankapp.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * Publishes [id] as an accessibility content description so UI tests and
 * accessibility tooling can locate this node.
 *
 * Only applied when [UiDebug.exposeTestIds] is enabled (debug builds); in
 * release builds it is a no-op and adds no semantics to the component.
 */
fun Modifier.testId(id: String): Modifier =
    if (UiDebug.exposeTestIds) {
        semantics { contentDescription = id }
    } else {
        this
    }
