package com.example.zebracoffee.presentation.allowing

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CustomButtonPrimary
import com.example.zebracoffee.common.TextTemplate14
import com.example.zebracoffee.common.TextTemplate24

@Composable
fun HardUpdatingPage(/*onButtonClick: () -> Unit*/) {
    val allowClick by remember { mutableStateOf(true) }

    val activity = (LocalContext.current as? Activity)

    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(allowClick) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
    }
    DisposableEffect(Unit) {
        onBackPressedCallback.isEnabled = true
        onDispose {
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 70.dp, end = 15.dp, start = 15.dp, bottom = 50.dp)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_hard_updating),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .width(160.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTemplate24(R.string.hard_update)

            Spacer(modifier = Modifier.height(10.dp))
            TextTemplate14(R.string.hardUpdateDesc)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .weight(1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButtonPrimary(
                text = stringResource(id = R.string.HardUpdate),
                onClick = { /*onButtonClick()*/ },
            )
        }
    }
}
