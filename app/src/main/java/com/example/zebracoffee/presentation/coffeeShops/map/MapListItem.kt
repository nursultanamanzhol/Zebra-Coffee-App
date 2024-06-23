package com.example.zebracoffee.presentation.coffeeShops.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.ClusteringData
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapListItem(
    item: ClusteringData,
    viewModel: CoffeeShopsViewModel,
    isLoading: Boolean,
    worksTime: Pair<String, Painter>,
    onClick: () -> Unit,
) {
    val fineLocationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    val currentLocation = viewModel.currentLocation
    val distanceLocation =
        if (currentLocation?.latitude == 0.0 && currentLocation?.longitude == 0.0 ||
            currentLocation?.latitude == 0.0 && currentLocation?.longitude == 0.0
        ) {
            ""
        } else {
            "${item.distance} km"
        }
    if (isLoading) {
        ShimmerBox(
            height = 186.dp,
            width = 361.dp
        )
    } else {
        ElevatedCard(
            modifier = Modifier
                .height(186.dp)
                .width(361.dp)
                .clickable {
                    onClick()
                },
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp,
                focusedElevation = 3.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .background(color = MediumGray2)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        model = viewModel.getValidImageAddress(item.cover),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Фоновое изображение",
                        loading = {
                            CircularProgressBox(
                                indicatorColor = Color.White
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .height(34.dp)
                            .width(52.dp)
                            .padding(top = 12.dp)
                            .background(ColorPrimary),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(14.dp)
                                .align(Alignment.CenterVertically),
                            imageVector = Icons.Default.Star,
                            contentDescription = "icon",
                            tint = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = item.rating.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }

                Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {

                    Text(
                        text = item.address,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Image(
                            painter = worksTime.second,
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = worksTime.first,
                            color = TextColor2Light,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        if (fineLocationPermissionState.status.isGranted) {
                            Text(
                                text = "・",
                                color = colorResource(id = R.color.notifiDateTime),
                                fontSize = 14.sp,
                                fontWeight = W500
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        if (fineLocationPermissionState.status.isGranted) {
                            Icon(
                                painter = painterResource(id = R.drawable.location_v2),
                                contentDescription = "",
                                tint = TextColor2Light
                            )
                        }
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = distanceLocation,
                            color = TextColor2Light,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}