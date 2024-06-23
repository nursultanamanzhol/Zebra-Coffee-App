package com.example.zebracoffee.presentation.loyalProgram

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.common.ShimmerBox


@Composable
fun LoyalCardWelcomeBlock(
    userItem: Resource<UserDto>,
    loyaltyCardState: Resource<LoyaltyCardResponseDto>,
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        if (loyaltyCardState is Resource.Loading || userItem is Resource.Loading) {
            ShimmerBox(
                height = 200.dp,
                width = 280.dp
            )
        }

        if (loyaltyCardState is Resource.Success && userItem is Resource.Success) {
            val loyaltyCardData = (loyaltyCardState).data
            val userCardData = (userItem).data
            if (loyaltyCardData != null && userCardData != null) {
                LoyalCard(
                    cardItem = loyaltyCardData,
                    userItem = userCardData,
                )
            }
        }
    }
}
