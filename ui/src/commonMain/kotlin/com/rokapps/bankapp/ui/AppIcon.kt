package com.rokapps.bankapp.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

/**
 * The app's icon primitive. Wraps Material3 [Icon].
 *
 * @param testId stable id published as an accessibility description in debug builds.
 */
@Composable
fun AppIcon(
    painter: Painter,
    contentDescription: String?,
    testId: String,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.testId(testId),
        tint = tint,
    )
}
