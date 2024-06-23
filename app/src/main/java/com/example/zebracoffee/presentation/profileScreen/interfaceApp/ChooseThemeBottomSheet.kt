package com.example.zebracoffee.presentation.profileScreen.interfaceApp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zebracoffee.R
import com.example.zebracoffee.presentation.profileScreen.userInfoScreen.BottomSheetTopBlock
import com.example.zebracoffee.utils.Constant
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseThemeBottomSheet(
    visible: Boolean,
    strings: List<String>,
    chosenIndex: Int,
    chooseCallback: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalSheet(
        visible = visible,
        onDismiss = onDismiss
    ) { sheetState ->
        Column(modifier = Modifier.padding(16.dp)) {
            BottomSheetTopBlock(
                text = stringResource(id = R.string.interface_language),
                iconClick = { onDismiss() }
            )
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
            ) {
                itemsIndexed(strings) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(top = 10.dp)
                            .clickable(
                                onClick = {
                                    scope
                                        .launch { sheetState.hide() }
                                        .invokeOnCompletion {
                                            if (!sheetState.isVisible) {
                                                chooseCallback(index)
                                                onDismiss()
                                            }
                                        }
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = item,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontFamily = Constant.fontRegular,
                        )
                        Log.d("testItemModes", item)
                        if (index == chosenIndex) {
                            Spacer(modifier = Modifier.weight(1F))
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}