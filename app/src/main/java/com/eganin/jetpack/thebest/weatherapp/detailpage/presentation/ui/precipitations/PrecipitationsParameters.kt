package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations

import androidx.compose.ui.graphics.Color

data class PrecipitationsParameters(
    val particleCount: Int,
    val maxSpeed: Float,
    val shape: PrecipitationShape,
    val gravity : Float,
    val sizeParticle : Float,
    val color : Color
)

val snowParameters = PrecipitationsParameters(
    particleCount = 200,
    maxSpeed = 1.5f,
    shape = PrecipitationShape.Circle,
    gravity = 0.01f,
    sizeParticle = 20f,
    color = Color.White
)

val rainParameters = PrecipitationsParameters(
    particleCount = 200,
    maxSpeed = 1.5f,
    shape = PrecipitationShape.Line,
    gravity = 0.01f,
    sizeParticle = 20f,
    color =Color.Gray
)
