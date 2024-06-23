package com.example.zebracoffee.presentation.registration.personalInfo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsBlack
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.registration.typeNumber.TypeNumberSnackbar
import com.example.zebracoffee.ui.theme.MainRed
import com.example.zebracoffee.ui.theme.White40
import com.example.zebracoffee.utils.Constant
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PersonalInfoScreen(
    navController: NavHostController,
    viewModel: PersonalInfoViewModel,
) {
    SetupSystemBarsBlack()
    var nameValue by remember { mutableStateOf("") }
    var birthDateValue by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf("") }

    val updateInfoState by viewModel.updateInfoState.collectAsStateWithLifecycle()

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
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { navController.popBackStack() },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "icon back",
                    tint = Color.White
                )

                Text(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Расскажите нам\nнемного о себе".uppercase(),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    fontFamily = Constant.fontBold
                )

                Text(
                    modifier = Modifier
                        .width(361.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp),
                    text = "Это позволит нам дарить вам подарки и\nскидки в нужное для вас время",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.farfetch_basis_regular))
                )

                if (updateInfoState is Resource.Failure) {
                    coroutineScope.launch {
                        snackState.showSnackbar("CustomSnackbar")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                MyTextField(
                    textHint = stringResource(id = R.string.name_hint),
                    focusedHint = "",
                    onValueChanged = { newValue ->
                        nameValue = newValue
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormatDateTextField(
                    textHint = stringResource(id = R.string.birthday_hint),
                    focusedHint = "__.__.___",
                    onValueChanged = { newValue ->
                        birthDateValue = newValue
                    },
                    shouldFormat = true,
                    keyboardType = KeyboardType.Number
                )

                if (updateInfoState is Resource.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .size(56.dp),
                        color = Color.White
                    )
                }

                if (errorState.isNotEmpty()) {
                    Text(
                        text = errorState,
                        fontSize = 14.sp,
                        fontFamily = Constant.fontRegular,
                        color = MainRed,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = nameValue.isNotEmpty() && birthDateValue.isNotEmpty(),
                        modifier = Modifier
                            .width(100.dp)
                            .height(48.dp)
                            .background(
                                if (nameValue.isNotEmpty() && birthDateValue.isNotEmpty()) Color.White
                                else White40,
                                RoundedCornerShape(56.dp)
                            ),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor =
                            if (nameValue.isNotEmpty() && birthDateValue.isNotEmpty()) Color.Black
                            else Color.White
                        ),
                        onClick = {
                            val formatDate = reformatDate(birthDateValue)
                            val requestFormat = requestDate(formatDate)
                            if (isValidDate(requestFormat)) {
                                viewModel.updateInfo(nameValue, requestFormat)
                            } else {
                                errorState = "Формат даты не поддерживается"
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_next).uppercase(),
                            fontSize = 12.sp,
                            fontFamily = Constant.fontBold,
                            color = if (nameValue.isNotEmpty() && birthDateValue.isNotEmpty()) Color.Black
                            else Color.White
                        )
                    }
                }
            }
            LaunchedEffect(updateInfoState) {
                if (updateInfoState is Resource.Success) {
                    navController.navigate(route = "${MainDestinations.AvatarScreen_route}/$nameValue")
                    viewModel.resetState()
                    errorState = ""
                }
            }
        }
    )
}

fun reformatDate(input: String): String {
    if (input.length != 8) {
        return "Invalid input"
    }
    return "${input.substring(0, 2)}.${input.substring(2, 4)}.${input.substring(4)}"
}

fun requestDate(input: String): String {
    val parts = input.split(".")

    if (parts.size != 3) {
        return "Invalid input"
    }

    return "${parts[2]}-${parts[1]}-${parts[0]}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidDate(dateString: String): Boolean {
    val parts = dateString.split("-")
    if (parts.size != 3) {
        return false
    }

    try {
        val year = when (parts[0].length) {
            4 -> {
                if (parts[0][0] == '0') {
                    return false
                }
                parts[0].toInt()
            }

            2 -> {
                val currentYearLastTwoDigits = LocalDate.now().year % 100
                val inputYear = parts[0].toInt()

                if (inputYear <= currentYearLastTwoDigits) {
                    2000 + inputYear
                } else {
                    1900 + inputYear
                }
            }

            else -> return false
        }

        val month = parts[1].toInt()
        val day = parts[2].toInt()

        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return false
        }

        val currentDate = LocalDate.now()
        val inputDate = LocalDate.of(year, month, day)

        return !inputDate.isAfter(currentDate)
    } catch (e: NumberFormatException) {
        return false
    }
}
