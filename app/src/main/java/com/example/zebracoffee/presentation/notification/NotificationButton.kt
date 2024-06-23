package com.example.zebracoffee.presentation.notification

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.domain.entity.Notification
import com.example.zebracoffee.utils.Constant


@Composable
fun NotificationButton(
    link: String,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    textColor: Color = colorResource(id = R.color.notifiTextColor),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = if (isSystemInDarkTheme()) colorResource(id = R.color.notifiBackIconDark)  else colorResource(id = R.color.notifiBackIcon),
    ),
    minHeight: Dp = 36.dp,
    maxHeight: Dp = 36.dp,
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = "Перейти по ссылке",
            color = textColor,
            fontFamily = Constant.fontBold,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            modifier = Modifier

                .padding(horizontal = 4.dp),
            textAlign = TextAlign.Center,
        )
    },
    shape: Shape = RoundedCornerShape(32.dp),
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

    }
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            launcher.launch(intent)
        },
        modifier = modifier
            .heightIn(minHeight, maxHeight),
        shape = shape,
        elevation = null,
        colors = buttonColors,
        border = border,
        content = content,
    )
}

@UserNotification.Parcelize
abstract class UserNotification(
    val notification: Notification,
    val isRead: Boolean,
    val notificationType: String
) : Parcelable {
    annotation class Parcelize
}