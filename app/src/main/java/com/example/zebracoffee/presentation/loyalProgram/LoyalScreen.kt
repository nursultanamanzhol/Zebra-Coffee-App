@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.zebracoffee.presentation.loyalProgram

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.LoyalCardToolbar
import com.example.zebracoffee.common.TextTemplateUniversal
import com.example.zebracoffee.domain.entity.LoyaltyCardProgress
import com.example.zebracoffee.presentation.loyalProgram.progressBar.CircularProgress
import com.example.zebracoffee.presentation.loyalProgram.progressBar.ProgressValues
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.ui.theme.BackLoyaltyCard
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@Composable
fun LoyalScreen(
    navController: NavController,
    viewModelLoyal: LoyalScreenViewModel,
) {

    val loyaltyCardState by viewModelLoyal.loyaltyCard.collectAsStateWithLifecycle()
    val userDataState by viewModelLoyal.userDataCard.collectAsStateWithLifecycle()

    val loyaltyDiscountItems by viewModelLoyal.loyaltyProgressState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LoyalCardToolbar(
                text = stringResource(id = R.string.loyalSystemsToolbar).uppercase(),
                onClick = { navController.popBackStack() },
                helpClick = { }
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(state = scrollState)
                .background(MaterialTheme.colorScheme.background),

            ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                LoyalCardWelcomeBlock(
                    userDataState, loyaltyCardState
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.discountLevel).uppercase(),
                color = TextColor2Light,
                lineHeight = 24.sp,
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                fontWeight = W700
            )
            Spacer(modifier = Modifier.height(5.dp))
            DiscountLevel(items = loyaltyDiscountItems, viewModelLoyal)
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.getMoreDiscount).uppercase(),
                color = TextColor2Light,
                fontSize = 12.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Start,
                fontWeight = W700
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = stringResource(id = R.string.getMoreDiscountDesc),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    fontWeight = W400,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Constant.fontRegular,
                    lineHeight = 24.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.getMoreDiscountDescPersentag),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    fontWeight = W400,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

    }
}


@Composable
fun DiscountLevel(
    items: List<LoyaltyCardProgress>,
    viewModelLoyal: LoyalScreenViewModel,
) {

    LazyVerticalGrid(
        modifier = Modifier
            .height(310.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        items(items) { item ->
            DiscountItem(item = item, viewModelLoyal)
        }

    }

}

private var isCurrentId: Int = 0

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscountItem(
    item: LoyaltyCardProgress,
    viewModel: LoyalScreenViewModel,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val theme by viewModel.theme.collectAsState()

    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }

    var isSheetOpenLoyal by rememberSaveable {
        mutableStateOf(false)
    }

    val boxSize = if (item.progressPercentage == 100) {
        83.dp
    } else {
        70.dp
    }
    val boxProgress = if (item.progressPercentage == 100) {
        0f
    } else {
        item.progressPercentage.toFloat()
    }


    if (item.isCurrent) {
        isCurrentId = item.id + 1
    } else {
        Log.d("testPersent", "isCurrentId")
    }

    ConstraintLayout(
        modifier = Modifier
            .height(150.dp)
            .width(130.dp)
    ) {
        val (discountNum, progress, title, progressPercentage) = createRefs()

        CircularProgress(
            progressPercentage = item.progressPercentage,
            modifier = Modifier
                .size(111.dp)
                .constrainAs(progress) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            values = {
                ProgressValues(
                    primary = boxProgress,
                )
            },
            maxValue = 100f,
            strokeWidth = 10f
        )

        Box(
            modifier = Modifier
                .constrainAs(discountNum) {
                    start.linkTo(progress.start)
                    top.linkTo(progress.top)
                    end.linkTo(progress.end)
                    bottom.linkTo(progress.bottom)
                }
                .width(boxSize)
                .height(boxSize)
                .clickable {
                    isSheetOpenLoyal = true
                }
                .background(BackLoyaltyCard.copy(alpha = 0.5f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = "${imageBaseUrl}${item.image}",
                modifier = Modifier
                    .width(45.dp)
                    .height(50.dp)
                    .background(Color.Transparent),
                contentDescription = "",
                contentScale = ContentScale.Inside,
                loading = {
                    CircularProgressBox()
                }
            )
        }

        Row(
            Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
        ) {
            TextTemplateUniversal(
                ("${item.discountPercentage}%"),
                (R.color.black),
                font = 14,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )
            if (item.isCurrent) {
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .aspectRatio(1f)
                        .background(ColorPrimary, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_done_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                    )
                }
            }
        }
        if (!item.isCurrent && item.id == isCurrentId) {
            Log.d("TextIsCurrentId", "item.id")
            Row(
                Modifier
                    .constrainAs(progressPercentage) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom, 5.dp)
                        bottom.linkTo(parent.bottom)
                    },
            ) {
                TextTemplateUniversal(
                    "${item.progressPercentage}/${item.threshold}",
                    color = (R.color.notifiDateTime),
                    font = 12,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                )

            }
        }
        if (isSheetOpenLoyal) {
            ModalBottomSheet(
                modifier = Modifier
                    .requiredHeight(350.dp)
                    .background(Color.Transparent),
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpenLoyal = false
                },
                dragHandle = {
                    bottomLoyaltyDesign(
                        item = item,
                        theme = darkTheme,
                    ) { isSheetOpenLoyal = false }
                }
            ) {

            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun bottomLoyaltyDesign(
    item: LoyaltyCardProgress,
    theme: Boolean,
    onButtonClick: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val preloaderProgress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .padding(horizontal = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(1.dp))


            Row(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {

                Box(

                    modifier = Modifier
                        .size(44.dp)
                        .aspectRatio(1f)
                        .clickable {
                            onButtonClick()
                        }
                        .background(
                            color = if (theme) colorResource(id = R.color.fill_colors_4_dark) else colorResource(
                                id = R.color.fill_colors_4
                            ),
                            shape = RoundedCornerShape(38.dp)


                        ),
                    contentAlignment = Alignment.TopEnd,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_secondary_btm_sheet),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(14.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(160.dp)
                    .background(
                        BackLoyaltyCard.copy(alpha = 0.5f), shape = RoundedCornerShape(80.dp)
                    ),

            ) {
                GlideImage(
                    model = "${imageBaseUrl}${item.image}",
                    modifier = Modifier
                        .size(160.dp)
                        .padding(24.dp)
                        .background(Color.Transparent),
                    contentDescription = "null",
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextTemplateUniversal(
                    text = item.discountPercentage.toString(),
                    (R.color.black),
                    28,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
                TextTemplateUniversal(
                    text = stringResource(id = R.string.discountBottom).uppercase(),
                    (R.color.black),
                    28,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val textBottom: String = if (item.isCurrent) {
                    stringResource(id = R.string.loyalLevelDiscount)
                } else {
                    stringResource(
                        R.string.remaining_purchases,
                        item.threshold,
                        item.discountPercentage
                    )
                }
                Text(
                    text = textBottom,
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = Constant.fontRegular,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
        if (item.isCurrent) {
            LottieAnimation(
                composition = composition,
                progress = { preloaderProgress },
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}
