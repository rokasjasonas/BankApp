package com.rokapps.bankapp.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * The app's primary button. Wraps Material3 [Button] with a text label.
 *
 * @param testId stable id published as an accessibility description in debug builds.
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    testId: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.testId(testId),
        enabled = enabled,
    ) {
        Text(text)
    }
}
