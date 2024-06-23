package com.example.zebracoffee.presentation.allowing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.common.CustomButtonPrimaryHome
import com.example.zebracoffee.common.TextTemplate18
import com.example.zebracoffee.common.TextTemplate24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortUpdate(/*onButtonClick: () -> Unit*/) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            isSheetOpen = false
        },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(430.dp)
                .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 0.dp),
            ) {
            Row(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .width(44.dp)
                        .aspectRatio(1f)
                        .clickable {
/*
                            onButtonClick()
*/
                        }
                        .background(
                            color = colorResource(id = R.color.fill_colors_4),
                            shape = RoundedCornerShape(38.dp)
                        ),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_secondary_btm_sheet),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .height(44.dp)
                            .width(44.dp)
                            .padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            TextTemplate24(R.string.new_update)
            Spacer(modifier = Modifier.height(10.dp))
            TextTemplate18(R.string.first_line)
            TextTemplate18(R.string.second_line)
            TextTemplate18(R.string.third_line)
            TextTemplate18(R.string.dump_line)

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .background(
                        Color.Transparent,
                    ),
                contentAlignment = Alignment.Center

            ) {
                Image(
                    painter = painterResource(id = R.drawable.stopwatch_front_1),
                    contentDescription = null,
                    modifier = Modifier
                        .height(160.dp)
                        .width(160.dp)
                )
            }
            CustomButtonPrimaryHome(stringResource(id = R.string.ShortUpdate)) {
/*
                onButtonClick()
*/
            }
        }
    }
}

