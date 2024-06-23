package com.example.zebracoffee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.ShimmerBox
import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.data.modelDto.StoriesCategory
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoriesBlockItem(
    viewModel: HomeViewModel,
    item: StoriesCategory,
    isLoading: Boolean,
    onclick: (List<Stories>) -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    if (isLoading && !viewModel.isInitialized) {
        ShimmerBox(
            height = 112.dp,
            width = 96.dp
        )
    } else {
        Box(
            modifier = Modifier
                .height(112.dp)
                .width(96.dp)
                .clip(shape)
                .clickable { onclick(item.stories) }
        ) {
            GlideImage(
                model = "$imageBaseUrl${item.image}",
                modifier = Modifier.fillMaxSize(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
            )
            // Градиентный слой
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.colorStoriesGradient),
                                colorResource(id = R.color.colorStoriesGradient).copy(alpha = 0.6f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = item.title ?: "null",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = W400,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}
