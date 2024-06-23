package com.example.zebracoffee.presentation.coffeeShops

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.City
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Country
import com.example.zebracoffee.presentation.coffeeShops.map.MapUiState
import com.example.zebracoffee.presentation.myOrders.EmptyBlock
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.ui.theme.BlurBackground
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeShopsScreen(
    navigateToCoffeeDetails: (Int) -> Unit,
    navigateToLocationQuery: () -> Unit,
    viewModel: CoffeeShopsViewModel,
) {
    val context = LocalContext.current
    val countryList by viewModel.countryList.collectAsState()
    val shopList by viewModel.coffeeShopCityList.collectAsState()
    val isLoadingShops by viewModel.isLoadingShops.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userImage by viewModel.userImage.collectAsStateWithLifecycle()

    val currentLocation = viewModel.currentLocation
    val fineLocationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    var isCoffeeShopsOpened by remember { mutableStateOf(false) }
    val isButtonPressed by remember { mutableStateOf(false) }
    val sharedPreferencesLocation =
        context.getSharedPreferences("isCoffeeShopsScreen", Context.MODE_PRIVATE)

    val cityId = viewModel.getCityId()
    //shadow
    val theme by viewModel.theme.collectAsState()
    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }
    val colorShadow: Color = if (darkTheme) {
        Color.White
    } else if (!darkTheme) {
        Color.Transparent
    } else {
        Color.Transparent
    }

    isCoffeeShopsOpened = sharedPreferencesLocation.getBoolean("isCoffeeShopsScreen", false)

//    viewModel.getShopInitializedCountry()
    LaunchedEffect(shopList) {
//        viewModel.getCountryList()
        viewModel.getShopInitializedCountry()
    }
    LocationUpdater(fineLocationPermissionState, viewModel)
    val isRefreshing by viewModel.isRefreshing.collectAsState() // Состояние обновления из ViewModel
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing) // Состояние Pull-to-Refresh
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.refresh(
                cityId,
                currentLocation!!.latitude,
                currentLocation.longitude
            )
        },
        indicator = { state, trigger ->
            if (state.isRefreshing) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(35.dp),
                        color = ColorPrimary
                    )

                }
            }
        }
    ) {
        if (currentLocation?.latitude != null && currentLocation?.longitude != null &&
            (currentLocation.latitude != 0.0 || currentLocation.longitude != 0.0)
        ) {
            LaunchedEffect(countryList, cityId) {
                if (cityId != -1 && currentLocation?.latitude != null && currentLocation?.longitude != null &&
                    (currentLocation.latitude != 0.0 || currentLocation.longitude != 0.0)
                ) {
                    viewModel.isShopInitialized(
                        cityId,
                        currentLocation!!.latitude,
                        currentLocation.longitude
                    )
                } else {
                    viewModel.getCurrentLocation()
                }
            }

        } else if ((currentLocation?.latitude == null && currentLocation?.longitude == null) ||
            (currentLocation?.latitude == 0.0 && currentLocation?.longitude == 0.0)
        ) {
            viewModel.getCurrentLocation()
        } else {
            viewModel.getCurrentLocation()
        }

        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        var titleTextState by remember { mutableStateOf(viewModel.chooseTopBarTitle(context)) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                    TopBarBlockCoffeeShops(
                        onClick = {
                            showBottomSheet = !showBottomSheet
                        },
                        viewModel = viewModel,
                        titleText = titleTextState,
                        imageUrl = userImage
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                },
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (showBottomSheet) {
                            ModalBottomSheet(
                                modifier = Modifier
                                    .background(Color.Transparent),
                                onDismissRequest = {
                                    showBottomSheet = false
                                },
                                sheetState = sheetState,
                                containerColor = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                dragHandle = {
                                    BottomSheetContent(
                                        onClick = {
                                            showBottomSheet = false
                                        },
                                        countryList = countryList,
                                        viewModel = viewModel,
                                        onCitySelected = { selectedCity ->
                                            titleTextState = selectedCity.name.uppercase()
                                        }
                                    )
                                }
                            ) {

                            }
                        }

                        if (!uiState) {
                            //list of coffee
                            viewModel.getCurrentLocation()
                            if (cityId != -1) {

                                if (!isCoffeeShopsOpened && !isButtonPressed) {
                                    navigateToLocationQuery()
                                    isCoffeeShopsOpened = true
                                    with(sharedPreferencesLocation.edit()) {
                                        putBoolean("isCoffeeShopsScreen", isCoffeeShopsOpened)
                                        apply()
                                    }
                                }

                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(55.dp))
                                    LazyColumn(
                                        modifier = Modifier
                                    ) {
                                        items(shopList) {
                                            Box(
                                                modifier = Modifier
                                                    .shadow(
                                                        20.dp,
                                                        shape = RoundedCornerShape(15.dp),
                                                        ambientColor = colorShadow,
                                                        spotColor = BlurBackground
                                                    )
                                                    .clipToBounds()
                                            ) {
                                                CoffeeListItemScreen(
                                                    item = it,
                                                    viewModel = viewModel,
                                                    viewModel.getWorksTime(
                                                        openFrom = it.open_from,
                                                        openUntil = it.open_until
                                                    ),
                                                    onClick = {
                                                        navigateToCoffeeDetails(it.id)
                                                    },
                                                    isLoading = isLoadingShops,
                                                    fineLocationPermissionState
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 30.dp)
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    EmptyBlock(
                                        navigation = {
                                            showBottomSheet = !showBottomSheet

                                        },
                                        textTitle = R.string.empty_screen_coffee_shop_screen,
                                        textButton = R.string.choose_city,
                                        icon = R.drawable.ic_empty_page,
                                    )
                                }
                            }

                        } else if (uiState) {
                            Column {
                                Spacer(modifier = Modifier.height(60.dp))
                                MapUiState(
                                    modifier = Modifier.fillMaxSize(),
                                    viewModelData = viewModel,
                                    navigateToCoffeeDetails = navigateToCoffeeDetails,

                                    )
                                viewModel.getCurrentLocation()
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CoffeeListItemScreen(
    item: CoffeeDetails,
    viewModel: CoffeeShopsViewModel,
    worksTime: Pair<String, Painter>,
    onClick: () -> Unit,
    isLoading: Boolean,
    fineLocationPermissionState: PermissionState,
) {

    val distanceInBackEnd = if (fineLocationPermissionState.status.isGranted) {
        item.distance.toString() + " km"
    } else {
        ""
    }
    if (isLoading) {
        ShimmerBox(
            height = 186.dp,
            width = 300.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 16.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
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
                                CircularProgressBox()
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

                    Column(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {

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
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            if (fineLocationPermissionState.status.isGranted) {
                                Text(
                                    text = "・",
                                    color = colorResource(id = R.color.notifiDateTime),
                                    fontSize = 14.sp,
                                    fontWeight = W500
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            if (fineLocationPermissionState.status.isGranted) {
                                Icon(
                                    painter = painterResource(id = R.drawable.location_v2),
                                    contentDescription = "",
                                    tint = TextColor2Light
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                text = distanceInBackEnd,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    countryList: List<Country>,
    onClick: () -> Unit,
    viewModel: CoffeeShopsViewModel,
    onCitySelected: (City) -> Unit,
) {
    val savedCity = viewModel.getCitySharedPref()
    viewModel.getCurrentLocation()
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(id = R.string.coffee_city).uppercase(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                fontFamily = Constant.fontBold
            )
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSecondary, CircleShape)
                    .clickable {
                        onClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_x),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (countryList.isNotEmpty()) {
            LazyColumn {
                items(countryList) { country ->

                    Text(
                        text = country.name,
                        style = TextStyle(fontSize = 14.sp, color = TextColor2Light),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    country.cities.forEach { city ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.getCoffeeShopCity(
                                        city.id,
                                        city.latitude,
                                        city.longitude
                                    )
                                    onCitySelected(city)
                                    viewModel.saveCitySharedPref(city)
                                    onClick()
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .height(36.dp),
                                text = city.name,
                                style = TextStyle(fontSize = 14.sp, color = Color.Black),
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (savedCity.id == city.id) {
                                Icon(
                                    modifier = Modifier.align(Alignment.Top),
                                    painter = painterResource(id = R.drawable.ic_check_24),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TopBarBlockCoffeeShops(
    onClick: () -> Unit,
    viewModel: CoffeeShopsViewModel,
    titleText: String,
    imageUrl: String,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            model = "${imageBaseUrl}${imageUrl}",
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.coffee_top_bar_title),
                color = TextColor2Light,
                fontSize = 14.sp,
                fontFamily = Constant.fontRegular
            )
            Row(
                modifier = Modifier.clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleText,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = Constant.fontBold
                )
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.keyboard_arrow_right_24),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    viewModel.changedUiState()
                }
                .background(MaterialTheme.colorScheme.onSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (uiState) {
                Icon(
                    painter = painterResource(id = R.drawable.map_25),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationUpdater(
    fineLocationPermissionState: PermissionState,
    viewModel: CoffeeShopsViewModel,
) {
    val currentLocation = viewModel.currentLocation
    if (currentLocation?.latitude == null &&
        currentLocation?.longitude == null ||
        currentLocation.latitude == 0.0 &&
        currentLocation.longitude == 0.0
    ) {
        viewModel.getCurrentLocation()
    } else {
        LaunchedEffect(Unit) {
            val intervalFlow = flow {
                while (true) {
                    emit(Unit)
                    delay(20000)
                }
            }

            intervalFlow.collect {
                if (fineLocationPermissionState.status.isGranted) {
                    viewModel.getCurrentLocation()
                }
            }
        }
    }
}
