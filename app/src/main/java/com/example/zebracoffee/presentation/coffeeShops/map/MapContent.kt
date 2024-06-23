package com.example.zebracoffee.presentation.coffeeShops.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.ClusteringData
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.CameraPositionState
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.GoogleMap
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapProperties
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapType
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapUiSettings
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.Marker
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MarkerState
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


private enum class ClusteringType {
    CustomRenderer
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapContent(
    cameraPositionState: CameraPositionState,
    fineLocationPermissionState: PermissionState,
    currentLocation: Location?,
    viewModelData: CoffeeShopsViewModel,
    isLoadingShops: Boolean,
    navigateToCoffeeDetails: (Int) -> Unit,
    items: List<ClusteringData>,
) {

    var clusteringType by remember {
        mutableStateOf(ClusteringType.CustomRenderer)
    }

    val userImage by viewModelData.userImage.collectAsStateWithLifecycle()
    var isMapLoaded by remember { mutableStateOf(false) }
    val uiSettingsUiState = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false,
            tiltGesturesEnabled = false,
            mapToolbarEnabled = false
        )
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
                .background(color = MediumGray2)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettingsUiState,
                properties = MapProperties(
                    isMyLocationEnabled = false,
                    mapType = MapType.NORMAL,
                    isBuildingEnabled = true,
                    isTrafficEnabled = true
                ),
                onMapClick = {
                    viewModelData.setClickFacilityFalse()
                },
                onMapLoaded = {
                    isMapLoaded = true
                }
            ) {
                //user location
                if (currentLocation?.latitude == null || currentLocation.latitude == 0.0) {
                    viewModelData.getCurrentLocation()
                } else {
                    val density = LocalDensity.current
                    MapMarkerUser(
                        position = LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        ),
                        imageUrl = userImage,
                        density = density
                    )
                }
                //clustering items
                when (clusteringType) {
                    ClusteringType.CustomRenderer -> {
                        CustomRendererClustering(
                            viewModelData = viewModelData,
                            items = items,
                            onClick = {
                            }
                        )

                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 17.dp)
                    .align(Alignment.CenterEnd)
                    .background(Color.Transparent)
            ) {
                BottomInfoContent(
                    fineLocationPermissionState = fineLocationPermissionState,
                    viewModelData = viewModelData,
                    cameraPositionState = cameraPositionState,
                    currentLocation = currentLocation
                )
            }
            val selectedShop by viewModelData.selectedShop.collectAsState()
            val clickFacility by viewModelData.clickFacility.collectAsStateWithLifecycle()
            if (clickFacility) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(15.dp)
                        .background(Color.Transparent)
                ) {
                    selectedShop?.let {
                        MapListItem(
                            item = it,
                            viewModel = viewModelData,
                            isLoading = isLoadingShops,
                            worksTime = viewModelData.getWorksTime(
                                openFrom = it.open_from,
                                openUntil = it.open_until
                            )
                        ) {
                            navigateToCoffeeDetails(it.id)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MapMarkerUser(
    position: LatLng,
    imageUrl: String,
    density: Density
) {
    val coroutineScope = rememberCoroutineScope()
    var markerBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(imageUrl) {
        coroutineScope.launch {
            try {
                val url = URL("${imageBaseUrl}${imageUrl}")
                withContext(Dispatchers.IO) {
                    val inputStream = url.openConnection().getInputStream()
                    val res: Bitmap? = BitmapFactory.decodeStream(inputStream)

                    val scaledBitmap = res?.let { bitmap ->
                        val size = with(density) { 35.dp.toPx().toInt() }
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)
                        Bitmap.createBitmap(
                            scaledBitmap.width,
                            scaledBitmap.height,
                            Bitmap.Config.ARGB_8888
                        ).apply {
                            val canvas = Canvas(this)
                            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                            val shader = BitmapShader(
                                scaledBitmap,
                                Shader.TileMode.CLAMP,
                                Shader.TileMode.CLAMP
                            )
                            paint.shader = shader
                            val rect = RectF(
                                0f,
                                0f,
                                scaledBitmap.width.toFloat(),
                                scaledBitmap.height.toFloat()
                            )
                            val radius = with(density) { 20.dp.toPx() }
                            canvas.drawRoundRect(rect, radius, radius, paint)
                        }
                    }
                    markerBitmap = scaledBitmap
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    markerBitmap?.let { bitmap ->
        Marker(
            state = MarkerState(
                position = position
            ),
            icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        )
    }
}
