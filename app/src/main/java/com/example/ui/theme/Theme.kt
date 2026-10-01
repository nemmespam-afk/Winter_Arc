package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val WinterArcColorScheme = darkColorScheme(
    primary = IceCyan,
    onPrimary = ArcNavyDark,
    primaryContainer = ArcCardSelected,
    onPrimaryContainer = IceCyan,
    secondary = IceBlue,
    onSecondary = ArcNavyDark,
    secondaryContainer = ArcCard,
    onSecondaryContainer = IceGlow,
    tertiary = DisciplinedGreen,
    onTertiary = ArcNavyDark,
    background = ArcNavyDark,
    onBackground = ArcTextPrimary,
    surface = ArcSurface,
    onSurface = ArcTextPrimary,
    surfaceVariant = ArcCard,
    onSurfaceVariant = ArcTextSecondary,
    outline = ArcBorder,
    outlineVariant = ArcBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to true for authentic Winter Arc theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = WinterArcColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = ArcNavyDark.toArgb()
                window.navigationBarColor = ArcNavyDark.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
