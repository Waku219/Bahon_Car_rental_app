package com.example.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CholoColorScheme = lightColorScheme(
    primary = CholoGreen,
    onPrimary = CholoWhite,
    primaryContainer = CholoGreenLight,
    onPrimaryContainer = CholoGreenDark,
    secondary = CholoRed,
    onSecondary = CholoWhite,
    background = CholoWhite,
    onBackground = Ink,
    surface = CholoWhite,
    onSurface = Ink,
    surfaceVariant = SurfaceAlt,
    onSurfaceVariant = InkMuted,
    outline = Line,
    outlineVariant = Line
)

/**
 * Light-only for now. To add dark mode later, define a darkColorScheme()
 * and pick between the two based on isSystemInDarkTheme().
 */
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CholoColorScheme,
        typography = Typography,
        content = content
    )
}
