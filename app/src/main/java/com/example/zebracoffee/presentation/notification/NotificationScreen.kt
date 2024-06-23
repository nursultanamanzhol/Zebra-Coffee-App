package com.example.zebracoffee.presentation.notification

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.MainToolbar
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationsViewModel
) {
    val scrollState = rememberLazyListState()
    val theme by viewModel.theme.collectAsState()

    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }

    var isEndList = remember { false }
    val items = viewModel.pager.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        MainToolbar(
            text = stringResource(id = R.string.toolbarNotification).uppercase(),
            onClick = {navController.popBackStack()}
        )
        if (items.loadState.refresh is LoadState.Loading) {
            CircularProgressBox()
        } else if (items.itemSnapshotList.isEmpty()) {
            NoNotificationScreen()
        } else {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
            ) {
                items(items.itemCount) { index ->
                    val date = items[index]
                    Log.d("date","index: $index  date : $date")
                    if (index == 0) {
                        if (date != null) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                text = if (date.datetime != null) viewModel.getDate(dateTime = date.datetime.dropLast(11)) else "",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.height(10.dp))
                        if (date != null) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                text = if (date.datetime != null) viewModel.getDate(dateTime = date.datetime.dropLast(11)) else "",
                                maxLines = 1,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }

                    if (date != null) {
                        NotificationCart(
                            navController = navController,
                            modifier = Modifier.background(Color.White),
                            notification = date,
                            theme = darkTheme
                        )
                    }
                }
                item {
                    CheckEndOfPagination(loadState = items.loadState)
                }
//                item {
//                    LaunchedEffect(true){
//                        isEndList = true
//                    }
//                }
//                item {
//                    if (viewModel.hasMorePages.value && isEndList) {
//                        Row( modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center){
//                            CircularProgressIndicator()
//                        }
//                        //items.loadState.refresh
//                        isEndList = false
//                    }
//                }
            }
        }
    }
}

@Composable
fun CheckEndOfPagination(loadState: CombinedLoadStates) {
    Log.d("notifMs", "loadState: $loadState")
    when {
        loadState.append.endOfPaginationReached -> {
            Log.d("notifMs", "loadState.append.endOfPaginationReached : $loadState.append.endOfPaginationReached ")
            Text("Вы достигли конца списка.")
        }
        loadState.refresh is LoadState.Loading -> {
            CircularProgressBox()
        }
        loadState.append is LoadState.Loading -> {
            CircularProgressBox()
        }
        loadState.refresh is LoadState.Error -> {
            val e = loadState.refresh as LoadState.Error
            Button(onClick = { /* retry action */ }) {
                Text("Ошибка: ${e.error.localizedMessage}. Нажмите, чтобы повторить.")
            }
        }
    }
}


//val groupedNotifications =
//                items.itemSnapshotList.groupBy { it?.datetime?.let { it1 -> viewModel.getDate(it1.dropLast(11)) } }
//            LazyColumn(
//                state = scrollState,
//                contentPadding = PaddingValues(16.dp),
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.background),
//            ) {
//                groupedNotifications.keys.forEachIndexed { index, date ->
//                    if (index == 0) {
//                        item {
//                            Text(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .fillMaxWidth(),
//                                text = date.toString(),
//                                textAlign = TextAlign.Center,
//                                color = MaterialTheme.colorScheme.onSurface,
//                            )
//                        }
//                    } else {
//                        item {
//                            Spacer(modifier = Modifier.height(10.dp))
//                        }
//                        item {
//                            Text(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .weight(1f),
//                                text = date.toString(),
//                                maxLines = 1,
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                color = MaterialTheme.colorScheme.onSurface,
//                            )
//                        }
//                    }
//                    itemsIndexed(groupedNotifications[date]!!) { _, userNotification ->
//                        if (userNotification != null) {
//                            NotificationCart(
//                                navController = navController,
//                                modifier = Modifier
//                                    .background(Color.White),
//                                notification = userNotification,
//                                theme = theme
//                            )
//                        }else{
//
//                            Log.d("notifMs", "userNotification =-= null")
//                        }
//                    }
//                    item {
//                        LaunchedEffect(true){
//                            isEndList = true
//                        }
//                    }
//                    item {
//                        if (viewModel.hasMorePages.value && isEndList) {
//                            Row( modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.Center){
//                                CircularProgressIndicator()
//                            }
//                            items.loadState.refresh
//                            isEndList = false
//                        }
//                    }
//                }
//            }