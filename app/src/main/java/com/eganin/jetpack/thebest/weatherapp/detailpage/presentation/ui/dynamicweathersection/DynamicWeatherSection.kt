package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection

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

@Composable
fun DynamicWeatherSection(info: WeatherData) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        DynamicWeatherLandscape(info = info, constraints = constraints)
    }
}

@Composable
fun DynamicWeatherLandscape(info: WeatherData, constraints: Constraints) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backgroundLayer1, backgroundLayer2, mountain, particles, clouds, fog, temperature, temperatureUnit, weatherDescription) = createRefs()

        /*
        val (backgroundLayerOneImage , backgroundLayerTwoImage) = when{
            info.time.hour in 6..9 -
        }

         */

        val backgroundLayerOneImage = R.drawable.night
        val backgroundLayerTwoImage = R.drawable.sunrise

        Image(
            painter = painterResource(backgroundLayerOneImage),
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

        Image(
            painter = painterResource(backgroundLayerTwoImage),
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