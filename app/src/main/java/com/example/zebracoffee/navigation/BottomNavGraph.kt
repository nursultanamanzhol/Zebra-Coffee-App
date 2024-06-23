package com.example.zebracoffee.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zebracoffee.presentation.basket.BasketScreen
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsScreen
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.home.HomeScreen
import com.example.zebracoffee.presentation.home.HomeViewModel
import com.example.zebracoffee.presentation.myOrders.MyOrdersScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToProfile: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToQr: () -> Unit,
    navigateToLoyalty: () -> Unit,
    navigateToHardUpdate: () -> Unit,
    navigateToStoriesDetails: (String) -> Unit,
    navigateToCoffeeDetails: (Int) -> Unit,
    navigateToNewsDetails: (Int) -> Unit,
    navigateToLocationQuery: () -> Unit,
    navigateToPermissionNotification: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.HomeScreen_route,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(MainDestinations.HomeScreen_route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                navigateToProfile = navigateToProfile,
                navigateToNotifications = navigateToNotifications,
                navigateToQr = navigateToQr,
                navigateToLoyalty = navigateToLoyalty,
                navigateToHardUpdate = navigateToHardUpdate,
                navigateToStoriesDetails = navigateToStoriesDetails,
                navigateToNewsDetails = navigateToNewsDetails,
                navigateToPermissionNotification = navigateToPermissionNotification
            )
        }

        composable(MainDestinations.CoffeeShopScreen_route) {
            val coffeeShopModel: CoffeeShopsViewModel = hiltViewModel()
            CoffeeShopsScreen(
                viewModel = coffeeShopModel,
                navigateToCoffeeDetails = navigateToCoffeeDetails,
                navigateToLocationQuery = navigateToLocationQuery,
            )
        }

        composable(MainDestinations.MyOrderScreen_route) {
            MyOrdersScreen(
                navigateToProfile = navigateToProfile,
                navigateToNotifications = navigateToNotifications,
                navigateToCoffeeShop = {
                    navController.navigate(MainDestinations.CoffeeShopScreen_route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(MainDestinations.BasketScreen_route) {
            BasketScreen(
                navigateToProfile = navigateToProfile,
                navigateToNotifications = navigateToNotifications,
                navigateToCoffeeShop = {
                    navController.navigate(MainDestinations.CoffeeShopScreen_route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}