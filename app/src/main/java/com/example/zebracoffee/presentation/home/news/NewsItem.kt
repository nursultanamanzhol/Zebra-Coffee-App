package com.example.zebracoffee.presentation.home.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
    item: News,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    if (isLoading) {
        ShimmerBox(
            modifier = Modifier.wrapContentWidth(),
            height = 80.dp,
        )
    }
    Box(
        modifier = Modifier
            .width(327.dp)
            .height(72.dp)
            .clip(shape)
            .background(
                color = ColorPrimary,
                shape = shape
            )
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(16.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = W700,
                    maxLines = 1,  // Ограничение текста одной строкой
                    overflow = TextOverflow.Ellipsis

                )
                val subTitle = item.subtitle
                val resultTitle = extractSubstring(subTitle, 32)
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = resultTitle,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = W700,
                    maxLines = 1,  // Ограничение текста одной строкой
                    overflow = TextOverflow.Ellipsis
                )
            }
            GlideImage(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 5.dp)
                    .width(120.dp)
                    .fillMaxHeight(),
                model = "${imageBaseUrl}${item.image}",
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun extractSubstring(subtitle: String, length: Int): String {
    return subtitle.take(length)
}
