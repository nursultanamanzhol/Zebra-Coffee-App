package com.example.zebracoffee.presentation.profileScreen.aboutUs.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.zebracoffee.R

@Composable
fun PdfLegalScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ComposePDFViewer(pdfResId = R.raw.legal_info_pdf)
    }
}