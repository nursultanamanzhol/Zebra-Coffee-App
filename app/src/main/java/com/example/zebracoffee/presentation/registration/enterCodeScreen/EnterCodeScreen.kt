package com.example.zebracoffee.presentation.registration.enterCodeScreen

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsBlack
import com.example.zebracoffee.common.SnackbarBlock
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.ui.theme.White24
import com.example.zebracoffee.utils.Constant
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun EnterCodeScreen(
    navController: NavHostController,
    phoneNumber: String,
    viewModel: EnterCodeViewModel,
) {
    val context = LocalContext.current
    var otpCode by remember { mutableStateOf("") }
    val formatPhoneNumberSms = formatPhoneNumberSMS(phoneNumber)
    SmsRetriever.getClient(context).startSmsUserConsent("+$formatPhoneNumberSms");


    LaunchedEffect(Unit) {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()

        task.addOnSuccessListener {
            // Успешный запуск ретривера
            Log.d("SMSRetriever", "SMS Retriever starts successfully.")
        }

        task.addOnFailureListener {
            // Ошибка при запуске ретривера
            Log.e("SMSRetriever", "Failed to start SMS Retriever.", it)
        }
    }
    val smsReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
                    val extras = intent.extras
                    val smsMessage = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                    Log.d(
                        "SMSRetriever",
                        "Received SMS: $smsMessage"
                    ) // Логирование полученного SMS

                    val regex = "Код: (\\d{6})".toRegex()
                    val code = regex.find(smsMessage ?: "")?.groupValues?.get(1)

                    if (code != null) {
                        otpCode = code
                        Log.d("SMSRetriever", "Extracted code: $code")
                    } else {
                        Log.d("SMSRetriever", "No valid code found in SMS")
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        context.registerReceiver(smsReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
        onDispose {
            context.unregisterReceiver(smsReceiver)
        }
    }

    OtpTextField(otpText = otpCode, onOtpTextChange = { text, isComplete ->
        otpCode = text
        if (isComplete) {
            viewModel.verifyCode(formatPhoneNumberSms, otpCode.toInt())
        }
    })


    SetupSystemBarsBlack()
    val formattedPhoneNumber = formatPhoneNumber(phoneNumber)
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val verificationCodeState by viewModel.verificationCodeState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = {
            SnackbarBlock(
                snackState = snackState,
                text = "Неверный код, повторите попытку",
                iconId = R.drawable.ic_close
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black)
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Icon(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { navController.popBackStack() },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "icon back",
                    tint = White
                )

                Spacer(modifier = Modifier.height(20.dp))
                if (verificationCodeState is Resource.Failure) {
                    coroutineScope.launch {
                        snackState.showSnackbar("CustomSnackbar", duration = SnackbarDuration.Short)
                    }
                    viewModel.resetState()
                }

                EnterCodeTitle()
                EnterCodeInstructionText(formattedPhoneNumber)


                OtpTextField(
                    otpText = otpCode,
                    onOtpTextChange = { value, _ ->
                        otpCode = value
                    },
                )

                LaunchedEffect(otpCode) {
                    if (otpCode.length == 6) {
                        viewModel.verifyCode("7$phoneNumber", otpCode.toInt())
                        viewModel.resetState()
                    }
                }
                ButtonVerify(
                    viewModel = viewModel,
                    otpValue = otpCode,
                    phoneNumber = phoneNumber,
                    snackState = snackState
                )

                if (verificationCodeState is Resource.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(56.dp)
                            .align(Alignment.CenterHorizontally),
                        color = White
                    )
                }
            }
        }
    )
    LaunchedEffect(verificationCodeState) {
        if (verificationCodeState is Resource.Success) {
            navController.navigate(route = MainDestinations.PersonalInfoScreen_route)
            viewModel.resetState()
        }
    }
}

@Composable
fun ButtonVerify(
    viewModel: EnterCodeViewModel,
    otpValue: String,
    phoneNumber: String?,
    snackState: SnackbarHostState,
) {
    var isTimerRunning by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableStateOf(59) }
    var buttonEnabled by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            if (isTimerRunning) {
                timeLeft--
                buttonEnabled = true
            }
        }
        if (isTimerRunning) {
            isTimerRunning = false
            timeLeft = 0
        } else {
            while (isTimerRunning) {
                delay(1000L)
                timeLeft++
            }
        }
    }

    Button(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(0.8f)
            .height(48.dp)
            .background(
                if (!isTimerRunning) White
                else White24,
                RoundedCornerShape(56.dp)
            ),
        enabled = buttonEnabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (buttonEnabled) White else Black
        ),
        onClick = {
            if (!isTimerRunning) {
                isTimerRunning = true
                timeLeft = 59
            } else {
                if (otpValue.isEmpty()) {
                    coroutineScope.launch {
                        snackState.showSnackbar("CustomSnackbar", duration = SnackbarDuration.Short)
                    }
                }
            }
        },
    ) {
        Text(
            text = if (!isTimerRunning) stringResource(id = R.string.send_again).uppercase()
            else if (timeLeft > 0) "Отправить запрос заново ${formatTime(timeLeft)}".uppercase()
            else stringResource(id = R.string.send_again).uppercase(),
            fontSize = 12.sp,
            fontFamily = Constant.fontBold,
            color = if (!isTimerRunning) Black
            else White,
        )
    }
}

@Composable
fun EnterCodeInstructionText(formattedPhoneNumber: String) {
    Text(
        modifier = Modifier
            .padding(top = 15.dp),
        text = "На ваш номер $formattedPhoneNumber был отправлен СМС код, введите его ниже",
        textAlign = TextAlign.Center,
        color = White,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.farfetch_basis_regular))
    )
}

@Composable
fun EnterCodeTitle() {
    Text(
        modifier = Modifier.padding(top = 15.dp),
        text = stringResource(id = R.string.verify_phone_number).uppercase(),
        color = White,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        lineHeight = 36.sp,
        fontFamily = FontFamily(Font(R.font.farfetch_basis_bold))
    )
}

private fun formatTime(timeLeft: Int): String {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    return String.format("%02d:%02d", minutes, seconds)
}

private fun formatPhoneNumber(phoneNumber: String): String {
    return "+7 (${phoneNumber.substring(0, 3)}) ${phoneNumber.substring(3)}"
}

fun formatPhoneNumberSMS(phoneNumber: String): String {
    // Удаление всех нецифровых символов
    val cleanNumber = phoneNumber.filter { it.isDigit() }

    // Добавление кода страны
    return "+7$cleanNumber"
}