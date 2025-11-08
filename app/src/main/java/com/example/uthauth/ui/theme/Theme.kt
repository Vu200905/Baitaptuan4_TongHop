package com.example.uthauth.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
private val DarkColorScheme = darkColorScheme(
    primary = DarkButton,
    background = DarkThemeBackground
)
private val LightColorScheme = lightColorScheme(
    primary = WhiteButton,
    background = WhiteThemeBackground
)
private val PinkColorScheme = lightColorScheme(
    primary = PinkButton,
    background = PinkThemeBackground
)
private val BlueColorScheme = lightColorScheme(
    primary = BlueButton,
    background = BlueThemeBackground
)

@Composable
fun UTHAuthTheme(
    selectedTheme: String = "Light",
    content: @Composable () -> Unit
) {
    val colorScheme = when (selectedTheme) {
        "Light" -> LightColorScheme
        "Dark" -> DarkColorScheme
        "Pink" -> PinkColorScheme
        "Blue" -> BlueColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = (selectedTheme != "Dark")
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
