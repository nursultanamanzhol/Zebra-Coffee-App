package com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.zebracoffee.R
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.GoogleMap
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapProperties
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapType
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapUiSettings
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.Marker
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MarkerState
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.rememberCameraPositionState
import com.example.zebracoffee.presentation.notification.FollowTheLink
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.ui.theme.TextColor2Dark
import com.example.zebracoffee.utils.Constant
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun CoffeeDetailsItemScreen(
    item: CoffeeDetails,
    modifier: Modifier = Modifier,
    theme: Boolean
) {

    var isMapLoaded by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(Unit) {
        cameraPositionState.position =
            CameraPosition.fromLatLngZoom(LatLng(item.latitude, item.longitude), 15f)
        Log.d("CurrentLocationCap", "${item.longitude}, ${item.longitude}")
        isMapLoaded = true
    }

    val uiSettings = remember {
        MapUiSettings(
            compassEnabled = false,  // Отключить компас
            indoorLevelPickerEnabled = false,  // Отключить выбор этажей внутри помещений
            mapToolbarEnabled = false,  // Отключить панель инструментов карты (с кнопками Просмотр на карте и Информация об объекте)
            myLocationButtonEnabled = false,  // Отключить кнопку моего местоположения
            rotationGesturesEnabled = false,  // Отключить вращение карты жестами
            scrollGesturesEnabled = false,  // Отключить прокрутку карты жестами
            scrollGesturesEnabledDuringRotateOrZoom = false,  // Отключить прокрутку карты во время масштабирования или поворота
            tiltGesturesEnabled = false,  // Отключить наклон карты жестами
            zoomControlsEnabled = false,  // Отключить кнопки управления масштабом
            zoomGesturesEnabled = false  // Отключить масштабирование карты жестами
        )
    }
    ElevatedCard(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(185.dp)
            .padding(1.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 3.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    cameraPositionState = cameraPositionState,
                    uiSettings = uiSettings,
                    properties = MapProperties(
                        isMyLocationEnabled = false,
                        mapType = MapType.NORMAL,
                        isBuildingEnabled = true,
                        isTrafficEnabled = true
                    ),
                    onMapLoaded = {
                        isMapLoaded = true
                    }
                ) {
                    val currentMarker = LatLng(item.latitude, item.longitude)
                    Log.d("CoffeeDetailsItemScreen", "$currentMarker")
                    MapMarkerDetails(
                        position = currentMarker,
                        context = LocalContext.current,
                        iconResourceId = R.drawable.ic_shop_marker
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier) {
                    Text(
                        text = item.address,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        modifier = Modifier,
                        text = "г. ${item.city}",
                        color = TextColor2Dark,
                        fontSize = 12.sp
                    )
                }
                FollowTheLink(
                    modifier = Modifier,
                    link = item.two_gis_link,
                    text = stringResource(R.string.map),
                    font = 12,
                    fontFamily = Constant.fontRegular,
                    buttonColors = ButtonDefaults.buttonColors(
                        backgroundColor = if (theme) colorResource(id = R.color.notifiBackIconDark) else colorResource(
                            id = R.color.notifiBackIcon
                        )
                    )
                )
            }
        }
    }


@Composable
fun MapMarkerDetails(
    context: Context,
    position: LatLng,
    @DrawableRes iconResourceId: Int,
) {
    val icon = bitmapDetailsFromVector(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(
            position = position
        ),
        icon = icon
    )
}

fun bitmapDetailsFromVector(
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}