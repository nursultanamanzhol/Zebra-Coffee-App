package com.example.zebracoffee.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.zebracoffee.navigation.MainNavGraph
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.home.HomeViewModel
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.ui.theme.ZebracoffeeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val shopViewModel by viewModels<CoffeeShopsViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            val theme by viewModel.theme.collectAsState()

            val darkTheme = when (theme) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                else -> isSystemInDarkTheme()
            }

            ZebracoffeeTheme(darkTheme = darkTheme) {
                Box(Modifier.safeDrawingPadding()) {
                    val startDestination = viewModel.startDestination
                    MainNavGraph()
                    LaunchedEffect(key1 = darkTheme) {
                        val color = if (darkTheme) {
                            Color(0xFF000000)
                        } else {
                            Color(0xFFFFFFFF)
                        }
                        window.statusBarColor = color.toArgb()

                        WindowInsetsControllerCompat(window, window.decorView).apply {
                            isAppearanceLightStatusBars =
                                !darkTheme
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.isInitialized = false
        shopViewModel.isShopInitialized = false
        shopViewModel.isShopInitializedCountry = false
        homeViewModel.visually = false
    }
}
