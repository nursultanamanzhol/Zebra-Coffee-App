package com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.ButtonMenuBasket
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.CustomButtonMenuNav
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.menuDto.Item
import com.example.zebracoffee.data.modelDto.menuDto.MenuDtoItem
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.presentation.registration.personalInfo.SearchTextField
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import java.util.Locale
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeShopDetailsScreen(
    viewModel: CoffeeShopDetailsViewModel,
    navController: NavHostController,
    id: String?,
) {
    var isSheetOpenWorkTime by rememberSaveable {
        mutableStateOf(false)
    }
    val menuData by viewModel.menuStore.collectAsStateWithLifecycle()
    val searchProduct by viewModel.searchProduct.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val coffeeShop by viewModel.coffeeShop.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val context = LocalContext.current

    val theme by viewModel.theme.collectAsState()

    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getCoffeeShop(id.toInt())
            viewModel.getMenuStore(id.toInt())
        }
    }

    when (coffeeShop) {
        is Resource.Success -> {
            val coffeeData = (coffeeShop as Resource.Success).data
            coffeeData?.let {
                DetailsSuccessState(
                    viewModel = viewModel,
                    it,
                    navController,
                    id,
                    context = context,
                    onBottomSheetOpen = { isSheetOpenWorkTime = true },
                    menuData = menuData,
                    theme = darkTheme,
                    searchProduct = searchProduct,
                    searchHistory = searchHistory

                )
            }
        }

        is Resource.Loading -> {
            CircularProgressBox(
                indicatorColor = MaterialTheme.colorScheme.onSurface,
                indicatorSize = 44.dp
            )
        }

        is Resource.Failure -> {

            Log.d("CoffeeShopDetailsScreen", "error")
        }

        else -> Unit
    }

    if (isSheetOpenWorkTime) {
        ModalBottomSheet(
            tonalElevation = 0.dp,
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpenWorkTime = false
            },
            containerColor = MaterialTheme.colorScheme.background,
//            dragHandle = { BottomSheetDefaults.DragHandle() },

        ) {
            if (coffeeShop is Resource.Success) {
                val data = (coffeeShop as Resource.Success).data
                data?.let {
                    BottomSheetWorkTime(item = it, theme = darkTheme) {
                        isSheetOpenWorkTime = false
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsSuccessState(
    viewModel: CoffeeShopDetailsViewModel,
    itemCoffe: CoffeeDetails,
    navController: NavHostController,
    id: String?,
    context: Context,
    theme: Boolean,
    onBottomSheetOpen: () -> Unit,
    menuData: List<MenuDtoItem>?,
    searchProduct: List<MenuDtoItem>?,
    searchHistory: List<MenuDtoItem>?,
) {
    val images = mutableListOf<String>()
//    for (shopImage in itemCoffe.shop_images) {


    for (shopImage in itemCoffe.shop_images) {
        shopImage.image.let { images.add(it) }
    }

    val dialLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}
    val menuRowNav by viewModel.menuStore.collectAsStateWithLifecycle()
    val originalText = itemCoffe.schedule.short_description
    val daysOfWeek = originalText.substringBefore(",").split("-").joinToString(" - ") {
        it.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
    val timeRange = originalText.substringAfter(",").replace(":00:00", ":00")
        .replace("-", " - ") // Добавляем пробелы вокруг дефиса
    val modifiedText = "$daysOfWeek, $timeRange"


    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(810.dp),
        ) {
            ImageSwiper(
                navController,
                modifier = Modifier
                    .weight(0.45f),
                images = images,
                content = {
                    if (!id.isNullOrBlank()) {
                        val shareText = itemCoffe.two_gis_link

                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                },
                bottomSearch = {
                    viewModel.setClickSearchTrue()
                }
            )

            BtnLazyRow(
                menuData = menuRowNav,
                modifier = Modifier,
                viewModel = viewModel
            )

            Column(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
                    .weight(0.55f)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {

                CustomRowInfo(
                    modified = Modifier.weight(0.15f),
                    R.drawable.ic_star_rating,
                    stringResource(R.string.coffee_shop_rating, itemCoffe.rating),
                    null,
                    stringResource(id = R.string.All_Reviews),
                    theme = theme
                ) {
                    navController.navigate(route = "${MainDestinations.ReviewScreen_route}/$id")
                }
                CustomRowInfo(
                    modified = Modifier.weight(0.15f),
                    R.drawable.ic_clock_work_time,
                    stringResource(id = R.string.Work_TimeDetails),
                    modifiedText,
                    stringResource(id = R.string.Learn_More),
                    onClick = {
                        onBottomSheetOpen()
                    },
                    theme = theme
                )
                CustomRowInfo(
                    modified = Modifier.weight(0.15f),
                    R.drawable.ic_warning_contacts_info,
                    stringResource(id = R.string.Contacts_DetailsScreen),
                    itemCoffe.contacts,
                    stringResource(id = R.string.CallForContacts),
                    theme = theme
                ) {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${itemCoffe.contacts}")
                    }
                    dialLauncher.launch(dialIntent)

                }
                CoffeeDetailsItemScreen(
                    item = itemCoffe,
                    theme = theme
                )
            }
        }
        if (menuData != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                menuData.forEach { menuItem ->
                    Text(
                        text = menuItem.name.uppercase(),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        color = colorResource(id = R.color.notifiDateTime),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                    )
                    val heightLazyGrid: Int = (ceil(menuItem.items.size / 2.0) * 110).toInt()
                    Log.d(
                        "TestMenu",
                        "${menuItem.name} + ${menuItem.items.size} $heightLazyGrid"
                    )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .height(heightLazyGrid.dp)
                            .fillMaxWidth(),
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                    ) {
                        items(menuItem.items) { item ->
                            MenuCards(
                                menuItem = item,
                                viewModel = viewModel
                            ) // Передаем каждый Item в MenuCards
                        }
                    }
                }
            }
        }
        ButtonMenuBasket(
            text = stringResource(id = R.string.basketBtn),
            color = colorResource(id = R.color.basketBtnColor),
            onClick = { /*TODO*/ },
            textColor = colorResource(id = R.color.notifiTextColor)
        )
        val openBottomSearch by viewModel.openBottomSearch.collectAsStateWithLifecycle()
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        var searchValue by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()
        if (openBottomSearch) {
            ModalBottomSheet(
                modifier = Modifier
//                        .height(650.dp)
                    .weight(0.4f)
                    .background(Color.Transparent),
                onDismissRequest = {
                    viewModel.setClickSearchFalse()
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .height(650.dp)
                            .background(MaterialTheme.colorScheme.background),
                    ) {
                        Row(
                            modifier = Modifier
//                                    .weight(0.2f)
                                .height(80.dp)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(0.82f)
                            ) {
                                SearchTextField(
                                    textHint = stringResource(id = R.string.name_hint),
                                    focusedHint = "",
                                    onValueChanged = { newValue ->
                                        searchValue = newValue
                                        if (newValue == "") {
                                            viewModel.getSearchHistory(id!!.toInt())
                                        } else {
                                            viewModel.getProductInSearch(newValue, id!!.toInt())
                                        }
                                    },
                                    modifier = Modifier
//                                    .weight(0.8f)
                                )
                            }
//                            Spacer(modifier = Modifier.width(30.dp))
                            Row(
                                modifier = Modifier
                                    .weight(0.15f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .height(44.dp)
                                        .width(44.dp)
                                        .aspectRatio(1f)
                                        .clickable {
                                            viewModel.setClickSearchFalse()
                                        }
                                        .background(
                                            color = colorResource(id = R.color.fill_colors_4),
                                            shape = RoundedCornerShape(38.dp)
                                        ),
                                    contentAlignment = Alignment.TopEnd,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_secondary_btm_sheet),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .height(44.dp)
                                            .width(44.dp)
                                            .padding(14.dp)
                                    )
                                }
                            }
                        }


                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .verticalScroll(state = scrollState)
                                .background(MaterialTheme.colorScheme.background),
                        ) {
                            if (searchProduct != null && searchValue != "") {
                                searchProduct.forEach { menuItem ->
                                    Text(
                                        text = menuItem.name.uppercase(),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        color = colorResource(id = R.color.notifiDateTime),
                                        modifier = Modifier
                                            .padding(vertical = 10.dp)
                                            .fillMaxWidth()
                                    )
                                    val heightLazyGrid: Int =
                                        (ceil(menuItem.items.size / 2.0) * 110).toInt()
                                    Log.d(
                                        "TestMenu",
                                        "${menuItem.name} + ${menuItem.items.size} $heightLazyGrid"
                                    )
                                    LazyVerticalGrid(
                                        modifier = Modifier
                                            .background(Color.Transparent)
                                            .height(heightLazyGrid.dp)
                                            .fillMaxWidth(),
                                        columns = GridCells.Fixed(2),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(0.dp),
                                    ) {
                                        items(menuItem.items) { item ->
                                            MenuCards(
                                                menuItem = item,
                                                viewModel = viewModel
                                            ) // Передаем каждый Item в MenuCards
                                        }
                                    }
                                }
                            } else {
                                searchHistory?.forEach { menuItem ->
                                    Text(
                                        text = menuItem.name.uppercase(),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        color = colorResource(id = R.color.notifiDateTime),
                                        modifier = Modifier
                                            .padding(vertical = 10.dp)
                                            .fillMaxWidth()
                                    )
                                    val heightLazyGrid: Int =
                                        (ceil(menuItem.items.size / 2.0) * 110).toInt()
                                    Log.d(
                                        "TestMenu",
                                        "${menuItem.name} + ${menuItem.items.size} $heightLazyGrid"
                                    )
                                    LazyVerticalGrid(
                                        modifier = Modifier
                                            .background(Color.Transparent)
                                            .height(heightLazyGrid.dp)
                                            .fillMaxWidth(),
                                        columns = GridCells.Fixed(2),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(0.dp),
                                    ) {
                                        items(menuItem.items) { item ->
                                            MenuCards(
                                                menuItem = item,
                                                viewModel = viewModel
                                            ) // Передаем каждый Item в MenuCards
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            ) {}
        }
    }
}


@Composable
fun MenuCards(
    menuItem: Item,
    viewModel: CoffeeShopDetailsViewModel
) {
    val imageUrl = if (menuItem.image == null) {
        "https://media.istockphoto.com/id/1316420668/vector/user-icon-human-person-symbol-social-profile-icon-avatar-login-sign-web-user-symbol.jpg?s=612x612&w=0&k=20&c=AhqW2ssX8EeI2IYFm6-ASQ7rfeBWfrFFV4E87SaFhJE="
    } else {
        "${imageBaseUrl}${menuItem.image}"
    }
    val priceActive = if (menuItem.state == "active") {
        "${menuItem.price} ₸"
    } else {
        "Нет в наличии"
    }
    val nameActiveColor: Color = if (menuItem.state == "active") {
        Color.Black
    } else {
        MediumGray2
    }


    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .height(90.dp)
            .clickable { }
            .background(Color.White)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),

            ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
            ) {

                SubcomposeAsyncImage(
                    modifier = Modifier
                        .clip(CircleShape),
                    model = imageUrl,
                    loading = {
                        CircularProgressBox(
                            indicatorColor = Color.Green,
                            indicatorSize = 20.dp
                        )
                    },
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                if (menuItem.inBasketCount != 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd)
                            .background(
                                color = colorResource(id = R.color.notifiTextColor),
                                shape = RoundedCornerShape(38.dp)
                            )
                    ) {
                        Text(
                            text = "${menuItem.inBasketCount}",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = W700,
                            fontFamily = Constant.fontBold,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = menuItem.name ?: "Template",
                    color = nameActiveColor,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                )
                Text(
                    textAlign = TextAlign.Start,
                    text = priceActive,
                    color = colorResource(id = R.color.notifiDateTime),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                if (menuItem.inBasket) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "В корзине",
                        color = colorResource(id = R.color.notifiTextColor),
                        fontSize = 12.sp,
                        fontWeight = W400,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }

}


@Composable
fun BtnLazyRow(
    menuData: List<MenuDtoItem>?,
    modifier: Modifier,
    viewModel: CoffeeShopDetailsViewModel
) {
    val buttonTitles = viewModel.menuStore.value
    val heightRow = 40.dp

    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .height(heightRow),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .height(heightRow)
                    .width(heightRow)
                    .background(Color.Transparent)
                    .clickable {
                        // Обработчик нажатия иконки
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_nav),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        // Проверка на null
        menuData?.let { data ->
            items(data) { menuItem ->
                CustomButtonMenuNav(
                    color = Color.Transparent,
                    textColor = Color.Black,
                    text = menuItem.name,
                    onClick = {
                        // Обработчик нажатия кнопки
                    }
                )
            }
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, shareText)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            context.startActivity(shareIntent)
        }
    }
}

// карта кофемана
@Composable
fun СardСoffeemana() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
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
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                LazyRow(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .height(52.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                ) {

                }
            }
        }
    }
}
