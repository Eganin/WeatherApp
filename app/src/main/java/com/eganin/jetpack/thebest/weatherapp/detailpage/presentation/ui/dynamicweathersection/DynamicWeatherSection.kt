package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherData
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import com.eganin.jetpack.thebest.weatherapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

        val height = constraints.maxHeight
        val width = constraints.maxWidth

        var sunProgressX = 0f
        var sunProgressY = 0f
        var sunIsVisible = true

        var moonProgressX = 0f
        var moonProgressY = 0f
        var moonIsVisible = false

        val (backgroundLayer1, backgroundLayer2, mountain, particles, clouds, fog, temperature, temperatureUnit, weatherDescription) = createRefs()
        val (backgroundLayerOneImage, backgroundLayerTwoImage) = when {
            info.time.hour in 6..12
                    && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) in -1..2 -> {
                moonIsVisible = true
                moonProgressX = 200f
                moonProgressY = height.toFloat() / 2f

                sunProgressX = width.toFloat() - 200f
                sunProgressY = height.toFloat() / 3f

                PaintStatusBarColor(color = SystemBarColorSunset)
                R.drawable.day to R.drawable.sunset
            }

            (info.time.hour in 6..12
                    && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) !in -1..2)
                    || (info.time.hour in 13..17) -> {
                sunProgressY = height.toFloat()/8f
                sunProgressX = width.toFloat() / 2
                PaintStatusBarColor(color = SystemBarColorDay)
                R.drawable.day to null
            }

            info.time.hour in 18..23
                    && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) in -1..2 -> {
                sunProgressX = 200f
                sunProgressY = height.toFloat() / 3f
                moonIsVisible = true
                moonProgressX = width.toFloat() - 200f
                PaintStatusBarColor(color = SystemBarColorSunrise)
                R.drawable.night to R.drawable.sunrise
            }

            (info.time.hour in 18..23
                    && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) !in -1..2)
                    || (info.time.hour in 0..5) -> {
                sunIsVisible = false
                moonIsVisible = true
                moonProgressX = width.toFloat() / 2
                moonProgressY = height.toFloat()/8f
                PaintStatusBarColor(color = SystemBarColorNight)
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

        Image(
            painter = painterResource(id = R.drawable.landscape),
            contentDescription = stringResource(
                R.string.mountain_description
            ),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(mountain) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        val textColor = White.copy(alpha = 0.8f)

        Text(
            text = "${info.temperatureCelsius}",
            style = Typography.h3,
            color = textColor,
            modifier = Modifier
                .statusBarsPadding()
                .constrainAs(temperature) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        Text(
            text = "°C",
            style = Typography.h5,
            color = textColor,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 12.dp)
                .constrainAs(temperatureUnit) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(temperature.end, margin = 4.dp)
                }
        )

        val textShadow = Shadow(
            offset = Offset(5f, 5f),
            blurRadius = 5f
        )

        Text(
            text = info.weatherType.weatherDesc,
            style = Typography.h5.copy(shadow = textShadow),
            color = textColor,
            modifier = Modifier
                .constrainAs(weatherDescription) {
                    top.linkTo(temperature.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        if (sunIsVisible) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = stringResource(R.string.sun_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .offset(
                        x = with(LocalDensity.current) { sunProgressX.toDp() },
                        y = with(LocalDensity.current) { sunProgressY.toDp() }
                    )
            )
        }

        if (moonIsVisible) {
            Image(
                painter = painterResource(id = R.drawable.moon),
                contentDescription = stringResource(R.string.moon_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .offset(
                        x = with(LocalDensity.current) { moonProgressX.toDp() },
                        y = with(LocalDensity.current) { moonProgressY.toDp() }
                    )
            )
        }
    }
}

@Composable
fun PaintStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // setup status bar
        systemUiController.apply {
            setSystemBarsColor(color = color)
        }
    }
}