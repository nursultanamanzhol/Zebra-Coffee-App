package com.example.zebracoffee.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.zebracoffee.R


object Constant {

    val fontRegular = FontFamily(
        Font(R.font.farfetch_basis_regular, FontWeight.Black)
    )
    val fontBold = FontFamily(
        Font(R.font.farfetch_basis_bold, FontWeight.Black)
    )
    const val BASE_URL = "https://api-dev.zebra-crm.kz/"
    const val imageBaseUrl = "https://api-dev.zebra-crm.kz"
    const val USER_SETTINGS = "userSettings"
    const val APP_ENTRY = "appEntry"
}
