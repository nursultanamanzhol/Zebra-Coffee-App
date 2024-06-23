package com.example.zebracoffee.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.utils.Constant

@Composable
fun TextTemplate18(text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        fontFamily = Constant.fontRegular,
        color = Color.Black,
        fontSize = 18.sp,
    )
}

@Composable
fun TextTemplate14(text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        fontFamily = Constant.fontRegular,
        color = Color.Black,
        fontSize = 14.sp,
    )
}

@Composable
fun TextTemplate24(
    text: Int,
    color:Color = Color.Black
) {
    Text(
        text = stringResource(id = text).uppercase(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontFamily = Constant.fontBold,
        color = color,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun TextTemplateUniversal(
    text: String,
    color: Int = 1,
    font: Int,
    textAlign: TextAlign,
    modifier: Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontFamily = Constant.fontRegular,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.W700,
        fontSize = font.sp,
        textAlign = textAlign,
    )
}