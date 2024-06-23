package com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.TextTemplateUniversal
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.ui.theme.TextColor2Dark
import com.example.zebracoffee.ui.theme.White56
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.fontRegular
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSwiper(
    navController: NavController,
    modifier: Modifier = Modifier,
    images: List<String>,
    content: () -> Unit,
    bottomSearch: () -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = { images.size })


    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { index ->

            val imageUrl = if (images.isEmpty()) {
                "https://batyrmall.kz/wp-content/uploads/2022/06/IMG_20220609_112626-1-1024x473.jpg"
            } else {
                "${imageBaseUrl}${images[index]}"
            }

            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                loading = {
                    CircularProgressBox()
                },
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        Row(
            modifier = Modifier
                .height(44.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .aspectRatio(1f)
                    .clickable {
                        navController.popBackStack()
                    }
                    .background(
                        color = White56.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(38.dp)
                    ),
                contentAlignment = Alignment.TopStart,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(12.dp)
                )
            }
            Row {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .aspectRatio(1f)
                        .clickable {
                            bottomSearch.invoke()
                        }
                        .background(
                            color = White56.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(38.dp)
                        ),
                    contentAlignment = Alignment.TopEnd,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_web),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(12.dp),
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .aspectRatio(1f)
                        .clickable {
                            content.invoke()
                        }
                        .background(
                            color = White56.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(38.dp)
                        ),
                    contentAlignment = Alignment.TopEnd,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(12.dp),
                    )
                }
            }
        }
        if (pagerState.pageCount > 1) {
            SwiperPagination(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SwiperPagination(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
                    .background(
                        color = if (index == currentPage) Color.White else Color.White.copy(
                            alpha = 0.4f
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(4.dp)
            )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomRowInfo(
    modified: Modifier = Modifier,
    icon: Int,
    text1: String,
    nullOrText: String?,
    text2: String,
    theme: Boolean,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = icon,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(
                        width = 8.dp,
                        color = Color.Transparent,
                        shape = CircleShape
                    ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Spacer(
                modifier = Modifier
                    .width(15.dp)
            )
            if (nullOrText == null) {
                TextTemplateUniversal(
                    text = text1,
                    color = R.color.black,
                    font = 14,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                )
            } else {
                Column {
                    TextTemplateUniversal(
                        text = text1,
                        font = 14,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )

                    Text(
                        text = nullOrText,
                        color = TextColor2Dark,
                        fontSize = 14.sp,
                        fontFamily = fontRegular,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Start,

                        )
                }
            }
        }

        Button(
            modifier = Modifier
                .height(32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (theme) colorResource(
                    id = R.color.notifiBackIconDark
                ) else colorResource(id = R.color.notifiBackIcon)
            ),
            onClick = { onClick.invoke() },
        ) {
            Text(
                text = text2.uppercase(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                color = colorResource(id = R.color.notifiTextColor),
                fontFamily = Constant.fontRegular
            )
        }
    }
}


@Composable
fun BottomSheetWorkTime(
    item: CoffeeDetails,
    theme: Boolean,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),

        ) {
        Row(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextTemplateUniversal(
                text = stringResource(id = R.string.Work_Time_Bottom).uppercase(),
                color = R.color.black,
                font = 14,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .aspectRatio(1f)
                    .clickable {
                        onButtonClick()
                    }
                    .background(
                        color = if (theme) colorResource(id = R.color.fill_colors_4_dark) else colorResource(
                            id = R.color.fill_colors_4
                        ),
                        shape = RoundedCornerShape(38.dp)
                    ),
                contentAlignment = Alignment.Center,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_secondary_btm_sheet),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .height(44.dp)
                        .width(44.dp)
                        .padding(14.dp),
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
        ) {


            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.monday),
                "${item.schedule.monday_start.dropLast(3)}-${
                    item.schedule.monday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.tuesday),
                "${item.schedule.tuesday_start.dropLast(3)}-${
                    item.schedule.tuesday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.wednesday),
                "${item.schedule.wednesday_start.dropLast(3)}-${
                    item.schedule.wednesday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.thursday),
                "${item.schedule.thursday_start.dropLast(3)}-${
                    item.schedule.thursday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.friday),
                "${item.schedule.friday_start.dropLast(3)}-${
                    item.schedule.friday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.saturday),
                "${item.schedule.saturday_start.dropLast(3)}-${
                    item.schedule.saturday_end.dropLast(3)
                }"
            )
            RowItemWorkTime(
                textDayInWeek = stringResource(id = R.string.sunday),
                "${item.schedule.sunday_start.dropLast(3)}-${
                    item.schedule.sunday_end.dropLast(3)
                }"
            )
        }
    }
}

@Composable
fun RowItemWorkTime(textDayInWeek: String, textWorkTime: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(28.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextTemplateUniversal(
            text = textDayInWeek,
            color = R.color.black,
            font = 14,
            modifier = Modifier,
            textAlign = TextAlign.Start,
        )

        TextTemplateUniversal(
            text = textWorkTime,
            color = R.color.black,
            font = 14,
            modifier = Modifier,
            textAlign = TextAlign.Start,
        )
    }
}

