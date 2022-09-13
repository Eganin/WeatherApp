package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.eganin.jetpack.thebest.weatherapp.common.domain.weather.WeatherState
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.sunsetsunrisetime.SunsetSunriseTimeData
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.Particles
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.clouds.SceneCloud
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain.SceneRain
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain.StepFrame
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.snowParameters
import com.eganin.jetpack.thebest.weatherapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DynamicWeatherSection(
    info: WeatherData,
    sunsetAndSunriseTimeData: SunsetSunriseTimeData,
    cityName: String,
    isSmallSize: Boolean = false
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        DynamicWeatherLandscape(
            info = info,
            constraints = constraints,
            sunsetAndSunriseTimeData = sunsetAndSunriseTimeData,
            cityName = cityName,
            isSmallSize = isSmallSize
        )
    }
}

@Composable
fun DynamicWeatherLandscape(
    info: WeatherData,
    sunsetAndSunriseTimeData: SunsetSunriseTimeData,
    constraints: Constraints,
    cityName: String,
    isSmallSize: Boolean
) {
    var isVisibleThunder by remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        // while for thunder
        rememberCoroutineScope().launch {
            withContext(Dispatchers.Default) {
                while (true) {
                    delay(1000L)
                    withContext(Dispatchers.Main) {
                        isVisibleThunder = !isVisibleThunder
                    }
                }
            }
        }

        val height = constraints.maxHeight
        val width = constraints.maxWidth

        var sunProgressX = 0f
        var sunProgressY = 0f
        var sunIsVisible = true

        var moonProgressX = 0f
        var moonProgressY = 0f
        var moonIsVisible = false

        val startXPositionIcon = width.toFloat() / 1.2f
        val startYPositionIcon = if (!isSmallSize) height.toFloat() / 4f else height.toFloat()/14f
        val endXPositionIcon = width.toFloat() / 8f
        val endYPositionIcon = if (!isSmallSize) height.toFloat() / 2.5f else height.toFloat()/12f
        val maxXPositionIcon = width.toFloat() / 2f
        val maxYPositionIcon = if (!isSmallSize) height.toFloat() / 8f else height.toFloat()/14f

        val (backgroundLayer1, backgroundLayer2, mountain, particles, fog, temperature, temperatureUnit, weatherDescription, cityDescription) = createRefs()
        val (backgroundLayerOneImage, backgroundLayerTwoImage) = when {
            info.time.hour in 6..11 && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) in -1..2 -> {
                moonIsVisible = true
                moonProgressX = endXPositionIcon
                moonProgressY = endYPositionIcon

                sunProgressX = startXPositionIcon
                sunProgressY = startYPositionIcon

                PaintStatusBarColor(color = SystemBarColorSunrise)
                R.drawable.day to R.drawable.sunrise
            }

            (info.time.hour in 6..11 && (sunsetAndSunriseTimeData.sunriseHour - info.time.hour) !in -1..2) || (info.time.hour in 12..16) -> {
                sunProgressY = maxYPositionIcon
                sunProgressX = maxXPositionIcon
                PaintStatusBarColor(color = SystemBarColorDay)
                R.drawable.day to null
            }

            info.time.hour in 17..21 && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) in -1..2 -> {
                sunProgressX = endXPositionIcon
                sunProgressY = endYPositionIcon
                moonIsVisible = true
                moonProgressX = startXPositionIcon
                moonProgressY = startYPositionIcon
                PaintStatusBarColor(color = SystemBarColorSunset)
                R.drawable.night to R.drawable.sunset
            }

            (info.time.hour in 21..23 && (sunsetAndSunriseTimeData.sunsetHour - info.time.hour) !in -1..2) || (info.time.hour in 0..5) -> {
                sunIsVisible = false
                moonIsVisible = true
                moonProgressX = maxXPositionIcon
                moonProgressY = maxYPositionIcon
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

        Image(painter = painterResource(id = R.drawable.landscape),
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
                })

        val textColor = White.copy(alpha = 0.8f)

        Text(text = "${info.temperatureCelsius}",
            style = Typography.h3,
            color = textColor,
            modifier = Modifier
                .statusBarsPadding()
                .constrainAs(temperature) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                })

        Text(text = "°C",
            style = Typography.h5,
            color = textColor,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 12.dp)
                .constrainAs(temperatureUnit) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(temperature.end, margin = 4.dp)
                })

        val textShadow = Shadow(
            offset = Offset(5f, 5f), blurRadius = 5f
        )

        Text(text = info.weatherType.weatherDesc,
            style = Typography.h5.copy(shadow = textShadow),
            color = textColor,
            modifier = Modifier.constrainAs(weatherDescription) {
                top.linkTo(temperature.bottom, margin = 4.dp)
                start.linkTo(parent.start, margin = 16.dp)
            })

        Text(text = cityName,
            style = Typography.h5.copy(shadow = textShadow),
            color = textColor,
            modifier = Modifier.constrainAs(cityDescription) {
                top.linkTo(weatherDescription.bottom, margin = 2.dp)
                start.linkTo(parent.start, margin = 16.dp)
            })

        if (sunIsVisible) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = stringResource(R.string.sun_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .offset(x = with(LocalDensity.current) { sunProgressX.toDp() },
                        y = with(LocalDensity.current) { sunProgressY.toDp() })
            )
        }

        if (moonIsVisible) {
            Image(
                painter = painterResource(id = R.drawable.moon),
                contentDescription = stringResource(R.string.moon_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .offset(x = with(LocalDensity.current) { moonProgressX.toDp() },
                        y = with(LocalDensity.current) { moonProgressY.toDp() })
            )
        }

        // add fog
        val weatherState = info.state
        Crossfade(targetState = weatherState) { state ->
            val fogAlpha = when (state) {
                WeatherState.MOSTLY_CLOUDY -> 0.3f
                WeatherState.HEAVY_RAIN -> 0.3f
                WeatherState.RAIN -> 0.3f
                WeatherState.THUNDERSTORM -> 0.4f
                WeatherState.SNOW -> 0.4f
                WeatherState.FOG -> 0.6f
                else -> 0f
            }
            if (fogAlpha > 0f) {
                Surface(color = Color.DarkGray.copy(alpha = fogAlpha),
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(fog) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    content = {})
            }
        }
        // add thunder
        if (weatherState == WeatherState.THUNDERSTORM && isVisibleThunder) {
            Thunder(width = constraints.maxWidth, height = constraints.maxHeight)
        }
        // add clouds
        // and add snow and rain
        Crossfade(targetState = weatherState) { state ->
            val cloudCount = when (state) {
                WeatherState.RAIN -> 3
                WeatherState.HEAVY_RAIN -> 5
                WeatherState.THUNDERSTORM -> 6
                WeatherState.SNOW -> 3
                WeatherState.CLEAR_SKY -> 0
                WeatherState.FEW_CLOUDS -> 2
                WeatherState.SCATTERED_CLOUDS -> 3
                WeatherState.MOSTLY_CLOUDY -> 8
                WeatherState.FOG -> 3
            }

            if (cloudCount > 0) {
                CreateClouds(particleCount = cloudCount)
            }
            // add rain and snow
            when (weatherState) {
                WeatherState.HEAVY_RAIN, WeatherState.THUNDERSTORM -> CreateRain(particleCount = 300)
                WeatherState.RAIN -> CreateRain(particleCount = 100)
                WeatherState.SNOW -> Particles(parameters = snowParameters,
                    modifier = Modifier.constrainAs(particles) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                else -> {}

            }

        }
    }
}

@Composable
private fun CreateRain(particleCount: Int) {
    val scene = remember { SceneRain(particleCount = particleCount) }
    scene.setupScene()
    val frameState = StepFrame {
        scene.update()
    }
    scene.render(frameState = frameState)
}

@Composable
private fun CreateClouds(particleCount: Int) {
    val scene = remember { SceneCloud(particleCount = particleCount) }
    scene.setupScene()
    val frameState = StepFrame {
        scene.update()
    }
    scene.render(frameState = frameState)
}

@Composable
private fun PaintStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // setup status bar
        systemUiController.apply {
            setSystemBarsColor(color = color)
        }
    }

}
