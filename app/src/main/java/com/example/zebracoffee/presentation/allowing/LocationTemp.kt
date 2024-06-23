package com.example.zebracoffee.presentation.allowing

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(onPermissionStatusChange: (LocationPermissionStatus) -> Unit) {
    val fineLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    when {
        fineLocationPermissionState.status.isGranted -> {
        }

        fineLocationPermissionState.status.shouldShowRationale -> {
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp() {
    var permissionStatus by remember { mutableStateOf<LocationPermissionStatus?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Permission Demo") },
                elevation = 4.dp
            )
        },
        content = {
            LocationPermissionScreen(
                onPermissionStatusChange = { status ->
                    permissionStatus = status
                }
            )
        }
    )
    if (permissionStatus != null) {
        when (permissionStatus) {
            LocationPermissionStatus.Granted -> {}
            LocationPermissionStatus.GrantedOnlyWhileInUse -> {}
            LocationPermissionStatus.Denied -> {}
            else -> {}
        }
    }
}
enum class LocationPermissionStatus {
    Granted,
    GrantedOnlyWhileInUse,
    Denied
}