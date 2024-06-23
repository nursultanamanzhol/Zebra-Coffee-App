package com.example.zebracoffee.presentation.loyalProgram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.domain.entity.User
import com.example.zebracoffee.utils.Constant

@Composable
fun LoyalCard(
    cardItem: LoyaltyCardResponseDto? = null,
    userItem: User? = null,
) {
    val baseUrl = Constant.BASE_URL
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
        ) {
            SubcomposeAsyncImage(
                model = "$baseUrl${cardItem?.image}",
                modifier = Modifier
                    .fillMaxSize(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                loading = {
                    ShimmerBox()
                }
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .width(52.dp)
                            .height(28.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "-${cardItem?.discount_percentage}%",
                            fontSize = 16.sp,
                            lineHeight = 1.25.em,
                            fontFamily = Constant.fontBold,
                            textAlign = TextAlign.Center,
                            color = Color(0xff4eb9c9)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_loyalty_card),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = userItem?.name?.uppercase() ?: "",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontFamily = Constant.fontBold
                    )
                }
            }
        }
    }
}
