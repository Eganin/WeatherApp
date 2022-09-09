package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.constraintlayout.compose.ConstraintLayout
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData

@Composable
fun DynamicWeatherSection(info: WeatherData, sunsetAndSunriseTimeData: SunsetSunriseTimeData) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        DynamicWeatherLandscape(
            info = info,
            constraints = constraints,
            sunsetAndSunriseTimeData = sunsetAndSunriseTimeData
        )
    }
}

@Composable
fun DynamicWeatherLandscape(
    info: WeatherData,
    sunsetAndSunriseTimeData: SunsetSunriseTimeData,
    constraints: Constraints
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundLayer1, backgroundLayer2, mountain, particles, clouds, fog, temperature, temperatureUnit, weatherDescription) = createRefs()
        val (backgroundLayerOneImage, backgroundLayerTwoImage) = when {
            info.time.hour in 6..12
                    && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) in -1..2 -> {
                R.drawable.day to R.drawable.sunset
            }

            info.time.hour in 6..12
                    && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) !in -1..2 -> {
                R.drawable.day to null
            }

            info.time.hour in 13..17 -> {
                R.drawable.day to null
            }

            info.time.hour in 18..23
                    && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) in -1..2 -> {
                R.drawable.night to R.drawable.sunrise
            }

            info.time.hour in 18..23
                    && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) !in -1..2 -> {
                R.drawable.night to null
            }

            info.time.hour in 0..5 -> {
                R.drawable.night to null
            }

            else -> null to null
        }
        backgroundLayerOneImage?.let { imageId ->
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backgroundLayer1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            )
        }

        backgroundLayerTwoImage?.let { imageId ->
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backgroundLayer2) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            )
        }
    }
}