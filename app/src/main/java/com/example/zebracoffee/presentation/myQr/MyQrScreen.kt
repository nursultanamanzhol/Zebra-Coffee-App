package com.example.zebracoffee.presentation.myQr

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.ui.theme.FillColor4Light
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import kotlinx.coroutines.delay

@Composable
fun MyQrScreen(
    viewModel: MyQrViewModel,
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.getUserQr()
    }
    val myQrState by viewModel.userQr.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        QrTopBarBlock { navController.popBackStack() }
        Spacer(modifier = Modifier.height(20.dp))
        QrCardBlock(item = myQrState)
    }
}


@Composable
fun QrTopBarBlock(
    navigateBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = stringResource(id = R.string.myQr).uppercase(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontFamily = Constant.fontBold
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary, CircleShape)
                .padding(12.dp)
                .clickable { navigateBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_x),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun QrCardBlock(
    item: Resource<QrResponseDto>,
) {
    var showShimmer by remember { mutableStateOf(false) }

    LaunchedEffect(item) {
        when (item) {
            is Resource.Loading -> {
                showShimmer = true
            }
            else -> {
                delay(1500) // Держим ShimmerBox еще 1.5 секунды после загрузки данных
                showShimmer = false
            }
        }
    }
    if (showShimmer) {
        ShimmerBox(height = 341.dp)
        Spacer(modifier = Modifier.height(16.dp))
        ShimmerBox(height = 95.dp)
    } else {
        when (item) {
            is Resource.Success -> {
                SuccessBlock(item = item)
            }
            is Resource.Failure -> {
                Text(
                    text = "Error: ${item.message}",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
            else -> {} // Обработка других случаев, если необходимо
        }
    }
}

@Composable
fun SuccessBlock(item: Resource.Success<QrResponseDto>) {
    QrCodeImageBlock(item = item)
    Spacer(modifier = Modifier.height(16.dp))
    UserQrCodeBlock(item = item)
}

@Composable
fun QrCodeImageBlock(item: Resource.Success<QrResponseDto>) {
    ElevatedCard(
        modifier = Modifier
            .height(341.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            focusedElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubcomposeAsyncImage(
                    model = "${imageBaseUrl}${item.data?.qr}",
                    modifier = Modifier
                        .size(240.dp)
                        .background(Color.Transparent),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    loading = {
                        CircularProgressBox()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DashedLine()
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.myQr_show_text),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Constant.fontRegular
                )
            }
        }
    }
}

@Composable
fun UserQrCodeBlock(item: Resource.Success<QrResponseDto>) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            focusedElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val originalText = item.data?.code.toString()
            val formattedText = "${originalText.substring(0, 3)}-${originalText.substring(3)}"
            Text(
                text = formattedText,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 28.sp,
                fontFamily = Constant.fontBold
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.myQr_uniueq_text),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = Constant.fontRegular
            )
        }
    }
}
@Composable
fun DashedLine() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = FillColor4Light,
            start = Offset(0f, 0f),
            end = Offset(size.width, 10f),
            pathEffect = pathEffect
        )
    }
}