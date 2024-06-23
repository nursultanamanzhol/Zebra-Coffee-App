package com.example.zebracoffee.presentation.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import com.example.zebracoffee.R
import com.example.zebracoffee.utils.Constant

@Composable
fun TitleBlock(
    text: String,
    navController: NavHostController,
    onIconClick: () -> Unit = {navController.popBackStack()}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable { onIconClick() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontFamily = Constant.fontBold
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.Transparent)
        ){

        }
    }
}