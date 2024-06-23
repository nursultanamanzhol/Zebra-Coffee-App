package com.example.zebracoffee.presentation.profileScreen.aboutUs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.utils.Constant

@Composable
fun AboutItem(
    iconId: Int,
    text: String,
    onClick:()-> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = Modifier.clickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint =  MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular
        )
    }
}