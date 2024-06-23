package com.example.zebracoffee.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.zebracoffee.R
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.FillColor2
import com.example.zebracoffee.utils.Constant

@Composable
fun MainNavBar(
    navController: NavHostController
) {
    val screens = listOf(
        MainNavBarScreen.Home,
        MainNavBarScreen.CoffeeShop,
        MainNavBarScreen.MyOrderScreen,
        MainNavBarScreen.BasketScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .height(50.dp)
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainNavBarScreen,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = screen.icon),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = screen.title,
                    fontSize = 12.sp,
                    fontFamily = Constant.fontRegular,
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = ColorPrimary,
            selectedTextColor = FillColor2,
            unselectedTextColor = FillColor2,
            unselectedIconColor = FillColor2,
            indicatorColor = MaterialTheme.colorScheme.secondary
        ),
        selected = currentRoute == screen.route,

        onClick = {
            navController.navigate(screen.route) {
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

sealed class MainNavBarScreen(
    var route: String,
    var title: String,
    @DrawableRes val icon: Int,
) {
    data object Home : MainNavBarScreen(
        MainDestinations.HomeScreen_route,
        "Главная",
        icon = R.drawable.ic_home
    )

    data object CoffeeShop : MainNavBarScreen(
        MainDestinations.CoffeeShopScreen_route,
        "Кофейни",
        icon = R.drawable.ic_bottom_coffen
    )
    data object MyOrderScreen : MainNavBarScreen(
        MainDestinations.MyOrderScreen_route,
        "Мои заказы",
        icon = R.drawable.ic_profile
    )

    data object BasketScreen : MainNavBarScreen(
        MainDestinations.BasketScreen_route,
        "Корзина",
        icon = R.drawable.ic_basket
    )
}
