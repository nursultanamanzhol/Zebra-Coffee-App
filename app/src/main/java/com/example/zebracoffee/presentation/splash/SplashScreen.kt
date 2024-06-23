package com.example.zebracoffee.presentation.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zebracoffee.R
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.utils.Constant

private const val ACTIVE = "active"
private const val INACTIVE = "inactive"
private const val INCOMPLETE = "incomplete"

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navigateToMainScreen: () -> Unit,
    navigateToTypeNumber: () -> Unit,
    navigateToPersonalInfo: () -> Unit,
) {
    val splashScreenFinished by remember { mutableStateOf(false) }
    val userDataSate by viewModel.userData.collectAsStateWithLifecycle()

    LaunchedEffect(userDataSate) {
        viewModel.getUserData()
        var hasToken = viewModel.checkUserToken()

//        hasToken = viewModel.refreshToken()
//        if (!hasToken) {
//            navigateToTypeNumber()
//        }
//        Log.d("RefreshTokenUser", hasToken.toString())
        if (hasToken) {
            when (userDataSate) {
                is Resource.Success -> {
                    val userData = (userDataSate as Resource.Success<UserDto>).data
                    Log.d("SplashScreen", "2:$userDataSate")
                    when (userData?.status) {
                        ACTIVE -> {
                            navigateToMainScreen()
                        }

                        INACTIVE -> {
                            navigateToTypeNumber()
                        }

                        INCOMPLETE -> {
                            navigateToPersonalInfo()
                        }
                    }
                }

                is Resource.Loading -> {}

                is Resource.Failure -> {
                    navigateToMainScreen()
                }

                else -> Unit
            }
        } else {
            navigateToTypeNumber()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (!splashScreenFinished) {
            Text(
                modifier = Modifier.padding(horizontal = 40.dp),
                text = stringResource(id = R.string.app_name),
                fontSize = 32.sp,
                fontFamily = Constant.fontBold,
                color = Color.White
            )
        }
    }
}
