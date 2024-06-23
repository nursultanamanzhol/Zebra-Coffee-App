package com.example.zebracoffee.presentation.profileScreen.userInfoScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.registration.personalInfo.DateDefaults.DATE_MASK
import com.example.zebracoffee.presentation.registration.personalInfo.MaskVisualTransformation
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.utils.Constant

@Composable
fun ProfileTextField(
    label: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean = true,
    value: String,
) {
    var text by remember { mutableStateOf(value) }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular,
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Blue,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            errorLabelColor = Color.Red,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.fill_colors_textfield) else Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledLabelColor = MaterialTheme.colorScheme.inversePrimary,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledTextColor = MaterialTheme.colorScheme.inversePrimary,
        ),
        singleLine = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        enabled = enabled,
    )
}



@Composable
fun NumberTextField(
    label: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean,
    value: String,
) {
    if (!enabled) {
        // Отображение значения в виде текста, если поле неактивно
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(12.dp))
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Constant.fontRegular,
                    color = MediumGray2
                )
            )
        }
    } else {
        var text by remember { mutableStateOf(value) }

        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChanged(it)
            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Constant.fontRegular,
                color = MaterialTheme.colorScheme.onSurface
            ),
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Blue,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
                errorLabelColor = Color.Red,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.fill_colors_textfield) else Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledLabelColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledTextColor = MaterialTheme.colorScheme.inversePrimary,
            ),
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            enabled = true
        )
    }
}


fun formatDate(inputDate: String): String {
    val parts = inputDate.split("-")
    if (parts.size == 3) {
        return parts[2] + parts[1] + parts[0]
    }
    return inputDate // Возврат исходной строки, если формат некорректен
}

fun formatDateForBackEnd(inputDate: String): String {
    if (inputDate.length == 8) { // Проверяем, что длина строки соответствует формату ddmmyear
        val day = inputDate.substring(0, 2)   // Извлекаем день
        val month = inputDate.substring(2, 4) // Извлекаем месяц
        val year = inputDate.substring(4)     // Извлекаем год

        return "$year-$month-$day" // Собираем строку в формате year-mm-dd
    }
    return inputDate // Возврат исходной строки, если формат некорректен
}


@Composable
fun BirthdayTextField(
    label: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean = true,
    value: String,
) {

    val formattedDate = formatDate(value)

    var text by remember { mutableStateOf(formattedDate) }

    TextField(
        visualTransformation = MaskVisualTransformation(DATE_MASK),
        value = text,
        onValueChange = {
            if (it.length <= 8) {
                text = it
                onValueChanged(it)
            }
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular,
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Blue,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            errorLabelColor = Color.Red,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.fill_colors_textfield) else Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledLabelColor = MaterialTheme.colorScheme.inversePrimary,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledTextColor = MaterialTheme.colorScheme.inversePrimary,
        ),
        singleLine = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        enabled = enabled,
    )
}
