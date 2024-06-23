package com.example.zebracoffee.presentation.home.stories

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.utils.Constant.fontBold
import com.example.zebracoffee.utils.Constant.fontRegular
import kotlin.math.max
import kotlin.math.min

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun StoriesScreen(
    navController: NavHostController,
    viewModel: StoriesScreenViewModel,
    homeList: List<Stories>,
) {
    val onBoardingList by viewModel.storiesList.collectAsStateWithLifecycle()
    val isLoadingStories by viewModel.isLoadingStories.collectAsStateWithLifecycle()

    if (homeList.isEmpty()) {
        if (isLoadingStories) {
            /* Column(
                 modifier = Modifier.fillMaxSize()
             ) {
                 Box(
                     modifier = Modifier
                         .fillMaxSize()
                         .background(Color.Blue)
                 ) {
                     CircularProgressIndicator(
                         modifier = Modifier
                             .size(56.dp)
                             .align(Alignment.Center),
                         color = Color.Red
                     )
                 }
             }*/
        } else {
            StoryScreen(
                story = onBoardingList,
                viewModel = viewModel,
                navigate = "",
                screenType = true,
                navController = navController
            )
        }
    } else {
        StoryScreen(
            story = homeList,
            viewModel = viewModel,
            navigate = "",
            screenType = false,
            navController = navController
        )
    }
}
/*
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryViewPager(
    list: List<Stories>,
    viewModel: InstagramStoryViewModel,
    navigate: String,
    screenType: Boolean,
    navController: NavHostController,
) {
    val pagerState = rememberPagerState(pageCount = { list.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            StoryScreen(
                story = listOf(list[page]),
                viewModel = viewModel,
                navigate = "",
                screenType = false,
                navController = navController
            )
        }
}*/

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoryScreen(
    story: List<Stories>,
    viewModel: StoriesScreenViewModel,
    navigate: String,
    screenType: Boolean,
    navController: NavHostController,
) {
    val currentStep = remember { mutableIntStateOf(0) }
    val stepTime = story[currentStep.intValue].duration * 1000
    val stepCount = story.size
    val isPaused = remember { mutableStateOf(false) }


    if (story.isEmpty()) {
        navController.navigate(navigate)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (story.getOrNull(currentStep.intValue)?.link != null) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = viewModel.getValidImageAddress(story.getOrNull(currentStep.intValue)?.link!!),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressBox(
                        indicatorColor = Color.White,
                        indicatorSize = 40.dp
                    )
                }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.splash_img),
                contentDescription = "Фоновое изображение",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(story) {
                    detectTapGestures(
                        onTap = { offset ->
                            currentStep.intValue = when {
                                offset.x < constraints.maxWidth / 2 -> max(
                                    0,
                                    currentStep.intValue - 1
                                )

                                currentStep.intValue == story.size - 1 -> {
                                    if (screenType) navController.navigate(navigate)
                                    else navController.popBackStack()
                                    currentStep.intValue
                                }

                                else -> {
                                    val nextStep = min(stepCount - 1, currentStep.intValue + 1)
                                    currentStep.intValue = nextStep
                                    isPaused.value = false
                                    nextStep
                                }
                            }
                        },
                        onPress = {
                            try {
                                isPaused.value = true
                                awaitRelease()
                            } finally {
                                isPaused.value = false
                            }
                        }
                    )
                }
                .padding(top = 120.dp, start = 20.dp)
        ) {
            Text(
                fontFamily = fontBold,
                modifier = Modifier
                    .fillMaxWidth(),
                text = story.getOrNull(currentStep.intValue)?.body?.title ?: "",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                modifier = Modifier
                    .padding(top = 20.dp),
                fontFamily = fontRegular,
                text = story.getOrNull(currentStep.intValue)?.body?.description ?: "",
                color = Color.White,
                fontSize = 16.sp,
            )
        }

        InstagramProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 13.dp, end = 13.dp),
            stepCount = stepCount,
            stepDuration = stepTime.toInt(),
            unSelectedColor = Color.LightGray,
            selectedColor = Color.White,
            currentStep = currentStep.intValue,
            onStepChanged = { currentStep.intValue = it },
            isPaused = isPaused.value,
            onComplete = {
                if (screenType) navController.navigate(navigate)
                else navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .padding(top = 5.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.05f)
                        .height(20.dp)
                        .width(20.dp)
                        .aspectRatio(1f)
                        .background(Color.Transparent, RoundedCornerShape(5.dp))
                ) {
                    if (story.getOrNull(currentStep.intValue)?.header?.icon != null) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = viewModel.getValidImageAddress(story.getOrNull(currentStep.intValue)?.header?.icon!!),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Фоновое изображение",
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_zebra_symbol),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .weight(0.9f)
                        .fillMaxHeight()
                        .padding(start = 5.dp),
                    text = story.getOrNull(currentStep.intValue)?.header?.title
                        ?: stringResource(id = R.string.zebra_coffee),
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Box(
                    modifier = Modifier
                        .weight(0.05f)
                        .size(50.dp)
                        .aspectRatio(1f)
                        .clickable {
                            if (screenType) navController.navigate(navigate)
                            else navController.popBackStack()
                        }
                        .background(Color.Transparent, RoundedCornerShape(5.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_v2),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 13.dp, end = 13.dp, bottom = 50.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = story.getOrNull(currentStep.intValue)?.footer?.title ?: "",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = fontRegular,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(0.8f)
                )

                if (!screenType && story.getOrNull(currentStep.intValue)?.footer?.title != null) {
                    Button(
                        modifier = if (screenType) Modifier
                            .width(92.dp)
                            .height(48.dp)
                        else Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        onClick = {
                            if (currentStep.intValue == story.size - 1) {
                                if (screenType) navController.navigate(navigate)
                                else navController.popBackStack()
                            }

                            val nextStep = min(stepCount - 1, currentStep.intValue + 1)
                            currentStep.intValue = nextStep
                            isPaused.value = false
                        },
                    ) {
                        Text(
                            modifier = Modifier,
                            text = story.getOrNull(currentStep.intValue)?.footer?.title
                                ?: "Далее",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = fontRegular
                        )

                    }
                } else if (screenType) {
                    Button(
                        modifier = Modifier
                            .width(92.dp)
                            .height(48.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        onClick = {
                            if (currentStep.intValue == story.size - 1) {
                                navController.navigate(navigate)
                            }

                            val nextStep = min(stepCount - 1, currentStep.intValue + 1)
                            currentStep.intValue = nextStep
                            isPaused.value = false
                        },
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Далее",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = fontRegular
                        )
                    }
                }
            }
        }
    }
}
/* else {
   val video = list.getOrNull(currentStep.intValue)?.link
   if (video != null) {
       VideoPlayer(
           videoUrl = viewModel.getValidImageAddress(video),
           durationMillis = stepTime
       )
       Log.d("API1", "video success")
   } else {
       Log.d("API1", "video fail")
   }
}*/