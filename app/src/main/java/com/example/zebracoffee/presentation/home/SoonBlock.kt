package com.example.zebracoffee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.ui.theme.TextColor3
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.utils.Constant


@Composable
fun SoonBlock(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(TextColor3, RoundedCornerShape(16.dp))
            .width(55.dp)
            .height(18.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "",
                tint = Color.White
            )
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = stringResource(id = R.string.soon),
                color = White,
                fontSize = 10.sp,
                fontFamily = Constant.fontRegular
            )
        }
    }
}
