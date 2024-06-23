package com.example.zebracoffee.presentation.registration.welcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsWhite
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.utils.Constant

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeScreenViewModel,
    onCardClick: () -> Unit,
    onQrClick: () -> Unit,
) {
    SetupSystemBarsWhite(viewModel = viewModel)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val loyaltyCardState by viewModel.loyaltyCard.collectAsStateWithLifecycle()
    val userDataState by viewModel.userDataCard.collectAsStateWithLifecycle()

    val preloaderProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        UserCardWelcomeBlock(
            userDataState,
            loyaltyCardState,
            onQrClick = { onQrClick() },
            onCardClick = { onCardClick() },
        )

        ButtonBlock(navController)

        LottieAnimation(
            composition = composition,
            progress = { preloaderProgress },
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun UserCardWelcomeBlock(
    userItem: Resource<UserDto>,
    loyaltyCardState: Resource<LoyaltyCardResponseDto>,
    onCardClick: () -> Unit,
    onQrClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.padding(top = 70.dp),
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            fontFamily = Constant.fontBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = stringResource(id = R.string.welcome_sub_text),
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (loyaltyCardState is Resource.Loading || userItem is Resource.Loading) {
            ShimmerBox(
                height = 240.dp,
            )
        }
        //TODO MVI in jetpack compose read
        //TODO open state in each class like HomeState and write code
        //

        if (loyaltyCardState is Resource.Success && userItem is Resource.Success) {
            val loyaltyCardData = (loyaltyCardState).data
            val userCardData = (userItem).data
            if (loyaltyCardData != null && userCardData != null) {
                UserCard(
                    cardItem = loyaltyCardData,
                    userItem = userCardData,
                    onCardClick = { },
                    onQrClick = { }
                )
            }
        }
    }
}

@Composable
fun ButtonBlock(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        CustomButtonPrimary(
            text = stringResource(id = R.string.navigate_to_home),
            onClick = { navController.navigate(MainDestinations.MainScreen_route) }
        )
    }
}
