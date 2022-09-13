package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import me.nikhilchaudhari.quarks.CreateParticles
import me.nikhilchaudhari.quarks.particle.*

@Composable
fun Particles(
    modifier: Modifier = Modifier,
    parameters: PrecipitationsParameters
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        GenerateSnow(modifier = Modifier.fillMaxSize(), parameters = parameters)
    }
}

@Composable
fun GenerateSnow(modifier: Modifier = Modifier, parameters: PrecipitationsParameters) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val widthSourceList = listOf(width / 5f, width / 2f, width.toFloat() / 0.5f)
        widthSourceList.forEach { x ->
            CreateParticles(
                modifier = Modifier.fillMaxSize(),
                x = x, y = 0f - height / 2,
                velocity = Velocity(xDirection = parameters.maxSpeed, yDirection = 1f),
                force = Force.Gravity(parameters.gravity),
                particleSize = ParticleSize.ConstantSize(size = parameters.sizeParticle),
                particleColor = ParticleColor.SingleColor(color = parameters.color),
                lifeTime = LifeTime(100f, 0.2f),
                emissionType = EmissionType.FlowEmission(
                    maxParticlesCount = EmissionType.FlowEmission.INDEFINITE,
                    emissionRate = 1f
                ),
            )
        }
    }
}