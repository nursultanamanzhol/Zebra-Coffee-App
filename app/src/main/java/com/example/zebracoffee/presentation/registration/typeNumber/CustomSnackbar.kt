package com.example.zebracoffee.presentation.registration.typeNumber

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(
    @DrawableRes drawableRes: Int,
    message: String,
    isRtl: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.background,
    modifier: Modifier = Modifier
) {
    Snackbar(
        containerColor = containerColor,
        modifier = Modifier
            .padding(8.dp)
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = drawableRes),
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    message,
                    modifier = Modifier.padding(start = 5.dp),
                    color = Color.Black
                )
            }
        }
    }
}