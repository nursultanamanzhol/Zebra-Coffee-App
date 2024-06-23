package com.example.zebracoffee.presentation.registration.chooseAvatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.ui.theme.СolorBorderDarkGray

@Composable
fun AvatarItem(
    item: AvatarImage,
    onAvatarSelected: (AvatarImage) -> Unit,
    isSelected: Boolean,
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.Center) {
        val colorBorderWhite: Color = if (isSelected) {
            White
        } else {
            Color.Transparent
        }
        val colorBorderDarkGray: Color = if (isSelected) {
            СolorBorderDarkGray.copy(alpha = 0.85f)
        } else {
            Color.Transparent
        }

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = colorBorderWhite,
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = colorBorderDarkGray,
                        shape = CircleShape
                    )
            ) {
                SubcomposeAsyncImage(
                    model = item.avatar,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clickable {
                            onAvatarSelected(item)
                            isFocused = true
                        }
                        .size(80.dp)
                        .clip(CircleShape),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressBox()
                    }
                )
            }
        }
    }
}