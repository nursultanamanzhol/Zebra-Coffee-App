package com.example.zebracoffee.presentation.registration.typeNumber

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsBlack
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.ui.theme.White40
import com.example.zebracoffee.utils.Constant
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TypeNumberScreen(
    navController: NavHostController,
    viewModel: TypeNumberViewModel,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
) {
    SetupSystemBarsBlack()
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    val phoneNumberState by viewModel.phoneNumberState.collectAsStateWithLifecycle()

    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { TypeNumberSnackbar(snackState = snackState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(46.dp))

                TextTitle()

                PhoneField(phoneNumber,
                    mask = "+7 (000) 000 00 00",
                    maskNumber = '0',
                    onPhoneChanged = { phoneNumber = it })

                if (phoneNumberState is Resource.Failure) {
                    coroutineScope.launch {
                        snackState.showSnackbar("CustomSnackbar")
                    }
                }

                if (phoneNumberState is Resource.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(56.dp),
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .imePadding()
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ColoredTextWithClickableLink(
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(end = 10.dp),
                        onPrivacyPolicyClick = {
                            onPrivacyPolicyClick()
                        },
                        onTermsOfServiceClick = {
                            onTermsOfServiceClick()
                        }
                    )

                    Button(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(92.dp)
                            .height(48.dp)
                            .background(
                                if (phoneNumber.length == 10) Color.White else White40,
                                RoundedCornerShape(56.dp)
                            ),
                        enabled = phoneNumber.length == 10,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (phoneNumber.length == 10) Color.Black else Color.White
                        ),
                        onClick = {
                            navController.navigate(route = "${MainDestinations.EnterCodeScreen_route}/$phoneNumber")
                            viewModel.getPhoneNumber(phoneNumber = "7$phoneNumber")
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_next).uppercase(),
                            fontSize = 12.sp,
                            fontFamily = Constant.fontBold,
                            color = if (phoneNumber.length == 10) Color.Black else Color.White
                        )
                    }
                }
            }
            if (phoneNumberState is Resource.Success) {
                viewModel.resetState()
            }
        }
    )
}

@Composable
fun TextTitle() {
    Text(
        modifier = Modifier.padding(top = 35.dp),
        text = stringResource(id = R.string.write_phone_number).uppercase(),
        color = Color.White,
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        lineHeight = 36.sp,
        fontFamily = Constant.fontBold
    )
}

@Composable
fun TypeNumberSnackbar(snackState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackState,
    ) { snackbarData ->
        CustomSnackBar(
            drawableRes = R.drawable.ic_close,
            message = stringResource(id = R.string.error_message_code),
            isRtl = false,
            containerColor = MaterialTheme.colorScheme.background,
        )
    }
}
