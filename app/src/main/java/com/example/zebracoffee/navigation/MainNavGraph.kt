package com.example.zebracoffee.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.presentation.allowing.HardUpdatingPage
import com.example.zebracoffee.presentation.allowing.LocationAccessQuery
import com.example.zebracoffee.presentation.allowing.NotificationsQuery
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails.CoffeeShopDetailsScreen
import com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails.CoffeeShopDetailsViewModel
import com.example.zebracoffee.presentation.coffeeShops.reviewScreen.ReviewScreen
import com.example.zebracoffee.presentation.coffeeShops.reviewScreen.ReviewViewModel
import com.example.zebracoffee.presentation.home.HomeViewModel
import com.example.zebracoffee.presentation.home.news.NewsDetailsScreen
import com.example.zebracoffee.presentation.home.stories.StoriesScreen
import com.example.zebracoffee.presentation.home.stories.StoriesScreenViewModel
import com.example.zebracoffee.presentation.loyalProgram.LoyalScreen
import com.example.zebracoffee.presentation.loyalProgram.LoyalScreenViewModel
import com.example.zebracoffee.presentation.myQr.MyQrScreen
import com.example.zebracoffee.presentation.myQr.MyQrViewModel
import com.example.zebracoffee.presentation.notification.NotificationScreen
import com.example.zebracoffee.presentation.notification.NotificationsViewModel
import com.example.zebracoffee.presentation.onboarding.OnBoardingScreen
import com.example.zebracoffee.presentation.profileScreen.ProfileScreen
import com.example.zebracoffee.presentation.profileScreen.ProfileScreenViewModel
import com.example.zebracoffee.presentation.profileScreen.aboutUs.AboutUsScreen
import com.example.zebracoffee.presentation.profileScreen.aboutUs.pdf.PdfLegalScreen
import com.example.zebracoffee.presentation.profileScreen.aboutUs.pdf.PdfTermScreen
import com.example.zebracoffee.presentation.profileScreen.userInfoScreen.UserInfoScreen
import com.example.zebracoffee.presentation.profileScreen.userInfoScreen.UserInfoScreenViewModel
import com.example.zebracoffee.presentation.registration.chooseAvatar.AvatarScreen
import com.example.zebracoffee.presentation.registration.chooseAvatar.AvatarViewModel
import com.example.zebracoffee.presentation.registration.enterCodeScreen.EnterCodeScreen
import com.example.zebracoffee.presentation.registration.enterCodeScreen.EnterCodeViewModel
import com.example.zebracoffee.presentation.registration.personalInfo.PersonalInfoScreen
import com.example.zebracoffee.presentation.registration.personalInfo.PersonalInfoViewModel
import com.example.zebracoffee.presentation.registration.typeNumber.TypeNumberScreen
import com.example.zebracoffee.presentation.registration.typeNumber.TypeNumberViewModel
import com.example.zebracoffee.presentation.registration.welcomeScreen.WelcomeScreen
import com.example.zebracoffee.presentation.registration.welcomeScreen.WelcomeScreenViewModel
import com.example.zebracoffee.presentation.splash.SplashScreen
import com.example.zebracoffee.presentation.splash.SplashScreenViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
/*
    startDestination: String
*/
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.SPLASH_ROUTE,
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
        composable(MainDestinations.SPLASH_ROUTE) {
            val splashViewModel = hiltViewModel<SplashScreenViewModel>()
            SplashScreen(
                viewModel = splashViewModel,
                navigateToMainScreen = { navController.navigate(MainDestinations.MainScreen_route) },
                navigateToTypeNumber = { navController.navigate(MainDestinations.OnBoardingScreen_route) },
                navigateToPersonalInfo = { navController.navigate(MainDestinations.PersonalInfoScreen_route) }
            )
        }

        composable(route = MainDestinations.OnBoardingScreen_route) {
/*
            val viewModel = hiltViewModel<OnBoardingViewModel>()
*/
                OnBoardingScreen(
                navigateToTypeNumberScreen = { navController.navigate(MainDestinations.TypeNumberScreen_route) },
                /*event = {
                    viewModel.onEvent(it)
                }*/
            )
        }

        composable(route = MainDestinations.MainScreen_route) {
            MainScreen(
                navigateToProfile = { navController.navigate(MainDestinations.ProfileScreen_route) },
                navigateToNotifications = { navController.navigate(MainDestinations.NotificationScreen_route) },
                navigateToQr = { navController.navigate(MainDestinations.MyQrScreen_route) },
                navigateToLoyalty = { navController.navigate(route = MainDestinations.LoyalScreen_route) },
                navigateToHardUpdate = { navController.navigate(route = MainDestinations.HardUpdatingPage_route) },
                navigateToStoriesScreen = { listUri ->
                    navController.navigate(route = "${MainDestinations.StoriesScreen_route}/$listUri")
                },
                navigateToCoffeeDetails = { id ->
                    navController.navigate(route = "${MainDestinations.DetailsShopScreen_route}/$id")
                },
                navigateToNewsDetails = { id ->
                    navController.navigate(route = "${MainDestinations.NewsDetailsScreen_route}/$id")
                },
                navigateToLocationQuery = {
                    navController.navigate(route = MainDestinations.LocationAccessQuery_route)
                },
                navigateToPermissionNotification = {
                    navController.navigate(
                        route =
                        MainDestinations.NotificationsQuery_route
                    )
                },
            )
        }

        composable(route = MainDestinations.TypeNumberScreen_route) {
            val typeNumberViewModel = hiltViewModel<TypeNumberViewModel>()
            TypeNumberScreen(
                navController = navController,
                viewModel = typeNumberViewModel,
                onPrivacyPolicyClick = { navController.navigate(MainDestinations.PdfLegalScreen_route) },
                onTermsOfServiceClick = { navController.navigate(MainDestinations.PdfTermScreen_route) }
            )
        }
        composable(
            route = "${MainDestinations.EnterCodeScreen_route}/{phone_number}"
        ) { navBackStackEntry ->
            val phoneNumber = navBackStackEntry.arguments?.getString("phone_number")
            val enterCodeViewModel = hiltViewModel<EnterCodeViewModel>()
            EnterCodeScreen(navController, phoneNumber!!, enterCodeViewModel)
        }

        composable(route = MainDestinations.PersonalInfoScreen_route) {
            val personalInfoViewModel = hiltViewModel<PersonalInfoViewModel>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PersonalInfoScreen(navController, personalInfoViewModel)
            }
        }

        composable(route = "${MainDestinations.AvatarScreen_route}/{nameValue}") { navBackStackEntry ->
            val avatarViewModel = hiltViewModel<AvatarViewModel>()
            val nameValue = navBackStackEntry.arguments?.getString("nameValue")
            AvatarScreen(
                navController = navController,
                viewModel = avatarViewModel,
                nameValue = nameValue!!
            )
        }
        composable(route = MainDestinations.WelcomeScreen_route) {
            val welcomeViewModel = hiltViewModel<WelcomeScreenViewModel>()
            WelcomeScreen(
                navController,
                welcomeViewModel,
                onCardClick = { navController.navigate(MainDestinations.LoyalScreen_route) },
                onQrClick = { navController.navigate(MainDestinations.MyQrScreen_route) }
            )
        }

        composable(route = MainDestinations.NotificationScreen_route) {
            val viewModel = hiltViewModel<NotificationsViewModel>()
            NotificationScreen(navController, viewModel)
        }

        composable(route = MainDestinations.ProfileScreen_route) {
            val profileViewModel = hiltViewModel<ProfileScreenViewModel>()
            ProfileScreen(navController = navController, viewModel = profileViewModel)
        }
        composable(route = MainDestinations.MyQrScreen_route) {
            val qrViewModel = hiltViewModel<MyQrViewModel>()
            MyQrScreen(viewModel = qrViewModel, navController = navController)
        }
        composable(route = MainDestinations.LoyalScreen_route) {
            val viewModelLoyal = hiltViewModel<LoyalScreenViewModel>()
            LoyalScreen(navController, viewModelLoyal)
        }
        composable(route = MainDestinations.HardUpdatingPage_route) {
            HardUpdatingPage()
        }
        composable(route = MainDestinations.UserInfoScreen_route) {
            val userInfoViewModel = hiltViewModel<UserInfoScreenViewModel>()
            UserInfoScreen(navController = navController, viewModel = userInfoViewModel)
        }
        composable(route = MainDestinations.AboutUsScreen_route) {
            AboutUsScreen(navController = navController)
        }

        composable(
            route = MainDestinations.LocationAccessQuery_route,
            enterTransition = {
                fadeIn(animationSpec = tween(1500)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(1500)
                )
            },
            popEnterTransition = { fadeIn(animationSpec = tween(1500)) },
        ) {
            val viewModelShop = hiltViewModel<CoffeeShopsViewModel>()
            LocationAccessQuery(
                navController = navController,
                viewModel = viewModelShop
            )
        }

        composable(
            route = "${MainDestinations.DetailsShopScreen_route}/{id}",
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            val viewModelCoffee = hiltViewModel<CoffeeShopDetailsViewModel>()
            CoffeeShopDetailsScreen(viewModelCoffee, navController, id)
        }

        composable(
            route = "${MainDestinations.NewsDetailsScreen_route}/{id}",
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            val newsDetailsViewModel = hiltViewModel<HomeViewModel>()
            NewsDetailsScreen(
                viewModel = newsDetailsViewModel,
                id = id!!,
                navController = navController
            )
        }

        composable(route = "${MainDestinations.ReviewScreen_route}/{id}") { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            val viewModel = hiltViewModel<ReviewViewModel>()
            ReviewScreen(viewModel = viewModel, id = id, navController = navController)
        }

        composable(route = MainDestinations.PdfTermScreen_route) {
            PdfTermScreen()
        }

        composable(route = MainDestinations.PdfLegalScreen_route) {
            PdfLegalScreen()
        }

        composable(route = "${MainDestinations.StoriesScreen_route}/{listUri}") { navBackStackEntry ->
            val instagramStoryViewModel = hiltViewModel<StoriesScreenViewModel>()
            val listUri = navBackStackEntry.arguments?.getString("listUri")
            var list by remember { mutableStateOf<List<Stories>>(emptyList()) }
            if (!listUri.isNullOrEmpty()) {
                list = Gson().fromJson(listUri, object : TypeToken<List<Stories>>() {}.type)
            }
            StoriesScreen(navController, instagramStoryViewModel, list)
        }
        composable(
            MainDestinations.NotificationsQuery_route,
            enterTransition = {
                fadeIn(animationSpec = tween(1500)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(1500)
                )
            },
            popEnterTransition = { fadeIn(animationSpec = tween(1500)) },
        ) {
            NotificationsQuery(
                navigateToMainScreen = { navController.navigate(route = MainDestinations.MainScreen_route) })
        }

    }
}

private object MainScreens {
    const val OnBoardingScreen = "OnBoardingScreen"
    const val SPLASH = "splash"

    const val TypeNumberScreen = "TypeNumberScreen"
    const val EnterCodeScreen = "EnterCodeScreen"
    const val AvatarScreen = "AvatarScreen"
    const val PersonalInfoScreen = "PersonalInfoScreen"
    const val WelcomeScreen = "WelcomeScreen"

    const val HomeScreen = "HomeScreen"
    const val CoffeeShopScreen = "CoffeeShopScreen"
    const val MyOrderScreen = "MyOrderScreen"
    const val BasketScreen = "BasketScreen"

    const val NotificationScreen = "NotificationScreen"
    const val ProfileScreen = "ProfileScreen"
    const val MyQrScreen = "MyQrScreen"
    const val LoyalScreen = "LoyalScreen"
    const val HardUpdatingPage = "HardUpdatingPage"
    const val UserInfoScreen = "UserInfoScreen"
    const val AboutUsScreen = "AboutUsScreen"

    const val LocationAccessQuery = "LocationAccessQuery"
    const val NotificationsQuery = "NotificationsQuery"
    const val DetailsShopScreen = "DetailsShopScreen_route"
    const val ReviewScreen = "ReviewScreen"

    const val PdfTermScreen = "PdfTermScreen"
    const val PdfLegalScreen = "PdfLegalScreen"

    const val StoriesScreen = "StoriesScreen"
    const val NewsDetailsScreen = "NewsDetailsScreen"

    const val MainScreen = "MainScreen"

}

object MainDestinations {
    const val OnBoardingScreen_route = MainScreens.OnBoardingScreen
    const val SPLASH_ROUTE = MainScreens.SPLASH
    const val TypeNumberScreen_route = MainScreens.TypeNumberScreen
    const val EnterCodeScreen_route = MainScreens.EnterCodeScreen
    const val PersonalInfoScreen_route = MainScreens.PersonalInfoScreen
    const val AvatarScreen_route = MainScreens.AvatarScreen
    const val WelcomeScreen_route = MainScreens.WelcomeScreen

    const val HomeScreen_route = MainScreens.HomeScreen
    const val CoffeeShopScreen_route = MainScreens.CoffeeShopScreen
    const val MyOrderScreen_route = MainScreens.MyOrderScreen
    const val BasketScreen_route = MainScreens.BasketScreen

    const val NotificationScreen_route = MainScreens.NotificationScreen
    const val ProfileScreen_route = MainScreens.ProfileScreen
    const val MyQrScreen_route = MainScreens.MyQrScreen
    const val LoyalScreen_route = MainScreens.LoyalScreen
    const val HardUpdatingPage_route = MainScreens.HardUpdatingPage
    const val UserInfoScreen_route = MainScreens.UserInfoScreen
    const val AboutUsScreen_route = MainScreens.AboutUsScreen

    const val LocationAccessQuery_route = MainScreens.LocationAccessQuery
    const val DetailsShopScreen_route = MainScreens.DetailsShopScreen
    const val ReviewScreen_route = MainScreens.ReviewScreen

    const val PdfTermScreen_route = MainScreens.PdfTermScreen
    const val PdfLegalScreen_route = MainScreens.PdfLegalScreen

    const val StoriesScreen_route = MainScreens.StoriesScreen
    const val NotificationsQuery_route = MainScreens.NotificationsQuery
    const val NewsDetailsScreen_route = MainScreens.NewsDetailsScreen


    const val MainScreen_route = MainScreens.MainScreen
}