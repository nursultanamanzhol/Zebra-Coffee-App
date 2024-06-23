package com.example.zebracoffee.presentation.registration.personalInfo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.presentation.registration.personalInfo.DateDefaults.DATE_LENGTH
import com.example.zebracoffee.presentation.registration.personalInfo.DateDefaults.DATE_MASK
import com.example.zebracoffee.ui.theme.White12
import com.example.zebracoffee.ui.theme.White40
import com.example.zebracoffee.utils.Constant
import kotlin.math.absoluteValue

@Composable
fun FormatDateTextField(
    textHint: String,
    focusedHint: String = textHint,
    onValueChanged: (String) -> Unit,
    shouldFormat: Boolean = false,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text // Default to Text keyboard type
) {
    var date by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = date,
        onValueChange = {
            if (it.length <= DATE_LENGTH) {
                date = it
                onValueChanged(it)
            }
        },
        visualTransformation = MaskVisualTransformation(DATE_MASK),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
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


object DateDefaults {
    const val DATE_MASK = "##.##.####"
    const val DATE_LENGTH = 8
}

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}