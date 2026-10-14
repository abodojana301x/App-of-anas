package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SocialHubDarkColorScheme = darkColorScheme(
    primary = AppleSystemBlue,
    onPrimary = Color.White,
    primaryContainer = AppleSystemBlue,
    onPrimaryContainer = Color.White,
    secondary = AppleSystemGray,
    onSecondary = Color.Black,
    secondaryContainer = AppleSystemGray5,
    onSecondaryContainer = Color.White,
    tertiary = AppleSystemGreen,
    onTertiary = Color.Black,
    tertiaryContainer = AppleSystemGray4,
    onTertiaryContainer = Color.White,
    background = CanvasDark,
    onBackground = OnSurfaceDark,
    surface = AppleSystemGray6,
    onSurface = OnSurfaceDark,
    surfaceVariant = AppleSystemGray5,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = StatusError,
    onError = Color.White,
    errorContainer = StatusErrorContainer,
    onErrorContainer = Color.White
)

val SocialHubAmoledColorScheme = SocialHubDarkColorScheme.copy(
    background = CanvasDark,
    surface = AppleSystemGray6
)

val SocialHubLightColorScheme = lightColorScheme(
    primary = AppleSystemBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE5F1FF),
    onPrimaryContainer = AppleSystemBlue,
    secondary = AppleSystemGray,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE5E5EA),
    onSecondaryContainer = Color.Black,
    tertiary = AppleSystemGreen,
    onTertiary = Color.White,
    background = CanvasLight,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE5E5EA),
    onSurfaceVariant = AppleSystemGray,
    outline = Color(0xFFC7C7CC),
    outlineVariant = Color(0xFFE5E5EA)
)

@Composable
fun MyApplicationTheme(
    themeMode: String = "dark",
    customAccent: Color = AppleSystemBlue,
    content: @Composable () -> Unit
) {
    val baseScheme = when (themeMode) {
        "light" -> SocialHubLightColorScheme
        else -> SocialHubDarkColorScheme
    }

    val finalScheme = baseScheme.copy(
        primary = customAccent,
        primaryContainer = customAccent
    )

    MaterialTheme(
        colorScheme = finalScheme,
        typography = Typography,
        content = content
    )
}
