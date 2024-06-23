package com.example.zebracoffee.presentation.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.common.shimmerEffect
import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.presentation.allowing.ShortUpdate
import com.example.zebracoffee.presentation.home.news.NewsItem
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.presentation.registration.welcomeScreen.UserCard
import com.example.zebracoffee.ui.theme.BlurBackground
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import kotlinx.coroutines.delay

private const val RECOMENDED = "recomended"
private const val MUST_UPDATE = "mustUpdate"
private const val ANDROID = "android"

@Composable
fun HomeScreen(
    navigateToProfile: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToQr: () -> Unit,
    navigateToLoyalty: () -> Unit,
    navigateToHardUpdate: () -> Unit,
    navigateToPermissionNotification: () -> Unit,
    navigateToStoriesDetails: (String) -> Unit,
    navigateToNewsDetails: (Int) -> Unit,
    viewModel: HomeViewModel,
) {
    val loyaltyCardState by viewModel.loyaltyCard.collectAsStateWithLifecycle()
    val userDataState by viewModel.userDataCard.collectAsStateWithLifecycle()

    val storiesList by viewModel.storiesList.collectAsStateWithLifecycle()
    val newsList by viewModel.newsList.collectAsStateWithLifecycle()
    val isLoadingStoriesState by viewModel.isLoadingStories.collectAsStateWithLifecycle()
    val isLoadingNewsState by viewModel.isLoadingNews.collectAsStateWithLifecycle()
    val appVersionState by viewModel.appVersion.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val packageManager = context.packageManager
    val packageName = context.packageName
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    val versionName = packageInfo.versionName

    var isHomeScreenOpened by remember { mutableStateOf(false) }
    val isButtonPressed by remember { mutableStateOf(false) }
    val sharedPreferences = context.getSharedPreferences("isHomeScreenOpened", Context.MODE_PRIVATE)

    val isRefreshing by viewModel.isRefreshing.collectAsState() // Состояние обновления из ViewModel
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing) // Состояние Pull-to-Refresh
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

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh() },
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
        LaunchedEffect(Unit) {
            viewModel.checkAppVersion(ANDROID, versionName)
        }
//        if (getStoriesList)
//        LaunchedEffect(storiesList) {
//            viewModel.getStoriesList()
//        }


        isHomeScreenOpened = sharedPreferences.getBoolean("isHomeScreenOpened", false)
        if (!isHomeScreenOpened && !isButtonPressed) {
            navigateToPermissionNotification()
            isHomeScreenOpened = true
            with(sharedPreferences.edit()) {
                putBoolean("isHomeScreenOpened", isHomeScreenOpened)
                apply()
            }
        }
        if (isLoadingNewsState && isLoadingNewsState && viewModel.visually) {
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
        } else {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(horizontal = 16.dp)
//                    .padding(top = 16.dp)
                ,
                topBar = {
                    TopBarBlock(
                        navigateToProfile = navigateToProfile,
                        navigateToNotifications = navigateToNotifications
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(state = scrollState)
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    Spacer(modifier = Modifier.height(40.dp))
                    ProfileBlock(userDataState)

                    Spacer(modifier = Modifier.height(15.dp))

                    val list = storiesList.filter { it.type == "promotional" }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(list) { item ->
                            StoriesBlockItem(
                                item = item,
                                onclick = { list ->
                                    val jsonString = Gson().toJson(list)
                                    val listUri = Uri.encode(jsonString)
                                    navigateToStoriesDetails(listUri)
                                },
                                isLoading = isLoadingStoriesState,
                                viewModel = viewModel
                            )
                        }
                    }
                    Log.d("home loading", "isLoadingStories: $isLoadingStoriesState")

                    AppVersionCheckBlock(
                        appVersionState = appVersionState,
                        onMustUpdate = { navigateToHardUpdate() }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    UserCardBlock(
                        userItem = userDataState,
                        cardItem = loyaltyCardState,
                        navigateToQr = navigateToQr,
                        navigateToLoyalty = navigateToLoyalty
                    )
                    Spacer(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .height(8.dp)
                    )
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
                        ElevatedBoxFullBlock(viewModel = viewModel)
                    }
                    Spacer(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .height(16.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(newsList) { item ->
                            NewsItem(
                                item = item,
                                isLoading = isLoadingNewsState,
                                onClick = { navigateToNewsDetails(item.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

        }
    }
}

@Composable
fun AppVersionCheckBlock(
    appVersionState: Resource<CheckVersionDto>,
    onMustUpdate: () -> Unit,
) {
    when (appVersionState) {
        is Resource.Success -> {
            val checkVersionDto = (appVersionState).data
            val status = checkVersionDto?.status

            if (status != null) {
                when (status) {
                    RECOMENDED -> {
                        ShortUpdate()
                    }

                    MUST_UPDATE -> {
                        onMustUpdate()
                    }

                    else -> {
                    }
                }
            }
        }

        is Resource.Loading -> {
        }

        is Resource.Failure -> {
        }

        else -> Unit
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileBlock(userDataState: Resource<UserDto>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        when (userDataState) {
            is Resource.Loading -> {
                ShimmerBox(
                    height = 100.dp
                )
            }

            is Resource.Success -> {
                val userData = userDataState.data!!
                GlideImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    /*loading = ,*/
                    model = "${imageBaseUrl}${userData.avatar_image}",
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    InfoBlock(
                        text = "ZEBRA COINS",
                        textColor = TextColor2Light,
                        fontSize = 14.sp,
                        iconResId = R.drawable.ic_info_circle_stroke,
                        fontFamily = Constant.fontRegular,
                        iconSize = 20.dp,
                        tintColor = TextColor2Light
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InfoBlock(
                            text = userData.zc_balance.toString(),
                            textColor = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            iconSize = 35.dp,
                            iconResId = R.drawable.ic_zc,
                            fontFamily = Constant.fontBold,
                            tintColor = MaterialTheme.colorScheme.onSurface
                        )
                        SoonBlock(
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }

            is Resource.Failure -> {
                Text(
                    text = "Введутся технические работы\n" +
                            "Попробуйте по позже",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

            else -> {}
        }
    }
}

@Composable
fun InfoBlock(
    text: String,
    textColor: Color,
    fontSize: TextUnit = 14.sp,
    iconResId: Int,
    iconSize: Dp,
    fontFamily: FontFamily,
    tintColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
            fontFamily = fontFamily
        )
        Icon(
            modifier = Modifier
                .size(iconSize)
                .padding(start = 5.dp),
            painter = painterResource(id = iconResId),
            contentDescription = "",
            tint = tintColor,
        )
    }
}


@Composable
fun UserCardBlock(
    userItem: Resource<UserDto>,
    cardItem: Resource<LoyaltyCardResponseDto>,
    navigateToQr: () -> Unit,
    navigateToLoyalty: () -> Unit,
) {
    when {
        userItem is Resource.Success && cardItem is Resource.Success -> {
            val loyaltyCardData = (cardItem).data
            val userCardData = (userItem).data
            UserCard(
                cardItem = loyaltyCardData,
                userItem = userCardData,
                onCardClick = { navigateToLoyalty() },
                onQrClick = { navigateToQr() }
            )
        }

        userItem is Resource.Loading || cardItem is Resource.Loading -> {
            ShimmerBox(
                height = 240.dp
            )
        }

        userItem is Resource.Failure || cardItem is Resource.Failure -> {
        }

        else -> {
        }
    }
}

@Composable
fun ElevatedBoxFullBlock(viewModel: HomeViewModel) {
    var showShimmer by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (!viewModel.isInitialized) {
            delay(1500)
            showShimmer = false
        }
    }

    if (showShimmer && !viewModel.isInitialized) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.background)
                .shimmerEffect()
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp, bottom = 2.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            ElevatedCardBlock()
        }
    }
}


@Composable
fun TopBarBlock(
    navigateToProfile: () -> Unit,
    navigateToNotifications: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                painter = painterResource(id = R.drawable.zebra_coffee_home_top),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Row {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { navigateToNotifications() },
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    modifier = Modifier
                        .clickable { navigateToProfile() },
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ElevatedCardBlock() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.image_big_coin),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    text = stringResource(id = R.string.awards),
                    fontFamily = Constant.fontRegular
                )
                SoonBlock(
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.size(80.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(4) {
                        CoffeeIconItem()
                    }
                }
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.awards),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Constant.fontRegular
                )
                SoonBlock(
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}
