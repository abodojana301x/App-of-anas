package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SocialHubDarkColorScheme = darkColorScheme(
    primary = PrimaryViolet,
    onPrimary = OnPrimaryViolet,
    primaryContainer = PrimaryContainerViolet,
    onPrimaryContainer = OnPrimaryContainerViolet,
    secondary = Color(0xFFC3C6D5),
    onSecondary = Color(0xFF2C303C),
    secondaryContainer = Color(0xFF434653),
    onSecondaryContainer = Color(0xFFB1B4C3),
    tertiary = StatusWarning,
    onTertiary = Color(0xFF373100),
    tertiaryContainer = Color(0xFFAF9E00),
    onTertiaryContainer = Color(0xFF3B3500),
    background = CanvasDark,
    onBackground = OnSurfaceDark,
    surface = CanvasDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = StatusError,
    onError = Color(0xFF690005),
    errorContainer = StatusErrorContainer,
    onErrorContainer = Color(0xFFFFDAD6)
)

val SocialHubAmoledColorScheme = darkColorScheme(
    primary = PrimaryViolet,
    onPrimary = OnPrimaryViolet,
    primaryContainer = PrimaryContainerViolet,
    onPrimaryContainer = OnPrimaryContainerViolet,
    secondary = Color(0xFFC3C6D5),
    onSecondary = Color(0xFF2C303C),
    secondaryContainer = Color(0xFF282A2F),
    onSecondaryContainer = Color(0xFFB1B4C3),
    tertiary = StatusWarning,
    onTertiary = Color(0xFF373100),
    background = CanvasAmoled,
    onBackground = OnSurfaceDark,
    surface = CanvasAmoled,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = StatusError,
    onError = Color(0xFF690005)
)

val SocialHubLightColorScheme = lightColorScheme(
    primary = Color(0xFF6A4EB8),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8DDFF),
    onPrimaryContainer = Color(0xFF21005E),
    secondary = Color(0xFF5B5D6B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDFE2F1),
    onSecondaryContainer = Color(0xFF171B26),
    tertiary = Color(0xFF6F6400),
    onTertiary = Color.White,
    background = CanvasLight,
    onBackground = Color(0xFF1A1B21),
    surface = Color.White,
    onSurface = Color(0xFF1A1B21),
    surfaceVariant = Color(0xFFE3E2E9),
    onSurfaceVariant = Color(0xFF45464F),
    outline = Color(0xFF767680)
)

@Composable
fun MyApplicationTheme(
    themeMode: String = "dark",
    customAccent: Color = PrimaryContainerViolet,
    content: @Composable () -> Unit
) {
    val baseScheme = when (themeMode) {
        "amoled" -> SocialHubAmoledColorScheme
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
