package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.Particles
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.PrecipitationShape
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.PrecipitationsParameters

@Composable
fun Clouds(
    modifier: Modifier = Modifier,
    cloudCount: Int
) {
    BoxWithConstraints(modifier = modifier) {
        val cloudsParameters = PrecipitationsParameters(
            particleCount = cloudCount,
            maxSpeed = 0.6f,
            shape = PrecipitationShape.Cloud,
            gravity = 0.01f,
            sizeParticle = 20f,
            color = Color.White
        )

        Particles(
            modifier = Modifier
                .fillMaxSize(),
            parameters = cloudsParameters
        )
    }
}