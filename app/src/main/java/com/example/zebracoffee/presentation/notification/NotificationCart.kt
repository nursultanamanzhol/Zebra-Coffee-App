package com.example.zebracoffee.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.domain.entity.Notification
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.utils.Constant
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NotificationCart(
    modifier: Modifier,
    navController: NavController,
    notification: Notification,
    theme: Boolean
) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = inputFormat.parse(notification.datetime)
    val clockTime = outputFormat.format(date)

    val image = if (notification.type == null){
        "https://st.depositphotos.com/1005549/3024/v/450/depositphotos_30248493-stock-illustration-abstract-dark-background-with-stripes.jpg"
    } else {
        "${Constant.imageBaseUrl}${notification.type!!.image}"
    }

    Row(
        horizontalArrangement =
        Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .padding(4.dp)
                .align(Alignment.Top),
        ) {

            val image = if (notification.type == null){
                    "https://st.depositphotos.com/1005549/3024/v/450/depositphotos_30248493-stock-illustration-abstract-dark-background-with-stripes.jpg"
            } else {
                "${Constant.imageBaseUrl}${notification.type!!.image}"
            }
            GlideImage(
                model = image,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 8.dp,
                        color = Color.Transparent,
                        shape = CircleShape
                    ),
                contentDescription = notification.title,
                contentScale = ContentScale.Crop,
            )


        }

        Box(
            modifier = Modifier
                .padding(start = 5.dp, bottom = 16.dp)
                .background(
                    color = Color.Transparent,
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = notification.title,
                        fontFamily = Constant.fontBold,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 13.dp),
                    text = notification.body,
                    fontFamily = Constant.fontRegular,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, start = 13.dp),
                    text = clockTime,
                    fontFamily = Constant.fontBold,
                    fontWeight = FontWeight.W500,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = colorResource(id = R.color.notifiDateTime)
                )

                if (notification.notification_url != null && notification.notification_url!!.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    notification.notification_url?.let {
                        NotificationButton(
                            modifier = Modifier
                                .padding(horizontal = 13.dp),
                            link = it,
                            buttonColors = ButtonDefaults.buttonColors(
                                backgroundColor = if (theme) colorResource(id = R.color.notifiBackIconDark)  else colorResource(id = R.color.notifiBackIcon),
                            ),
                        )
                    }
                }
            }
        }
    }
}
