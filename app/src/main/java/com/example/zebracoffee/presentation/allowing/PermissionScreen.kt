package com.example.zebracoffee.presentation.allowing

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
@SuppressLint("InlinedApi")
@Composable
fun PermissionScreen(navController: NavHostController) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }
    if (permissionState.status.isGranted) {
        Column() {
            /*navController.navigate(Screen.HomeScreen.route)*/
        }
    } else {
        Column() {
        }
        if (permissionState.status.shouldShowRationale) {
            /*navController.navigate(Screen.HomeScreen.route)*/
        } else {
            /*navController.navigate(Screen.HomeScreen.route)*/
        }
    }
}
