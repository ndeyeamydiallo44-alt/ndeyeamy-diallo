package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ExecutiveDarkColorScheme = darkColorScheme(
    primary = EmeraldLight,
    onPrimary = ObsidianDark,
    primaryContainer = SurfaceElevated,
    onPrimaryContainer = EmeraldLight,
    secondary = PrestigeGold,
    onSecondary = ObsidianDark,
    secondaryContainer = SurfaceCard,
    onSecondaryContainer = PrestigeGoldLight,
    tertiary = CyberCyan,
    onTertiary = ObsidianDark,
    background = ObsidianDark,
    onBackground = PlatinumWhite,
    surface = MidnightNavy,
    onSurface = PlatinumWhite,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = SlateMuted,
    outline = BorderSubtle,
    error = CoralRed,
    onError = PlatinumWhite
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ExecutiveDarkColorScheme,
        typography = Typography,
        content = content
    )
}
