package com.example.zebracoffee.presentation.registration.typeNumber

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.ui.theme.White56
import com.example.zebracoffee.utils.Constant

@Composable
fun ColoredTextWithClickableLink(
    modifier: Modifier = Modifier,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
) {

    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = White56,
                fontFamily = Constant.fontRegular,
                fontSize = 12.sp
            )
        ) {
            append("Нажимая на кнопку Далее вы соглашаетесь\n")
        }
        withStyle(
            style = SpanStyle(
                color = White56,
                fontFamily = Constant.fontRegular,
                fontSize = 12.sp
            )
        ) {
            append("с ")
        }
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontFamily = Constant.fontRegular,
                fontSize = 12.sp
            )
        ) {
            append("Политикой конфиденциальности")
        }
        withStyle(
            style = SpanStyle(
                color = White56,
                fontFamily = Constant.fontRegular,
                fontSize = 12.sp
            )
        ) {
            append(" и\n")
        }
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontFamily = Constant.fontRegular,
                fontSize = 12.sp
            )
        ) {
            append("Условиями использования")
        }
    }

    ClickableText(
        text = text,
        onClick = { offset ->
            when (offset) {
                in 42 until 70 -> onPrivacyPolicyClick()
                in 72 until 112 -> onTermsOfServiceClick()
            }
        }
    )
}