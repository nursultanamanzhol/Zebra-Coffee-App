package com.example.zebracoffee.presentation.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.data.modelDto.DeleteAccountState
import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.profileScreen.dialog.AlertDialogComponent
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.ChooseThemeBottomSheet
import com.example.zebracoffee.ui.theme.MediumGray2
import com.example.zebracoffee.utils.CommonUtils.FormatNumber.Companion.reformatPhoneNumber
import com.example.zebracoffee.utils.Constant
import com.example.zebracoffee.utils.Constant.imageBaseUrl

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel,
    navController: NavHostController,
) {
    val isDialogVisible by viewModel.isLogoutDialogVisible.collectAsStateWithLifecycle()
    val isDialogDeleteVisibility by viewModel.isDeleteDialogVisible.collectAsStateWithLifecycle()
    val logoutState by viewModel.logout.collectAsStateWithLifecycle()
    val deleteAccountState by viewModel.deleteAccountState.collectAsStateWithLifecycle()

    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val userImage by viewModel.userImage.collectAsStateWithLifecycle()
    val userPhone by viewModel.userPhone.collectAsStateWithLifecycle()
    val userPk by viewModel.userPk.collectAsStateWithLifecycle()

    val theme by viewModel.theme.collectAsState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val darkTheme = when (theme) {
        AppTheme.DAY -> false
        AppTheme.NIGHT -> true
        else -> isSystemInDarkTheme()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TitleBlock(
            text = stringResource(id = R.string.profile),
            navController = navController,
            onIconClick = { navController.navigate(MainDestinations.MainScreen_route) }
        )

        ProfileHeader(
            userName = userName,
            userPhoneNumber = userPhone,
            imageUrl = userImage
        )
        Spacer(modifier = Modifier.height(44.dp))
//        ProfileBlockItem(
//            iconId = R.drawable.ic_2profile,
//            text = stringResource(id = R.string.inviteFriends),
//            padding = 44.dp,
//            onClick = {}
//        )
        ProfileBlockItem(
            iconId = R.drawable.ic_profile,
            text = stringResource(id = R.string.ownInfo),
            onClick = { navController.navigate(MainDestinations.UserInfoScreen_route) },
            theme = darkTheme
        )
        ProfileBlockItem(
            iconId = R.drawable.ic_zebra_symbol,
            text = stringResource(id = R.string.aboutUs),
            onClick = { navController.navigate(MainDestinations.AboutUsScreen_route) },
            theme = darkTheme
        )
        Spacer(modifier = Modifier.height(44.dp))

        ProfileBlockItem(
            iconId = R.drawable.ic_notification,
            text = stringResource(id = R.string.notification),
            padding = 0.dp,
            onClick = { navController.navigate(MainDestinations.NotificationScreen_route) },
            theme = darkTheme
        )


        ProfileBlockItem(
            iconId = R.drawable.ic_interface,
            text = stringResource(id = R.string.interface_language),
            onClick = { isSheetOpen = true },
            theme = darkTheme
        )

        ChooseThemeBottomSheet(
            visible = isSheetOpen,
            strings = listOf("Светлая тема", "Темная тема", "Системная"),
            chosenIndex = theme.toIndex(),
            chooseCallback = { viewModel.changeTheme(AppTheme.fromIndex(it)) },
        ) {
            isSheetOpen = false
        }

        Spacer(modifier = Modifier.height(28.dp))

        ProfileBlockItem(
            iconId = R.drawable.ic_logout,
            text = stringResource(id = R.string.logout_app),
            padding = 44.dp,
            onClick = { viewModel.changeVisibilityLogoutDialog(true) },
            theme = darkTheme
        )

        DialogLogout(
            logoutState = logoutState,
            navController = navController,
            isDialogVisible = isDialogVisible,
            viewModel = viewModel,
            userPk = userPk
        )

        Text(
            modifier = Modifier
                .padding(top = 25.dp, start = 16.dp)
                .clickable {
                    viewModel.changeVisibilityDeleteDialog(true)
                },
            text = stringResource(id = R.string.delete_account).uppercase(),
            color = MediumGray2,
            fontFamily = Constant.fontBold,
            fontSize = 12.sp
        )

        DialogDeleteAccount(
            deleteAccountState = deleteAccountState,
            navController = navController,
            isDialogDeleteVisibility = isDialogDeleteVisibility,
            viewModel = viewModel,
            userPk = userPk
        )
    }
}

@Composable
fun DialogLogout(
    logoutState: Resource<LogoutResponse>,
    navController: NavHostController,
    isDialogVisible: Boolean,
    viewModel: ProfileScreenViewModel,
    userPk: Int,
) {

    when (logoutState) {
        is Resource.Success -> {
            navController.navigate(MainDestinations.TypeNumberScreen_route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier,
                color = Color.Blue,
            )
        }

        is Resource.Failure -> {}
        else -> Unit
    }

    if (isDialogVisible) {
        AlertDialogComponent(
            textTitle = stringResource(id = R.string.are_you_sure_logout),
            textConfirm = stringResource(id = R.string.exit),
            textDismiss = stringResource(id = R.string.cancel),
            onDismissRequest = { viewModel.changeVisibilityLogoutDialog(false) },
            onClickConfirmButton = {
                viewModel.logout()
                viewModel.clearTokens()
                viewModel.deleteUser(userPk)
            },
        )
    }
}

@Composable
fun DialogDeleteAccount(
    deleteAccountState: DeleteAccountState,
    navController: NavHostController,
    isDialogDeleteVisibility: Boolean,
    viewModel: ProfileScreenViewModel,
    userPk:Int
) {

    when (deleteAccountState) {
        is DeleteAccountState.Success -> {
            navController.navigate(MainDestinations.TypeNumberScreen_route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }

        is DeleteAccountState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier,
                color = Color.Blue,
            )
        }

        is DeleteAccountState.Error -> {}
        else -> Unit
    }
    if (isDialogDeleteVisibility) {
        AlertDialogComponent(
            textTitle = stringResource(id = R.string.are_you_sure_delete),
            textConfirm = stringResource(id = R.string.delete),
            textDismiss = stringResource(id = R.string.exit),
            onDismissRequest = {
                viewModel.changeVisibilityDeleteDialog(false)
            },
            onClickConfirmButton = {
                viewModel.deleteUserAccount()
                viewModel.clearTokens()
                viewModel.deleteUser(userPk)
                viewModel.changeState()
            },
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileHeader(
    userName: String,
    userPhoneNumber: String,
    imageUrl: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = "${imageBaseUrl}${imageUrl}", //        model = "${imageBaseUrl}${user?.avatar_image}",
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Transparent, CircleShape)
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName,
                fontSize = 22.sp,
                fontFamily = Constant.fontBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            val userPhone = reformatPhoneNumber(userPhoneNumber)
            Text(
                text = userPhone,
                fontSize = 14.sp,
                fontFamily = Constant.fontRegular,
                color = MediumGray2
            )
        }
    }
}
