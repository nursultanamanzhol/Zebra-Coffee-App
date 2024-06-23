package com.example.zebracoffee.presentation.coffeeShops.map

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.ClusteringData
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.rememberCameraPositionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapUiState(
    navigateToCoffeeDetails: (Int) -> Unit,
    modifier: Modifier,
    viewModelData: CoffeeShopsViewModel
) {
    val fineLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val currentLocation = viewModelData.currentLocation
    val isLoadingShops by viewModelData.isLoadingShops.collectAsStateWithLifecycle()

    val items = remember { mutableStateListOf<ClusteringData>() }
    val shopList by viewModelData.coffeeShopCityList.collectAsState()
    LaunchedEffect(Unit) {
        shopList.forEach { shop ->
            val position = LatLng(shop.latitude, shop.longitude)
            items.add(
                ClusteringData(
                    position,
                    itemTitle = "",
                    itemSnippet = "",
                    itemZIndex = 0f,
                    address = shop.address,
                    blocked = shop.blocked,
                    card_account_id = shop.card_account_id,
                    cash_account_id = shop.cash_account_id,
                    city = shop.city,
                    contacts = shop.contacts,
                    cover = shop.cover ?: "https://cdn-icons-png.flaticon.com/512/4054/4054617.png",
                    distance = shop.distance,
                    id = shop.id,
                    latitude = shop.latitude,
                    longitude = shop.longitude,
                    name = shop.name,
                    open_from = shop.open_from,
                    open_until = shop.open_until,
                    pager_active = shop.pager_active,
                    rating = shop.rating,
                    schedule = shop.schedule,
                    shop_images = shop.shop_images,
                    tis_token = shop.tis_token,
                    warehouse_address = shop.warehouse_address?: "warehouse_address",
                    warehouse_name = shop.warehouse_name?: "warehouse_name",
                    two_gis_link = shop.two_gis_link,
                )
            )
        }
    }

    //clustering
    if (isLoadingShops && ((currentLocation?.latitude == null && currentLocation?.longitude == null) || (currentLocation.latitude == 0.0 && currentLocation.longitude == 0.0))) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
            )
        }
    } else {
        var isMapOpenLocationUser by remember { mutableStateOf(false) }
        val cameraPositionState = rememberCameraPositionState()
        if (
            fineLocationPermissionState.status.isGranted || fineLocationPermissionState.status.shouldShowRationale
        ) {
            if (currentLocation?.latitude == null && currentLocation?.longitude == null || (currentLocation.latitude == 0.0 && currentLocation.longitude == 0.0)) {
                viewModelData.getCurrentLocation()
            } else {
                if (!isMapOpenLocationUser) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(currentLocation.latitude, currentLocation.longitude),
                        15f
                    )
                    isMapOpenLocationUser = true
                }
            }

        }
        val fineLocationPermissionState =
            rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        MapContent(
            cameraPositionState = cameraPositionState,
            fineLocationPermissionState = fineLocationPermissionState,
            currentLocation = currentLocation,
            viewModelData = viewModelData,
            isLoadingShops = isLoadingShops,
            navigateToCoffeeDetails = navigateToCoffeeDetails,
            items = items
        )
    }
}





