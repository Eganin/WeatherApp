package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.weatherradar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R

@Composable
fun WeatherRadar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            title = stringResource(R.string.weather_radar_label),
            subtitle = stringResource(R.string.satellite_subtitle)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.padding(24.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp
            ) {
                Image(
                    painter = painterResource(R.drawable.weather_radar_lyon),
                    contentDescription = stringResource(R.string.fake_satellite_image_description),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}