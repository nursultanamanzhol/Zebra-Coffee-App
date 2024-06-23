package com.example.zebracoffee.presentation.profileScreen.aboutUs.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.zebracoffee.R

@Composable
fun PdfTermScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ComposePDFViewer(pdfResId = R.raw.terms_of_use_pdf)
    }
}