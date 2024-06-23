package com.example.zebracoffee.presentation.coffeeShops.reviewScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CustomButtonPrimary
import com.example.zebracoffee.common.MainToolbar
import com.example.zebracoffee.common.SnackbarBlock
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.presentation.profileScreen.userInfoScreen.ProfileTextField
import com.example.zebracoffee.presentation.profileScreen.userInfoScreen.formatDate
import com.example.zebracoffee.ui.theme.BackLoyaltyCard
import com.example.zebracoffee.ui.theme.FillColor4Light
import com.example.zebracoffee.ui.theme.MediumYellow
import com.example.zebracoffee.ui.theme.Primary
import com.example.zebracoffee.ui.theme.Tertiary
import com.example.zebracoffee.ui.theme.Tertiary40
import com.example.zebracoffee.ui.theme.TextColor2Light
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel,
    id: String?,
    navController: NavHostController,
) {
    val reviewList by viewModel.reviewMessage.collectAsStateWithLifecycle()
    val isLoadingReview by viewModel.isLoadingReview.collectAsStateWithLifecycle()
    val newReview by viewModel.newReview.collectAsStateWithLifecycle()
    val updateReview by viewModel.updateReview.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val snackState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        id?.let { viewModel.getReviewMessages(it.toInt()) }
    }
    var showLoadingIndicator by remember { mutableStateOf(false) }
    LaunchedEffect(isLoadingReview) {
        if (isLoadingReview) {
            showLoadingIndicator = true
        } else {
            delay(1500) // Задержка после того как isLoadingReview станет false
            showLoadingIndicator = false
        }
    }

    if (showLoadingIndicator) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .size(35.dp),
                color = Primary
            )
        }
    } else {
        Scaffold(
            snackbarHost = {
                SnackbarBlock(
                    snackState = snackState,
                    text = "Спасибо! Ваш отзыв принят",
                    iconId = R.drawable.ic_star_rating
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                ) {
                    MainToolbar(text = "Отзывы", onClick = { navController.popBackStack() })
                    ReviewScreenResult(reviewList, sheetState, viewModel, id)

                    if (newReview is Resource.Success) {
                        coroutineScope.launch {
                            snackState.showSnackbar(
                                "CustomSnackbar",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    if (updateReview is Resource.Success) {
                        coroutineScope.launch {
                            snackState.showSnackbar(
                                "CustomSnackbar",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreenResult(
    listItem: List<ReviewListItem>,
    sheetState: SheetState,
    viewModel: ReviewViewModel,
    idShop: String?,
) {
    Log.d("NoReviewBlock", listItem.toString())
    val shopRating = listItem.firstOrNull()?.shop_rating ?: "0"
    val review = listItem.count { it.type == "review" }
    val grade = listItem.count { it.type == "grade" } + review

    var reviewText by remember { mutableStateOf("") } // Добавьте это
    LaunchedEffect(listItem) {
        listItem.firstOrNull()?.text?.let {
            reviewText = it
        }
    }

    Log.d("ReviewScreenResult:", "$shopRating")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Рейтинг $shopRating из 5",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 28.sp,
            fontFamily = Constant.fontBold
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "$grade оценок  и $review отзывов",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontFamily = Constant.fontRegular
        )

        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Ваш отзыв".uppercase(),
            fontFamily = Constant.fontBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.inversePrimary
        )
        idShop?.let { ReviewCard(sheetState, listItem, viewModel, it) }
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Все отзывы".uppercase(),
            fontFamily = Constant.fontBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        if (listItem.isEmpty()) {
            Log.d("NoReviewBlock", listItem.toString())
            NoReviewBlock()
        } else {
            ReviewBlockResult(listItem)
        }
    }
}

@Composable
fun ReviewBlockResult(listItem: List<ReviewListItem>) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(listItem) { item ->
            ReviewItem(item = item)
        }
    }
}

@Composable
fun NoReviewBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(BackLoyaltyCard, CircleShape),
            contentAlignment = Alignment.TopCenter
        ) {
            Icon(
                modifier = Modifier
                    .padding(25.dp)
                    .size(80.dp),
                painter = painterResource(id = R.drawable.ic_no_review),
                contentDescription = null,
                tint = TextColor2Light
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.no_reviewScreen),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewCard(
    sheetState: SheetState,
    listItem: List<ReviewListItem>,
    viewModel: ReviewViewModel,
    idShop: String,
) {
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val myReview = listItem.firstOrNull()?.my_review
    val rating = listItem.firstOrNull()?.grade
    val reviewText = listItem.firstOrNull()?.text
    val shopId = listItem.firstOrNull()?.shop
    val reviewId = listItem.firstOrNull()?.id

    val scrollState = rememberScrollState()

    var newReviewText by remember { mutableStateOf("") }
    var updateReviewText by remember { mutableStateOf(reviewText) }

    var ratingNullReview by remember {
        mutableStateOf(0)
    }

    var ratingReview by remember {
        mutableStateOf(rating)
    }

    ElevatedCard(
        onClick = { isSheetOpen = true },
        modifier = Modifier.padding(top = 8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            if (myReview == true) {
                MyReviewIsHave(listItem = listItem, onClick = { isSheetOpen = true })
            } else {
                MyReviewIsNot()
//                MyReviewIsHave(listItem = listItem, onClick = { isSheetOpen = true })
            }
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(0.85f),
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                },
                containerColor = MaterialTheme.colorScheme.background,
                dragHandle = { BottomSheetDefaults.DragHandle() },
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (myReview == true) {
                        StarRating(
                            enabled = true, filled = rating!!.toInt(),
                            onRatingChanged = { newValue ->
                                ratingReview = newValue
                            })
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        ProfileTextField(
                            label = "Ваш отзыв",
                            onValueChanged = { newValue ->
                                updateReviewText = newValue
                            },
                            value = reviewText.toString(),
                            keyboardType = KeyboardType.Text
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CustomButtonPrimary(text = "Сохранить",
                            onClick = {
                                viewModel.updateReview(
                                    ratingReview!!,
                                    updateReviewText!!,
                                    shopId!!,
                                    reviewId!!
                                )
                                isSheetOpen = false
                            }
                        )

                    } else {
                        Column(
                            modifier = Modifier
                                .weight(0.8f)
                                .background(Color.Transparent)
                                .verticalScroll(state = scrollState)
                        ) {
                            StarRating(
                                enabled = true,
                                filled = ratingNullReview,
                                onRatingChanged = { newRating ->
                                    ratingNullReview = newRating
                                }
                            )
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            ProfileTextField(
                                label = "Ваш отзыв",
                                onValueChanged = {
                                    newReviewText = it
                                },
                                value = "",
                                keyboardType = KeyboardType.Text
                            )
                        }
                        CustomButtonPrimary(
                            text = "отправить",
                            onClick = {
                                newReviewText.let {
                                    viewModel.createNewReview(
                                        ratingNullReview,
                                        it,
                                        idShop.toInt(),

                                        )
                                }
                                isSheetOpen = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyReviewIsHave(
    listItem: List<ReviewListItem>,
    onClick: () -> Unit,
) {
    val status = listItem.firstOrNull()?.status

    Column() {
        ReviewItem(item = listItem.firstOrNull()!!, showStatusContent = true)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(FillColor4Light, RoundedCornerShape(56.dp)),
            shape = RoundedCornerShape(56.dp),
            onClick = { onClick() },
            colors = ButtonDefaults.textButtonColors(
                contentColor = FillColor4Light
            )
        ) {
            Text(
                text = stringResource(id = R.string.btn_edit).uppercase(),
                fontSize = 12.sp,
                fontFamily = Constant.fontBold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun MyReviewIsNot() {
    StarRating(enabled = false)
    Spacer(modifier = Modifier.padding(top = 10.dp))
    ProfileTextField(
        label = "Ваш отзыв",
        enabled = false,
        onValueChanged = {}, value = ""
    )
}

@Composable
fun StarRating(
    enabled: Boolean,
    size: Dp = 48.dp,
    filled: Int = 0,
    onRatingChanged: (Int) -> Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    @SuppressLint("ModifierParameter") rowModifier: Modifier = Modifier.fillMaxWidth(),
) {
    var ratingState by remember {
        mutableStateOf(filled)
    }

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_rating_star),
                contentDescription = "Review Rating",
                modifier = Modifier
                    .size(size)
                    .clickable(enabled = enabled) {
                        if (enabled) {
                            ratingState = i
                            onRatingChanged(i)
                        }
                    },
                tint = if (i <= ratingState) Tertiary else Tertiary40
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewItem(item: ReviewListItem, showStatusContent: Boolean = false) {
    val date = formatDate(item.date)

    val status = item.status

    val statusVisible by remember {
        mutableStateOf(showStatusContent)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Top)
                .clip(CircleShape),
            model = "${imageBaseUrl}${item.avatar}",
            contentDescription = "",
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(modifier = Modifier) {
                StarRating(
                    enabled = false,
                    size = 16.dp,
                    filled = item.grade,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    rowModifier = Modifier.width(100.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "${item.grade}.0",
                    color = TextColor2Light,
                    fontSize = 14.sp,
                    fontFamily = Constant.fontRegular
                )
            }
            if (item.text.isNotBlank()) {
                Text(
                    text = item.text,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontFamily = Constant.fontRegular
                )
            }
            Text(
                text = date,
                color = TextColor2Light,
                fontSize = 14.sp,
                fontFamily = Constant.fontRegular
            )
            if (statusVisible) {
                if (status == "on_moderation") {
                    Row(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.ic_clock_review),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "На проверке",
                            color = MediumYellow,
                            fontSize = 14.sp,
                            fontFamily = Constant.fontRegular
                        )
                    }
                }
            } else {
                Box {}
            }
        }
    }
}

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}