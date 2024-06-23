package com.example.zebracoffee.presentation.registration.chooseAvatar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.zebracoffee.R
import com.example.zebracoffee.common.SetupSystemBarsBlack
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.ui.theme.White
import com.example.zebracoffee.ui.theme.СolorBorderDarkGray
import com.example.zebracoffee.utils.Constant

@SuppressLint("ResourceType")
@Composable
fun AvatarScreen(
    navController: NavHostController,
    viewModel: AvatarViewModel,
    nameValue: String,
) {
    SetupSystemBarsBlack()
    var selectedAvatar by remember { mutableStateOf<AvatarImage?>(null) }
    val avatarImages by viewModel.avatarImagesState.collectAsStateWithLifecycle()
    val updateAvatar by viewModel.updateAvatar.collectAsStateWithLifecycle()

    LaunchedEffect(avatarImages) {
        if (avatarImages.isNotEmpty()) {
            selectedAvatar = avatarImages.first()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            FirstBlock(navController = navController)
            AvatarImageBlock(selectedAvatar, nameValue)
        }

        Row(
            modifier = Modifier
                .padding(top = 80.dp, bottom = 5.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SaveButtonBlock(navController, viewModel, selectedAvatar)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(СolorBorderDarkGray.copy(alpha = 0.85f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
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
//                            viewModel.updateAvatarImage(item.id)
//                            onAvatarSelected = { selectedAvatar = item }
                        },
                        isSelected = selectedAvatar == item
                    )
                }
            }
        }
    }

    LaunchedEffect(updateAvatar) {
        if (updateAvatar is Resource.Success) {
            viewModel.resetState()
        }
    }
}

@Composable
fun FirstBlock(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            modifier = Modifier
                .weight(0.1f)
                .clickable { navController.popBackStack() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "icon back",
            tint = White
        )
        Text(
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.choose_avatar).uppercase(),
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Icon(
            modifier = Modifier
                .weight(0.1f)
                .clickable { },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "icon back",
            tint = Color.Black
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AvatarImageBlock(
    selectedAvatar: AvatarImage?,
    nameValue: String
) {
    val avatarImage = selectedAvatar?.avatar ?: "default_avatar_image"
    val contentDescription = selectedAvatar?.name ?: "default avatar image"

    GlideImage(
        model = avatarImage,
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(top = 16.dp)
            .size(160.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

    Text(
        modifier = Modifier.padding(top = 24.dp),
        text = nameValue.uppercase(),
        color = Color.White,
        fontFamily = Constant.fontBold,
        fontSize = 28.sp
    )

    if (selectedAvatar != null) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "[" + selectedAvatar.name + "]",
            color = Color.White,
            fontFamily = Constant.fontRegular,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SaveButtonBlock(
    navController: NavHostController,
    viewModel: AvatarViewModel,
    selectedAvatar: AvatarImage?,
) {
    Button(
        modifier = Modifier
            .width(130.dp)
            .height(48.dp),
        shape = RoundedCornerShape(56.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        onClick = {
            selectedAvatar?.let { avatar ->
                navController.navigate(MainDestinations.WelcomeScreen_route)
                viewModel.updateAvatarImage(avatar.id)
//                viewModel.updateAvatarImage(it.id)
            }
        }
    ) {
        Text(
            text = stringResource(id = R.string.save_btn).uppercase(),
            fontSize = 14.sp,
            fontFamily = Constant.fontBold
        )
    }
}
