package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.Particles
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.PrecipitationShape
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.PrecipitationSourceEdge
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.PrecipitationsParameters

@Composable
fun Clouds(
    modifier: Modifier = Modifier,
    cloudCount: Int
) {
    BoxWithConstraints(modifier = modifier) {
        val cloudsParameters = PrecipitationsParameters(
            particleCount = cloudCount,
            distancePerStep = 1,
            minSpeed = 0.2f,
            maxSpeed = 0.6f,
            minAngle = 0,
            maxAngle = 0,
            shape = PrecipitationShape.Image(
                image = ImageBitmap.imageResource(R.drawable.cloud),
                minWidth = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
                maxWidth = with(LocalDensity.current) { 120.dp.toPx() }.toInt(),
                minHeight = with(LocalDensity.current) { 30.dp.toPx() }.toInt(),
                maxHeight = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
            ),
            sourceEdge = PrecipitationSourceEdge.RIGHT
        )

        Particles(
            modifier = Modifier
                .fillMaxSize(),
            parameters = cloudsParameters
        )
    }
}