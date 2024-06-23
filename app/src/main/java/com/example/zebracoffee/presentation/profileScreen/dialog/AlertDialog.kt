package com.example.zebracoffee.presentation.profileScreen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.utils.Constant

@Composable
fun AlertDialogComponent(
    textTitle: String,
    textConfirm: String,
    textDismiss: String,
    onDismissRequest: () -> Unit,
    onClickConfirmButton: () -> Unit,
) {

    AlertDialog(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background, RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp)),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = textTitle,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = Constant.fontBold,
                fontSize = 18.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                    onClickConfirmButton()
                }
            ) {
                Text(
                    textConfirm,
                    color = Color.Red,
                    fontFamily = Constant.fontBold,
                    fontSize = 14.sp

                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                }
            ) {
                Text(
                    textDismiss,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Constant.fontBold,
                    fontSize = 14.sp
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface
    )
}