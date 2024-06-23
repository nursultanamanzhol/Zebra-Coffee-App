package com.example.zebracoffee.presentation.registration.welcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.domain.entity.User
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.ui.theme.White40
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserCard(
    cardItem: LoyaltyCardResponseDto? = null,
    userItem: User? = null,
    onQrClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable { onCardClick() },
            content = {
                GlideImage(
                    model = "${imageBaseUrl}${cardItem?.image}",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
                /*SubcomposeAsyncImage(
                    model = "${imageBaseUrl}${cardItem?.image}",
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = ""
                )*/
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    TopBlockUserCard(cardItem)
                    MoreBlockUserCard()
                    CenterBlockUserCard()
                    BottomBlockUserCard(
                        userItem,
                        onQrClick = { onQrClick() }
                    )
                }
            }
        )
    }
}


@Composable
fun TopBlockUserCard(
    cardItem: LoyaltyCardResponseDto? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.card_of_cofeeMan).uppercase(),
            color = White,
            fontFamily = Constant.fontBold,
            fontSize = 22.sp
        )
        Box(
            modifier = Modifier
                .width(55.dp)
                .height(32.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "-${cardItem?.discount_percentage}%",
                fontSize = 18.sp,
                fontFamily = Constant.fontBold,
                textAlign = TextAlign.Center,
                color = ColorPrimary,
            )
        }
    }
}


@Composable
fun MoreBlockUserCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 5.dp),
            painter = painterResource(id = R.drawable.ic_info_circle_stroke),
            contentDescription = "",
            tint = White
        )
        Text(
            text = stringResource(id = R.string.how_take_more),
            color = White,
            fontSize = 12.sp,
            fontFamily = Constant.fontRegular,
        )
    }
}

@Composable
fun CenterBlockUserCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(45.dp),
            painter = painterResource(id = R.drawable.ic_zebra_symbol),
            contentDescription = "",
            colorFilter = ColorFilter.tint(White)
        )
    }
}

@Composable
fun BottomBlockUserCard(
    userItem: User? = null,
    onQrClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = userItem?.name?.uppercase() ?: "ALINA",
            fontSize = 22.sp,
            color = Color.White,
            fontFamily = Constant.fontBold
        )
        Box() {}
        Box(
            modifier = Modifier
                .width(78.dp)
                .height(32.dp)
                .background(Color.Transparent, RoundedCornerShape(32.dp))
                .border(2.dp, White40, RoundedCornerShape(32.dp))
                .padding(8.dp)
                .clickable { onQrClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Moй QR",
                fontSize = 12.sp,
                fontFamily = Constant.fontRegular,
                color = Color.White,
            )
        }
    }
}
