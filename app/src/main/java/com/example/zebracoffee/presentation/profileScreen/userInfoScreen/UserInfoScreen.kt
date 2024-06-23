package com.example.zebracoffee.presentation.profileScreen.userInfoScreen

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CircularProgressBox
import com.example.zebracoffee.common.SnackbarBlock
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.profileScreen.TitleBlock
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.presentation.registration.chooseAvatar.AvatarItem
import com.example.zebracoffee.ui.theme.ColorPrimary
import com.example.zebracoffee.ui.theme.PrimaryDarkNotActive
import com.example.zebracoffee.ui.theme.PrimaryNotActive
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.utils.CommonUtils.FormatNumber.Companion.reformatPhoneNumber
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    navController: NavHostController,
    viewModel: UserInfoScreenViewModel,
) {
    val sharedPreferences: SharedPreferences
    val nameState by viewModel.userName.collectAsState()
    val birthDateState by viewModel.userBirthdate.collectAsState()
    val phoneState by viewModel.userPhone.collectAsState()
    val nameStateTest by viewModel.userName.collectAsState()
    val birthDateStateTest by viewModel.userBirthdate.collectAsState()
    val phoneStateTest by viewModel.userPhone.collectAsState()
    val userImage by viewModel.userImage.collectAsState()
    val theme by viewModel.theme.collectAsState()
    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }
    var dataChanged by remember { mutableStateOf(false) }

    val avatarImages by viewModel.avatarImagesState.collectAsStateWithLifecycle()
    var selectedAvatar by remember { mutableStateOf<AvatarImage?>(null) }
    val updatedUserInfo by viewModel.updateUserInfo.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    var birthdayUpdate: String = ""
    var errorState by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    if (updatedUserInfo is Resource.Success) {
        dataChanged = false
        viewModel.resetState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarBlock(
                snackState = snackState,
                text = "Успешно сохранено",
                iconId = R.drawable.ic_success
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .weight(0.8f)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleBlock(
                        text = stringResource(id = R.string.user_info),
                        navController = navController
                    )
                    BlockProfileImageBlock(
                        onRowClicked = {
                            isSheetOpen = true
                        },
                        selectedAvatar = selectedAvatar,
                        imageUrl = userImage
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    ProfileTextField(
                        label = stringResource(id = R.string.name),
                        value = nameState,
                        keyboardType = KeyboardType.Text,
                        onValueChanged = {
                            viewModel.setName(it)
                            dataChanged = nameState != it
                        }
                    )

                    NumberTextField(
                        label = stringResource(id = R.string.phoneNumber),
                        value = reformatPhoneNumber(phoneState),
                        enabled = false,
                        onValueChanged = { viewModel.setPhone(it) }
                    )

                    BirthdayTextField(
                        label = stringResource(id = R.string.birthDate),
                        value = birthDateState,
                        onValueChanged = {
                            viewModel.setBirthDate(it)
                            dataChanged = nameState != it
                        }
                    )

                    Spacer(modifier = Modifier.height(100.dp))


                }
                val birthdayFormatForBackEnd = formatDateForBackEnd(birthDateState)
                CustomButtonPrimaryUser(
                    text = stringResource(id = R.string.save_btn),
                    onClick = {
                        if (dataChanged) {
                            viewModel.updateUserInfo(
                                nameState,
                                phoneState,
                                birthdayFormatForBackEnd,
                                selectedAvatar?.id ?: 1
                            )
                        }
                    },
                    textColor = Color.White,
                    active = dataChanged,
                    modifier = Modifier
                        .weight(0.2f),
                    darkTheme = darkTheme
                )

                if (isSheetOpen) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            isSheetOpen = false
                        },
                        containerColor = MaterialTheme.colorScheme.background,
                        dragHandle = {},
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(14.dp)
                        ) {
                            BottomSheetTopBlock(
                                text = stringResource(id = R.string.avatar),
                                iconClick = { isSheetOpen = false }
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                            ) {
                                items(avatarImages) { item ->
                                    AvatarItem(
                                        item = item,
                                        onAvatarSelected = {
                                            selectedAvatar = item
                                        },
                                        isSelected = selectedAvatar == item
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    when (updatedUserInfo) {
        is Resource.Success -> {
            navController.navigate(MainDestinations.ProfileScreen_route)
            viewModel.resetState()
        }

        is Resource.Loading -> {
            CircularProgressBox()
            Log.d("ChangeName2", "loading")
        }

        is Resource.Failure -> {
            Log.d("ChangeName2", "error")

        }

        else -> Unit
    }
}

@Composable
fun BottomSheetTopBlock(
    text: String,
    iconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontFamily = Constant.fontBold
        )
        Box() {}
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary, CircleShape)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.clickable { iconClick() },
                painter = painterResource(id = R.drawable.ic_close_x),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BlockProfileImageBlock(
    imageUrl: String,
    onRowClicked: () -> Unit,
    selectedAvatar: AvatarImage?,
) {
    Row(
        modifier = Modifier
            .padding(top = 30.dp)
            .clickable {
                onRowClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box() {
            selectedAvatar?.let { selected ->
                GlideImage(
                    model = selected.avatar,
                    contentDescription = selected.name,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            } ?: run {
                GlideImage(
                    model = "${imageBaseUrl}${imageUrl}",
                    contentDescription = "",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent, CircleShape),
                    alignment = Alignment.Center
                )
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .size(40.dp)
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun CustomButtonPrimaryUser(
    active: Boolean,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    darkTheme: Boolean
) {

    val activeColor: Color = if (!active && !darkTheme) {
        PrimaryNotActive
    } else if (!active && darkTheme) {
        PrimaryDarkNotActive
    } else {
        ColorPrimary
    }
    val activeColorText: Color = if (!active && darkTheme) {
        ColorPrimary
    } else if (!active && !darkTheme) {
        ColorPrimary
    } else {
        White
    }

    Log.d("testUIBtnActive", active.toString())
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = activeColor
        ),
        onClick = { onClick.invoke() },
    ) {
        Text(
            text = text.uppercase(),
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = activeColorText,
            fontFamily = Constant.fontRegular
        )
    }
}