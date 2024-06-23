package com.example.zebracoffee.presentation.profileScreen.aboutUs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zebracoffee.R
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.profileScreen.TitleBlock
import com.example.zebracoffee.utils.Constant

@Composable
fun AboutUsScreen(
    navController: NavHostController,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        TitleBlock(
            text = stringResource(id = R.string.aboutUs),
            navController = navController,
            onIconClick = { navController.popBackStack() }
        )
        AboutImageBlock()

        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.aboutUs_sub_tetx),
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = Constant.fontRegular,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        AboutItem(
            iconId = R.drawable.ic_term_of_use,
            text = stringResource(id = R.string.term_of_use),
            onClick = { navController.navigate(MainDestinations.PdfTermScreen_route) },
            modifier = Modifier.padding(top = 40.dp)
        )
        AboutItem(
            iconId = R.drawable.ic_term_of_use,
            text = stringResource(id = R.string.term_of_legal),
            onClick = {navController.navigate(MainDestinations.PdfLegalScreen_route) },
            modifier = Modifier.padding(top = 18.dp)
        )
        AboutItem(
            iconId = R.drawable.ic_star,
            text = stringResource(id = R.string.make_star),
            onClick = {},
            modifier = Modifier.padding(top = 18.dp)
        )
    }
}

@Composable
fun AboutImageBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp))
                .size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(30.dp)
                    .size(90.dp),
                painter = painterResource(id = R.drawable.ic_zebra_symbol),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 18.dp)
                .padding(horizontal = 18.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.app_name).uppercase(),
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = Constant.fontBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
        )
    }
}
