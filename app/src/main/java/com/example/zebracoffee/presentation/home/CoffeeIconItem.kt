package com.example.zebracoffee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.ui.theme.FillColor3
import com.example.zebracoffee.ui.theme.FillColor5

@Composable
fun CoffeeIconItem() {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(
                FillColor5,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.coffee_item),
            modifier = Modifier
                .padding(5.dp)
                .size(20.dp),
            contentDescription = "null",
            tint = FillColor3
        )
    }
}
