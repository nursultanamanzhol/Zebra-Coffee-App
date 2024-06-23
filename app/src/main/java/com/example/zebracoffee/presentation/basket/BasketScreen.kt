package com.example.zebracoffee.presentation.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.home.TopBarBlock
import com.example.zebracoffee.presentation.myOrders.EmptyBlock

@Composable
fun BasketScreen(
    navigateToProfile: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToCoffeeShop: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBarBlock(
            navigateToProfile = navigateToProfile,
            navigateToNotifications = navigateToNotifications
        )
        EmptyBlock(
            { navigateToCoffeeShop() },
            textTitle = R.string.empty_screen_title_text,
            textButton = R.string.navigate_to_coffee_shop,
            R.drawable.ic_order
        )
    }
}
