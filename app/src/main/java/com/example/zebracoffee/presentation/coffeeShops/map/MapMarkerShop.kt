package com.example.zebracoffee.presentation.coffeeShops.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.Marker
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MarkerState
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.rememberCameraPositionState
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapMarkerShop(
    context: Context,
    position: LatLng,
    onClick: () -> Unit,
    @DrawableRes iconResourceId: Int,
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    val cameraPositionState = rememberCameraPositionState()
    Marker(
        state = MarkerState(
            position = position
        ),
        icon = icon,
        onInfoWindowClick = {},
        onClick = { it ->
            onClick()
            false
        },
    )
}


fun bitmapDescriptorFromVector(
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

    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}


