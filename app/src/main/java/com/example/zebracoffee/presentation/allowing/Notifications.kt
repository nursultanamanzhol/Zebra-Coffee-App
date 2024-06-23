package com.example.zebracoffee.presentation.allowing

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CustomButtonPrimary
import com.example.zebracoffee.common.TextTemplate24
import com.example.zebracoffee.common.TextTemplateUniversal
import com.example.zebracoffee.utils.Constant
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationsQuery(navigateToMainScreen: () -> Unit) {

    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    if (Build.VERSION.SDK_INT >= 33) {
        LaunchedEffect(permissionState.status) {
            if (permissionState.status.isGranted || permissionState.status.shouldShowRationale) {
                navigateToMainScreen()
            }
        }
    } else {

    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.End
        )
        {
            Text(
                modifier = Modifier
                    .clickable() {
                        navigateToMainScreen()
                    },
                text = stringResource(id = R.string.skip).uppercase(),
                color = colorResource(id = R.color.fill_colors_skip_btn),
                fontFamily = Constant.fontRegular,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.speaker1),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextTemplate24(
                    R.string.notification_text,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextTemplateUniversal(
                    text = stringResource(id = R.string.notificationDesc), R.color.black,
                    font = 16, textAlign = TextAlign.Center, modifier = Modifier
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .height(64.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CustomButtonPrimary(
                    text = stringResource(id = R.string.BtnOnNotification),
                    onClick = {
                        if (Build.VERSION.SDK_INT >= 33) {
                            permissionState.launchPermissionRequest()
                        } else {
                            permissionState.launchPermissionRequest()
                            if (permissionState.status.isGranted || permissionState.status.shouldShowRationale) {
                                navigateToMainScreen()
                            }
                        }

                    }
                )
            }
        }
    }
}
