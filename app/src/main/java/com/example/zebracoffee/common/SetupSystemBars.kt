package com.example.zebracoffee.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.presentation.registration.welcomeScreen.WelcomeScreenViewModel
import com.example.zebracoffee.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetupSystemBarsBlack() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black, // Черный цвет фона системного бара
            darkIcons = false // Светлые иконки для лучшей видимости на темном фоне
        )
    }
}

@Composable
fun SetupSystemBarsWhite(
    viewModel: WelcomeScreenViewModel
) {
    val systemUiController = rememberSystemUiController()
    val theme by viewModel.theme.collectAsState()
    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }
    val color: Color = if (darkTheme) {
        Color.Black
    }else if(!darkTheme){
        White
    } else {
        White
    }

    val colorIcons: Boolean = if (darkTheme) {
        false
    }else if(!darkTheme){
        true
    } else {
        true
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color, // Черный цвет фона системного бара
            darkIcons = colorIcons // Светлые иконки для лучшей видимости на темном фоне
        )
    }
}
