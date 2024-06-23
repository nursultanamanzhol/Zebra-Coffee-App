package com.example.zebracoffee.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.presentation.registration.typeNumber.CustomSnackBar

//@Composable
//fun SnackbarBlock(
//    snackState: SnackbarHostState,
//    text: String,
//    iconId: Int,
//) {
//    Box(
//        modifier = Modifier
//            .padding(horizontal = 18.dp)
//            .padding(top = 8.dp)
//            .shadow(
//                elevation = 20.dp,
//                shape = RoundedCornerShape(15.dp),
//                ambientColor = Color.White,
//                spotColor = Color.White
//            )
//            .clipToBounds(),
//        contentAlignment = Alignment.TopCenter  // Выравнивание содержимого вверху и по центру
//    ) {
//        SnackbarHost(
//            hostState = snackState,
//            snackbar = { data ->
//                CustomSnackBar(
//                    drawableRes = iconId,
//                    message = text,
//                    isRtl = false,
//                    containerColor = Color.White,
//                    modifier = Modifier.padding(8.dp)
//                )
//            }
//        )
//    }
//}

@Composable
fun SnackbarBlock(
    snackState: SnackbarHostState,
    text: String,
    iconId: Int,
) {

    Row(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Top)

        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.TopCenter)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(12.dp),
                        ambientColor = Color.White,
                        spotColor = Color.White
                    )
                    .clipToBounds(),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(
                    hostState = snackState,
                ) {
                    CustomSnackBar(
                        drawableRes = iconId,
                        message = text,
                        isRtl = false,
                        containerColor = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
