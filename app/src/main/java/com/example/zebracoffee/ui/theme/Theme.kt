package com.example.zebracoffee.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = Black,
    onSurface = White,
    secondary = FillColor822,
    onSecondary = FillColor4Dark,
    inversePrimary =  TextColor2Dark,
    onPrimary = TextColor2Dark,
    onTertiary = FillColor5Dark,
    onBackground = onBackgroundBlack,
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onSurface = Black,
    secondary = FillColor8,
    onSecondary = FillColor4Light,
    inversePrimary = TextColor2Light,
    onPrimary = FillColor2,
    onTertiary = FillColor5,
    onBackground = White
)

@Composable
fun ZebracoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = fontFarfecht,
        content = content
    )
}