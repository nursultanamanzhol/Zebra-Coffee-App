package com.example.zebracoffee.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.utils.Constant

@Composable
fun CustomButtonPrimary(
    text: String,
    color: Color = ColorPrimary,
    onClick: () -> Unit,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = { onClick.invoke() },
    ) {
        Text(
            text = text.uppercase(),
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = textColor,
            fontFamily = Constant.fontRegular
        )
    }
}

@Composable
fun CustomButtonPrimaryHome(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPrimary,
            contentColor = Color.White
        ),
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = Constant.fontRegular
        )
    }
}

@Composable
fun CustomButtonMenuNav(
    text: String,
    color: Color,
    onClick: () -> Unit,
    textColor: Color,
) {
    Button(
        modifier = Modifier
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = { onClick.invoke() },
    ) {
        Text(
            text = text,
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = textColor,
            fontFamily = Constant.fontRegular
        )
    }
}

@Composable
fun ButtonMenuBasket(
    text: String,
    color: Color,
    onClick: () -> Unit,
    textColor: Color,
) {
    Row(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .padding(horizontal = 16.dp)
            .background(color, shape = RoundedCornerShape(16.dp))
            .height(64.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                Alignment.Center,
            ) {
                Text(
                    text = "0",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = textColor,
                    fontFamily = Constant.fontRegular
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = text.uppercase(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = textColor,
                fontFamily = Constant.fontRegular
            )
        }

        Text(
            text = "0₸",
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = textColor,
            fontFamily = Constant.fontRegular,
            modifier = Modifier
                .padding(end = 24.dp)
        )
    }
}