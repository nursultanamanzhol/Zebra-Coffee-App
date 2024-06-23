package com.example.zebracoffee.presentation.home.news

import android.content.Intent
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CustomButtonPrimary
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.presentation.home.HomeViewModel
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailsScreen(
    id: String,
    viewModel: HomeViewModel,
    navController: NavHostController,
) {
    val newsById by viewModel.newsById.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (newsById) {
            is Resource.Loading -> {

            }

            is Resource.Success -> {
                val newsData = (newsById as Resource.Success<News>).data
                newsData?.let { SuccessState(item = it, navController = navController) }
            }

            is Resource.Failure -> {}
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getNewsById(id.toInt())
        Log.d("NewsDetailsScreen", id.toString())
    }
}

@Composable
fun LoadingState() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier
                    .width(44.dp),
                color = Color.Black,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SuccessState(
    item: News,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f)
                .verticalScroll(state = scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.TopEnd,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    model = "${imageBaseUrl}${item.banner_image}",
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(35.dp),
                                color = ColorPrimary
                            )
                        }
                    },
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .clip(CircleShape)
                        .background(
                            White.copy(alpha = 0.4f),
                            CircleShape
                        )
                        .size(44.dp)
                        .padding(12.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(13.dp),
                        painter = painterResource(id = R.drawable.ic_close_x),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp)
            ) {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
                val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val date = inputFormat.parse(item.created_at)
                val clockTime = outputFormat.format(date)
                val dateTime = getDateWihtMonth(dateTime = item.created_at.dropLast(11))
                val textCustom = Html.fromHtml(item.text).toString()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "$dateTime, $clockTime",
                    color = TextColor2Light,
                    fontSize = 14.sp,
                    fontFamily = Constant.fontRegular
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = item.banner_title,
                    fontSize = 28.sp,
                    fontFamily = Constant.fontBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = item.subtitle,
                    fontSize = 16.sp,
                    fontFamily = Constant.fontRegular,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = textCustom,
                    fontSize = 16.sp,
                    fontFamily = Constant.fontRegular,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row (modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ){
            CustomButtonPrimary(
                text = "Перейти на страницу кофейни",
                onClick = {
                    val uri =
                        "https://www.instagram.com/zebracoffee.atyrau?igsh=MXNzZnozYjFibWh3ZA=="
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = android.net.Uri.parse(uri)
                    context.startActivity(intent)
                })
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun getDateWihtMonth(dateTime: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateTime1 = LocalDateTime.parse(dateTime, formatter)
    val localDate = dateTime1.toLocalDate()
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    val formattedDate = when {
        localDate == today -> stringResource(id = R.string.common_today)
        localDate == yesterday -> "${stringResource(id = R.string.common_yesterday)}, ${
            localDate.format(
                DateTimeFormatter.ofPattern("d MMMM").withLocale(Locale.forLanguageTag("ru"))
            )
        }"

        else -> localDate.format(
            DateTimeFormatter.ofPattern("d MMMM").withLocale(Locale.forLanguageTag("ru"))
        )
    }.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    val fullFormattedDate = when {
        localDate == today || localDate == yesterday -> formattedDate
        else -> formattedDate
    }

    val date = sdf.parse(dateTime)
    val calendar = Calendar.getInstance()
    calendar.time = date ?: Date()

    return fullFormattedDate
}