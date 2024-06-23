package com.example.zebracoffee.presentation.onboarding

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.OptIn
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsBlack
import com.example.zebracoffee.common.VideoPlayer
import com.example.zebracoffee.presentation.home.stories.InstagramProgressIndicatorVideo
import com.example.zebracoffee.utils.Constant.fontBold
import com.example.zebracoffee.utils.Constant.fontRegular
import kotlin.math.max
import kotlin.math.min


@SuppressLint("DiscouragedApi")
@OptIn(UnstableApi::class)
@Composable
fun OnBoardingScreen(
    navigateToTypeNumberScreen: () -> Unit,
) {
    SetupSystemBarsBlack()
    val context = LocalContext.current
    val title = remember {
        listOf(
            R.string.title_first,
            R.string.title_second,
            R.string.title_third
        )
    }
    val description = remember {
        listOf(
            R.string.description_first,
            R.string.description_second,
            R.string.description_third
        )
    }

    val stepCount = title.size
    var currentStep by remember { mutableIntStateOf(0) }

    val videos: List<Pair<Uri, Int>> = List(3) { index ->
        val videoResourceName = "video${index + 1}"
        val resourceId =
            context.resources.getIdentifier(videoResourceName, "raw", context.packageName)
        val videoUri = Uri.parse("android.resource://${context.packageName}/$resourceId")

        val mediaPlayer = MediaPlayer.create(context, resourceId)
        val duration = mediaPlayer.duration
        mediaPlayer.release()

        videoUri to duration
    }

    val stepTime = videos.map { it.second.toLong() }
    /*  val totalWidth = LocalConfiguration.current.screenWidthDp.dp
      val stepWidth = totalWidth / stepCount*/

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        var isVideoPaused by remember { mutableStateOf(false) }
        videos.forEachIndexed { index, (videoUri, duration) ->
            if (index == currentStep) {
                VideoPlayer(
                    uri = videoUri,
                    duration = duration.toLong(),
                    modifier = Modifier.fillMaxSize(),
                    isPaused = isVideoPaused,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            currentStep = when {
                                offset.x < constraints.maxWidth / 2 -> max(
                                    0,
                                    currentStep - 1
                                )

                                currentStep == 2 -> {
                                    navigateToTypeNumberScreen()
                                    /*
                                    event(OnBoardingEvent.SaveAppEntry)
*/
                                    currentStep
                                }

                                else -> {
                                    val nextStep = min(stepCount - 1, currentStep + 1)
                                    currentStep = nextStep
                                    isVideoPaused = true
                                    nextStep
                                }
                            }
                        },
                        onLongPress = {
                            isVideoPaused = true
                        },
                        onPress = {
                            try {
                                isVideoPaused = true
                                awaitRelease()
                            } finally {
                                isVideoPaused = false
                            }
                        }
                    )
                }
        ) {
        }


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InstagramProgressIndicatorVideo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                stepCount = stepCount,
                stepDuration = stepTime,
                unSelectedColor = Color.White.copy(alpha = 0.4f),
                selectedColor = Color.White,
                currentStep = currentStep,
                onStepChanged = { currentStep = it },
                isPaused = isVideoPaused,
                onComplete = { navigateToTypeNumberScreen() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 16.dp)
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
                        .background(Color.White, RoundedCornerShape(5.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_zebra_symbol),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 5.dp),
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.welcome_message))
                        append(" ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.zebra_coffee))
                        }
                    },
                    color = Color.White,
                    fontSize = 14.sp,
                )

                Box(
                    modifier = Modifier
                        .weight(0.05f)
                        .size(20.dp)
                        .aspectRatio(1f)
                        .clickable {
                            navigateToTypeNumberScreen()
                            /*
                            event(OnBoardingEvent.SaveAppEntry)
*/
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
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    fontFamily = fontBold,
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = title[currentStep]),
                    fontSize = 28.sp,
//                    fontWeight = W700,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    lineHeight = 36.sp
                )
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = buildAnnotatedString {
                        append(stringResource(id = description[currentStep]))
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp,
                    fontFamily = fontRegular,
                    letterSpacing = 2.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.description_text),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = fontRegular,
                )
                Button(
                    modifier = Modifier
                        .width(92.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    onClick = {
                        if (currentStep == 2) {
                            navigateToTypeNumberScreen()
                        }
                        val nextStep = min(stepCount - 1, currentStep + 1)
                        currentStep = nextStep
                    },
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.btn_next),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = fontRegular
                    )
                }
            }
        }
    }
}