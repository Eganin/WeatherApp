package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

sealed class PrecipitationShape {
    object Circle : PrecipitationShape()

    object Line : PrecipitationShape()

    data class Image(
        val image: ImageBitmap,
        val minWidth: Int,
        val maxWidth: Int,
        val minHeight: Int,
        val maxHeight: Int,
    ) : PrecipitationShape()
}