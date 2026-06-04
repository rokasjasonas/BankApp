package com.rokapps.bankapp.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

/**
 * The app's image primitive. Wraps Foundation [Image].
 *
 * @param testId stable id published as an accessibility description in debug builds.
 */
@Composable
fun AppImage(
    painter: Painter,
    contentDescription: String?,
    testId: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.testId(testId),
        contentScale = contentScale,
    )
}
