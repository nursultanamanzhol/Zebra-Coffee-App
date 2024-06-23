package com.example.zebracoffee.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    navigateToNotifications: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToQr: () -> Unit,
    navigateToLoyalty: () -> Unit,
    navigateToHardUpdate: () -> Unit,
    navigateToLocationQuery: () -> Unit,
    navigateToStoriesScreen: (String) -> Unit,
    navigateToCoffeeDetails: (Int) -> Unit,
    navigateToNewsDetails: (Int) -> Unit,
    navigateToPermissionNotification: () -> Unit,
) {

    Scaffold(
        bottomBar = {
            Column {
                MainNavBar(navController)
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        BottomBarNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            navigateToNotifications = navigateToNotifications,
            navigateToProfile = navigateToProfile,
            navigateToQr = navigateToQr,
            navigateToLoyalty = navigateToLoyalty,
            navigateToHardUpdate = navigateToHardUpdate,
            navigateToStoriesDetails = navigateToStoriesScreen,
            navigateToCoffeeDetails = navigateToCoffeeDetails,
            navigateToNewsDetails = navigateToNewsDetails,
            navigateToLocationQuery = navigateToLocationQuery,
            navigateToPermissionNotification = navigateToPermissionNotification
        )
    }
}
