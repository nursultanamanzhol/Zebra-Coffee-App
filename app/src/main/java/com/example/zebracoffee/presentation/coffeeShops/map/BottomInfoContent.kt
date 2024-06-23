package com.example.zebracoffee.presentation.coffeeShops.map

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.CameraPositionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomInfoContent(
    fineLocationPermissionState: PermissionState,
    viewModelData: CoffeeShopsViewModel,
    cameraPositionState: CameraPositionState,
    currentLocation: Location?
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 38.dp,
                        topEnd = 38.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .size(44.dp)
                .clickable {
                    val currentPosition = cameraPositionState.position
                    val newZoom = currentPosition.zoom + 0.5f
                    val newPosition = CameraPosition
                        .Builder(currentPosition)
                        .zoom(newZoom)
                        .build()
                    cameraPositionState.position = newPosition
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Your location"
            )
        }

        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 38.dp,
                        bottomEnd = 38.dp
                    )
                )
                .size(44.dp)
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                    val currentPosition = cameraPositionState.position
                    val newZoom = currentPosition.zoom - 0.5f
                    val newPosition = CameraPosition
                        .Builder(currentPosition)
                        .zoom(newZoom)
                        .build()
                    cameraPositionState.position = newPosition
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = "Your location"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .size(44.dp)
                .clickable {
                    if (fineLocationPermissionState.status.isGranted) {
                        viewModelData.getCurrentLocation()
                        if (currentLocation?.latitude == null && currentLocation?.longitude == null || (currentLocation.latitude == 0.0 && currentLocation.longitude == 0.0)) {
                            viewModelData.getCurrentLocation()
                        } else {
                            viewModelData.getCurrentLocation()
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(
                                    LatLng(
                                        currentLocation.latitude,
                                        currentLocation.longitude
                                    ),
                                    15f
                                )
                        }
                    } else if (!fineLocationPermissionState.status.isGranted || !fineLocationPermissionState.status.shouldShowRationale) {
                        fineLocationPermissionState.launchPermissionRequest()

                    } else {
                        if (currentLocation?.latitude == null && currentLocation?.longitude == null || (currentLocation.latitude == 0.0 && currentLocation.longitude == 0.0)) {
                            viewModelData.getCurrentLocation()
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(
                                    LatLng(
                                        currentLocation?.latitude ?: 0.0,
                                        currentLocation?.longitude ?: 0.0
                                    ),
                                    15f
                                )
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "Your location"
            )

        }
    }
}
