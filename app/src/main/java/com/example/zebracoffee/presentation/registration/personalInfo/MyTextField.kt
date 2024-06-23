package com.example.zebracoffee.presentation.registration.personalInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.ui.theme.BackLoyaltyCard
import com.example.zebracoffee.ui.theme.FillColor3
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.ui.theme.White12
import com.example.zebracoffee.ui.theme.White40
import com.example.zebracoffee.utils.Constant

@Composable
fun MyTextField(
    textHint: String,
    focusedHint: String = textHint,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val isError by rememberSaveable { mutableStateOf(false) }
    val errorMessage = "Формат даты не поддерживается"

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .semantics {
                if (isError) error(errorMessage)
            }
            .border(
                width = 2.dp,
                color = if (isFocused) Color.Transparent else White12,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = if (isFocused) focusedHint else textHint,
                color = White40,
                fontFamily = Constant.fontBold,
                fontSize = 18.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = Constant.fontBold
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = White12,
            unfocusedContainerColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        )
    )
}


@Composable
fun SearchTextField(
    textHint: String,
    focusedHint: String = textHint,
    onValueChanged: (String) -> Unit,
    startIcon: Int = R.drawable.search_web,
    endIcon: Int = R.drawable.search_web,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val isError by rememberSaveable { mutableStateOf(false) }
    val errorMessage = "Формат даты не поддерживается"
    val colorBackActive: Color = if (isFocused) {
        BackLoyaltyCard
    } else {
        White
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(colorBackActive, shape = RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_web),
            contentDescription = null,
            modifier = Modifier
                .background(colorBackActive)
                .padding(start = 12.dp),
            tint = FillColor3
        )

        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChanged(it)
            },
            modifier = Modifier
                .background(colorBackActive)
                .weight(0.6f)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .semantics {
                    if (isError) error(errorMessage)
                },
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = if (isFocused) focusedHint else textHint,
                    color = White40,
                    fontFamily = Constant.fontBold,
                    fontSize = 18.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontFamily = Constant.fontBold
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
        )
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
                .size(24.dp)
                .clickable {
                    text = ""
                    onValueChanged("")
                }
                .background(
                    color = FillColor3,
                    shape = CircleShape
                ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_v2),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(18.dp),
                tint = Color.White
            )
        }
    }
}
