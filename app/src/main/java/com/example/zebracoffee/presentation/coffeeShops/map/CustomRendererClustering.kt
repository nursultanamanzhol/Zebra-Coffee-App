package com.example.zebracoffee.presentation.coffeeShops.map

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.coffeeShops.CoffeeShopsViewModel
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.Clustering
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.ClusteringData
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.library.MapsComposeExperimentalApi
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.rememberClusterManager
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.rememberClusterRenderer
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.White
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm

private val TAG = "Nursultan"

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun CustomRendererClustering(
    viewModelData: CoffeeShopsViewModel,
    items: List<ClusteringData>,
    onClick: (ClusteringData) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val clusterManager = rememberClusterManager<ClusteringData>()
    clusterManager?.setAlgorithm(
        NonHierarchicalViewBasedAlgorithm(
            screenWidth.value.toInt(),
            screenHeight.value.toInt()
        )
    )
    val renderer = rememberClusterRenderer(
        clusterContent = { cluster ->
            ClusteringContent(
                modifier = Modifier.size(35.dp),
                text = "%,d".format(cluster.size),
                color = ColorPrimary,
            )
        },
        clusterItemContent = {
            items.forEach { item ->
                ItemContent(
                    viewModel = viewModelData,
                    resourceId = R.drawable.ic_shop_marker,
                    text = item.itemTitle,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {}

                ) {
                    onClick(item)
                }
            }
        },
        clusterManager = clusterManager,
    )
    SideEffect {
        clusterManager ?: return@SideEffect
        clusterManager.setOnClusterClickListener {
            Log.d(TAG, "Cluster clicked! $it")
            false
        }
        clusterManager.setOnClusterItemClickListener {
            viewModelData.setSelectedShop(item = it)
            viewModelData.setClickFacilityTrue()
            Log.d(TAG, "Cluster item clicked! $it")


            false
        }
        clusterManager.setOnClusterItemInfoWindowClickListener {
            Log.d(TAG, "Cluster item info window clicked! $it")
        }
    }
    SideEffect {
        if (clusterManager?.renderer != renderer) {
            clusterManager?.renderer = renderer ?: return@SideEffect
        }
    }

    if (clusterManager != null) {
        Clustering(
            items = items,
            clusterManager = clusterManager,
        )
    }
}


@Composable
fun ItemContent(
    viewModel: CoffeeShopsViewModel,
    resourceId: Int, text: String, modifier: Modifier,
    onClick: () -> Unit
) {

    val clickFacility by viewModel.clickFacility.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .background(Color.Transparent)
        .clip(CircleShape)
        .border(
            width = 3.dp,
            color = White,
            shape = CircleShape
        )
        .clickable {
            onClick()
            Log.d("NursultanClick", "NursultanClick")
        }
    ) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = text,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun ClusteringContent(
    color: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier,
        shape = CircleShape,
        color = color,
        contentColor = Color.White,
        border = BorderStroke(3.dp, Color.White)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}