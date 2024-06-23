package com.example.zebracoffee.presentation.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.ui.theme.FillColor3
import com.example.zebracoffee.utils.Constant

@Composable
fun ProfileBlockItem(
    iconId: Int,
    text: String,
    padding: Dp = 12.dp,
    onClick: () -> Unit,
    theme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CircledIconItem(iconId = iconId, theme)
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(8.dp),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "null",
                tint = FillColor3
            )
        }
    }
}

@Composable
fun CircledIconItem(
    iconId: Int,
    theme: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                if (theme) colorResource(id = R.color.fill_colors_4_dark) else colorResource(
                    id = R.color.fill_colors_4
                ), CircleShape
            )
            .size(44.dp)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = if (theme) Color.White else Color.Black
        )
    }
}