package com.example.zebracoffee.presentation.myOrders


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.home.TopBarBlock
import com.example.zebracoffee.utils.Constant

@Composable
fun MyOrdersScreen(
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


@Composable
fun EmptyBlock(
    navigation: () -> Unit = {},
    textTitle: Int,
    textButton: Int,
    icon: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(bottom = 24.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Text(
            text = stringResource(id = textTitle),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontFamily = Constant.fontBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .width(185.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            onClick = {
                navigation()
            }) {

            Text(
                text = stringResource(id = textButton).uppercase(),
                fontSize = 12.sp,
                fontFamily = Constant.fontBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
